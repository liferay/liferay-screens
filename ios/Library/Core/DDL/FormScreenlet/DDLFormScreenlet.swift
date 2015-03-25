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
	@IBInspectable public var showSubmitButton: Bool = true {
		didSet {
			(screenletView as? DDLFormView)?.showSubmitButton = showSubmitButton
		}
	}

	@IBOutlet public var delegate: DDLFormScreenletDelegate?

	internal var formView: DDLFormView {
		return screenletView as DDLFormView
	}

	private var uploadStatus = UploadStatus.Idle

	private let LoadFormAction = "load-form"
	private let LoadRecordAction = "load-record"
	private let SubmitFormAction = "submit-form"


	//MARK: BaseScreenlet

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

	override internal func createInteractor(#name: String?, sender: AnyObject?) -> Interactor? {
		if name == nil {
			return nil
		}

		switch name! {
			case LoadFormAction:
				return createLoadFormInteractor()
			case LoadRecordAction: ()
				return createLoadRecordInteractor()
			case SubmitFormAction: ()
				return createSubmitFormInteractor()
			case UploadDocumentAction: ()
			default: ()
		}

		return nil
	}

	internal func createLoadFormInteractor() -> DDLFormLoadFormInteractor {
		let interactor = DDLFormLoadFormInteractor(screenlet: self)

		interactor.onSuccess = {
			if let resultRecordValue = interactor.resultRecord {
				self.userId = interactor.resultUserId ?? self.userId
				self.formView.record = resultRecordValue

				self.delegate?.onFormLoaded?(resultRecordValue)
			}
		}

		interactor.onFailure = {
			self.delegate?.onFormLoadError?($0)
			return
		}

		return interactor
	}

	internal func createSubmitFormInteractor() -> DDLFormSubmitFormInteractor? {
		if waitForInProgressUpload() {
			return nil
		}

		let interactor = DDLFormSubmitFormInteractor(screenlet: self)

		interactor.onSuccess = {
			if let resultRecordIdValue = interactor.resultRecordId {
				self.recordId = resultRecordIdValue
				self.formView.record!.recordId = resultRecordIdValue

				self.delegate?.onFormSubmitted?(self.formView.record!)
			}
		}

		interactor.onFailure = {
			self.delegate?.onFormSubmitError?($0)
			return
		}

		return interactor
	}

	internal func createLoadRecordInteractor() -> DDLFormLoadRecordInteractor {
		let interactor = DDLFormLoadRecordInteractor(screenlet: self)

		interactor.onSuccess = {
			// first set structure if loaded
			if let resultFormRecordValue = interactor.resultFormRecord {
				self.userId = interactor.resultFormUserId ?? self.userId
				self.formView.record = resultFormRecordValue

				self.delegate?.onFormLoaded?(resultFormRecordValue)
			}

			// then set data
			if let recordValue = self.formView.record {
				recordValue.updateCurrentValues(interactor.resultRecordData!)
				recordValue.recordId = interactor.resultRecordId!

				// Force didSet event
				self.formView.record = recordValue

				self.delegate?.onRecordLoaded?(recordValue)
			}
		}

		interactor.onFailure = {
			self.delegate?.onRecordLoadError?($0)
			return
		}

		return interactor
	}

	override internal func onAction(#name: String?, interactor: Interactor, sender: AnyObject?) -> Bool {
		if name!  == "upload-document" {
			if let document = sender as? DDLFieldDocument {
				return uploadDocument(document)
			}
		}

		return super.onAction(name: name, interactor: interactor, sender: sender)
	}


	//MARK: Public methods

	public func loadForm() -> Bool {
		return performAction(name: LoadFormAction)
	}

	public func loadRecord() -> Bool {
		return performAction(name: LoadRecordAction)
	}

	public func submitForm() -> Bool {
		return performAction(name: SubmitFormAction)
	}


	//MARK: Private methods

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
			if failedUploads.count > 0 {
				showHUDWithMessage(
					LocalizedString("ddlform-screenlet", "uploading-retry", self),
					details: LocalizedString("ddlform-screenlet", "uploading-retry-details", self))

				for failedDocumentField in failedUploads {
					performAction(name: UploadDocumentAction, sender: failedDocumentField)
				}

				uploadStatus = .Uploading(failedUploads.count, true)

				return
			}
		}

		assertionFailure("[ERROR] Inconsistency: No failedUploads but uploadState is failed")
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
