/**
* Copyright (c) 2000-present Liferay, Inc. All rights reserved.
*
* This library is free software; you can redistribute it and/or modify it under
* the terms of the GNU Lesser General Public License as published by the Free
* Software Foundation; either version 2.1 of the License, or (at your option)
* any later version.
*
* This library is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
* details.
*/
import UIKit


@objc public protocol DDLFormWidgetDelegate {

	optional func onFormLoaded(record: DDLRecord)
	optional func onFormLoadError(error: NSError)

	optional func onRecordLoaded(record: DDLRecord)
	optional func onRecordLoadError(error: NSError)

	optional func onFormSubmitted(record: DDLRecord)
	optional func onFormSubmitError(error: NSError)

	optional func onDocumentUploadStarted(field:DDLFieldDocument)
	optional func onDocumentUploadedBytes(field:DDLFieldDocument,
			bytes: UInt,
			sent: Int64,
			total: Int64)
	optional func onDocumentUploadCompleted(field:DDLFieldDocument, result:[String:AnyObject])
	optional func onDocumentUploadError(field:DDLFieldDocument, error: NSError)

}


@IBDesignable public class DDLFormWidget: BaseWidget {

	private enum FormOperation {

		case Idle
		case Uploading(DDLFieldDocument, Bool)

	}

	@IBInspectable public var structureId: Int64 = 0
	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var recordSetId: Int64 = 0
	@IBInspectable public var recordId: Int64 = 0
	@IBInspectable public var userId: Int64 = 0

	@IBInspectable public var repositoryId: Int64 = 0
	@IBInspectable public var folderId: Int64 = 0
	@IBInspectable public var filePrefix = "form-file-"

	@IBInspectable public var autoLoad = true
	@IBInspectable public var autoscrollOnValidation = true
	@IBInspectable public var showSubmitButton = true

	@IBOutlet public var delegate: DDLFormWidgetDelegate?

	internal var formView: DDLFormView {
		return widgetView as DDLFormView
	}

	private var currentOperation = FormOperation.Idle

	private var submitOperation: LiferayDDLFormSubmitOperation?
	private var loadFormOperation: LiferayDDLFormLoadOperation?
	private var loadRecordOperation: LiferayDDLFormRecordLoadOperation?
	private var uploadOperation: LiferayDDLFormUploadOperation?

	//MARK: BaseWidget

	override public func becomeFirstResponder() -> Bool {
		return formView.becomeFirstResponder()
	}

	override internal func onCreated() {
		formView.showSubmitButton = showSubmitButton
	}

	override internal func onShow() {
		if autoLoad {
			if recordId != 0 {
				loadRecord()
			}
			else {
				loadForm()
			}
		}
	}

	override internal func onUserAction(actionName: String?, sender: AnyObject?) {
		switch actionName! {
			case "submit-form":
				submitForm()
			case "upload-document":
				if let document = sender as? DDLFieldDocument {
					uploadDocument(document)
				}
			default: ()
		}
	}


	//MARK: Public methods

	public func loadForm() -> Bool {
		loadFormOperation = LiferayDDLFormLoadOperation(widget: self)

		loadFormOperation!.structureId = self.structureId
		loadFormOperation!.userId = self.userId

		return loadFormOperation!.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onFormLoadError?(error)
			}
			else {
				self.userId = self.loadFormOperation!.userId ?? self.userId
				self.formView.record = self.loadFormOperation!.loadedRecord

				self.delegate?.onFormLoaded?(self.formView.record!)
			}
		}
	}


	public func loadRecord() -> Bool {
		loadRecordOperation = LiferayDDLFormRecordLoadOperation(widget: self)

		loadRecordOperation!.recordId = self.recordId

		if formView.isRecordEmpty {
			if !loadForm() {
				return false
			}

			loadRecordOperation!.addDependency(loadFormOperation!)
		}

		return loadRecordOperation!.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onRecordLoadError?(error)
			}
			else {
				if let recordValue = self.formView.record {
					recordValue.updateCurrentValues(self.loadRecordOperation!.loadedRecord!)
					recordValue.recordId = self.recordId

					// Force didSet event
					self.formView.record = recordValue

					self.delegate?.onRecordLoaded?(recordValue)
				}
			}
		}
	}

	public func submitForm() -> Bool {
		switch currentOperation {
			case .Uploading(let doc, _):
				currentOperation = .Uploading(doc, true)
				showHUDWithMessage("Uploading file...", details: "Wait a second...")
				return true

			default: ()
		}

	 	submitOperation = LiferayDDLFormSubmitOperation(widget: self)

		submitOperation!.groupId = (self.groupId != 0)
				? self.groupId : LiferayServerContext.groupId

		submitOperation!.userId = (self.userId != 0)
				? self.userId : Int64((SessionContext.userAttribute("userId") ?? 0) as Int)

		submitOperation!.recordId = (self.recordId != 0) ? self.recordId : nil
		submitOperation!.recordSetId = self.recordSetId

		submitOperation!.autoscrollOnValidation = self.autoscrollOnValidation

		return submitOperation!.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onFormSubmitError?(error)
			}
			else {
				self.recordId = self.submitOperation!.recordId!
				self.formView.record!.recordId = self.recordId

				self.delegate?.onFormLoaded?(self.formView.record!)
			}
		}

	}


	//MARK: Private methods

	private func uploadDocument(document: DDLFieldDocument) -> Bool {
		let groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId
		let repositoryId = (self.repositoryId != 0) ? self.repositoryId : groupId

		uploadOperation = LiferayDDLFormUploadOperation(widget: self)

		uploadOperation!.document = document
		uploadOperation!.filePrefix = filePrefix
		uploadOperation!.repositoryId = repositoryId
		uploadOperation!.folderId = folderId
		uploadOperation!.onUploadedBytes = onUploadedBytes

		let result = uploadOperation!.validateAndEnqueue() {
			let document = self.uploadOperation!.document!

			if let error = $0.lastError {
				document.uploadStatus = .Failed(error)

				self.formView.changeDocumentUploadStatus(document)

				if !document.validate() {
					self.formView.showField(document)
				}

				self.delegate?.onDocumentUploadError?(document, error: error)

				self.currentOperation = .Idle
			}
			else {
				document.uploadStatus = .Uploaded(self.uploadOperation!.uploadResult!)

				self.formView.changeDocumentUploadStatus(document)

				self.delegate?.onDocumentUploadCompleted?(document,
						result: self.uploadOperation!.uploadResult!)

				switch self.currentOperation {
					case .Uploading(let document, let submitAfter):
						self.currentOperation = .Idle

						if submitAfter {
							self.submitForm()
						}

					default: ()
				}

			}
		}

		if result {
			delegate?.onDocumentUploadStarted?(document)
			currentOperation = .Uploading(document, false)
		}

		return result
	}

	private func onUploadedBytes(bytes: UInt, sent: Int64, total: Int64) {
		switch currentOperation {
			case .Uploading(let document, _):
				document.uploadStatus = .Uploading(UInt(sent), UInt(total))
				formView.changeDocumentUploadStatus(document)

				delegate?.onDocumentUploadedBytes?(document,
						bytes: bytes,
						sent: sent,
						total: total)

			default: ()
		}
	}

}
