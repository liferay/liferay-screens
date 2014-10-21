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


@objc public protocol DDLFormScreenletDelegate {

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


@IBDesignable public class DDLFormScreenlet: BaseScreenlet {

	private enum UploadStatus {

		case Idle
		case Uploading(Int, Bool)
		case Failed(NSError)

	}

	@IBInspectable public var structureId: Int64 = 0
	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var recordSetId: Int64 = 0
	@IBInspectable public var recordId: Int64 = 0
	@IBInspectable public var userId: Int64 = 0

	@IBInspectable public var repositoryId: Int64 = 0
	@IBInspectable public var folderId: Int64 = 0
	@IBInspectable public var filePrefix: String = "form-file-"

	@IBInspectable public var autoLoad: Bool = true
	@IBInspectable public var autoscrollOnValidation: Bool = true
	@IBInspectable public var showSubmitButton: Bool = true

	@IBOutlet public var delegate: DDLFormScreenletDelegate?

	internal var formView: DDLFormView {
		return screenletView as DDLFormView
	}

	private var uploadStatus = UploadStatus.Idle


	//MARK: BaseScreenlet

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
		let loadFormOperation = createLoadFormOperation()

		return loadFormOperation.validateAndEnqueue()
	}

	public func loadRecord() -> Bool {
		let loadRecordOperation = LiferayDDLFormRecordLoadOperation(screenlet: self)

		loadRecordOperation.recordId = self.recordId

		if formView.isRecordEmpty {
			let loadFormOperation = createLoadFormOperation()

			if !loadFormOperation.validateAndEnqueue() {
				return false
			}

			loadRecordOperation.addDependency(loadFormOperation)
		}

		return loadRecordOperation.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onRecordLoadError?(error)
			}
			else {
				if let recordValue = self.formView.record {
					recordValue.updateCurrentValues(loadRecordOperation.result!.record)
					recordValue.recordId = loadRecordOperation.result!.recordId

					// Force didSet event
					self.formView.record = recordValue

					self.delegate?.onRecordLoaded?(recordValue)
				}
			}
		}
	}

	public func submitForm() -> Bool {
		if waitForInProgressUpload() {
			return true
		}

	 	let submitOperation = LiferayDDLFormSubmitOperation(screenlet: self)

		submitOperation.groupId = (self.groupId != 0)
				? self.groupId : LiferayServerContext.groupId

		submitOperation.userId = (self.userId != 0)
				? self.userId : Int64((SessionContext.userAttribute("userId") ?? 0) as Int)

		submitOperation.recordId = (self.recordId != 0) ? self.recordId : nil
		submitOperation.recordSetId = self.recordSetId

		submitOperation.autoscrollOnValidation = self.autoscrollOnValidation

		return submitOperation.validateAndEnqueue() {
			if let error = $0.lastError {
				self.delegate?.onFormSubmitError?(error)
			}
			else {
				self.recordId = submitOperation.result!.recordId
				self.formView.record!.recordId = submitOperation.result!.recordId

				self.delegate?.onFormSubmitted?(self.formView.record!)
			}
		}
	}


	//MARK: Private methods

	private func createLoadFormOperation() -> LiferayDDLFormLoadOperation {
		let loadFormOperation = LiferayDDLFormLoadOperation(screenlet: self)

		loadFormOperation.structureId = self.structureId
		loadFormOperation.userId = self.userId

		loadFormOperation.onComplete = {
			if let error = $0.lastError {
				self.delegate?.onFormLoadError?(error)
			}
			else {
				self.userId = loadFormOperation.result!.userId ?? self.userId
				self.formView.record = loadFormOperation.result!.record

				self.delegate?.onFormLoaded?(self.formView.record!)
			}
		}

		return loadFormOperation
	}

	private func uploadDocument(document: DDLFieldDocument) -> Bool {
		let groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId
		let repositoryId = (self.repositoryId != 0) ? self.repositoryId : groupId

		let uploadOperation = LiferayDDLFormUploadOperation(screenlet: self)

		uploadOperation.document = document
		uploadOperation.filePrefix = filePrefix
		uploadOperation.repositoryId = repositoryId
		uploadOperation.folderId = folderId
		uploadOperation.onUploadedBytes = onUploadedBytes

		let result = uploadOperation.validateAndEnqueue() {
			let document = uploadOperation.document!

			if let error = $0.lastError {
				document.uploadStatus = .Failed(error)

				self.formView.changeDocumentUploadStatus(document)

				if !document.validate() {
					self.formView.showField(document)
				}

				self.delegate?.onDocumentUploadError?(document, error: error)

				self.uploadStatus = .Failed(error)
			}
			else {
				document.uploadStatus = .Uploaded(uploadOperation.uploadResult!)

				self.formView.changeDocumentUploadStatus(document)

				self.delegate?.onDocumentUploadCompleted?(document,
						result: uploadOperation.uploadResult!)

				switch self.uploadStatus {
					case .Uploading(let uploadCount, let submitRequest)
					where uploadCount > 1:
						self.uploadStatus = .Uploading(uploadCount - 1, submitRequest)

					case .Uploading(let uploadCount, let submitRequested)
					where uploadCount == 1 && submitRequested:
						self.uploadStatus = .Idle
						self.submitForm()

					case .Uploading(let uploadCount, let submitRequested)
					where uploadCount == 1 && !submitRequested:
						self.uploadStatus = .Idle

					default: ()
				}
			}
		}

		if result {
			delegate?.onDocumentUploadStarted?(document)

			switch uploadStatus {
				case .Uploading(let uploadCount, let submitRequested):
					uploadStatus = .Uploading(uploadCount + 1, submitRequested)

				default:
					uploadStatus = .Uploading(1, false)
			}
		}

		return result
	}

	private func waitForInProgressUpload() -> Bool {
		switch uploadStatus {
			case .Failed(_):
				retryUploads()

				return true

			case .Uploading(let uploadCount, let submitRequested)
			where submitRequested:
				return true

			case .Uploading(let uploadCount, let submitRequested)
			where !submitRequested:
				uploadStatus = .Uploading(uploadCount, true)

				let uploadMessage = (uploadCount == 1)
						? "uploading-message-singular" : "uploading-message-plural"

				showHUDWithMessage(
						LocalizedString("ddlform-screenlet", uploadMessage, self),
						details: LocalizedString("ddlform-screenlet", "uploading-details", self))

				return true

			default: ()
		}

		return false
	}

	private func retryUploads() {
		let failedDocumentFields = formView.record?.fields.filter() {
			if let fieldUploadStatus = ($0 as? DDLFieldDocument)?.uploadStatus {
				switch fieldUploadStatus {
					case .Failed(_): return true
					default: ()
				}
			}

			return false
		}

		if let failedUploads = failedDocumentFields {
			showHUDWithMessage(
				LocalizedString("ddlform-screenlet", "uploading-retry", self),
				details: LocalizedString("ddlform-screenlet", "uploading-retry-details", self))

			for failedDocumentField in failedUploads {
				uploadDocument(failedDocumentField as DDLFieldDocument)
			}

			uploadStatus = .Uploading(failedUploads.count, true)
		}
	}

	private func onUploadedBytes(document: DDLFieldDocument, bytes: UInt, sent: Int64, total: Int64) {
		switch uploadStatus {
			case .Uploading(_, _):
				formView.changeDocumentUploadStatus(document)

				delegate?.onDocumentUploadedBytes?(document, bytes: bytes, sent: sent, total: total)

			default: ()
		}
	}

}
