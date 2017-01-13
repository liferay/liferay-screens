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


@objc public protocol DDLFormScreenletDelegate : BaseScreenletDelegate {

	/// Called when the form is loaded. The second parameter (record) 
	/// contains only field definitions.
	///
	/// - Parameters:
	///   - screenlet
	///   - record: record definitions.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onFormLoaded record: DDLRecord)

	/// Called when an error occurs while loading the form.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error when loading the form.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onFormLoadError error: NSError)

	/// Called when a form with values loads. The second parameter (record) 
	/// contains field definitions and values. The method onFormLoadResult 
	/// is called before onRecordLoaded.
	///
	/// - Parameters:
	///   - screenlet
	///   - record: record definitions.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onRecordLoaded record: DDLRecord)

	/// Called when an error occurs while loading a record.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error when loading the record.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onRecordLoadError error: NSError)

	/// Called when the form values are successfully submitted to the server.
	///
	/// - Parameters:
	///   - screenlet
	///   - record: record definitions.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitted record: DDLRecord)

	/// Called when an error occurs while submitting the form.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - error: error when submitting the form.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onFormSubmitError error: NSError)

	/// Called when the upload of a Documents and Media field begins.
	///
	/// - Parameters:
	///   - screenlet
	///   - field: document field of the form.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onDocumentFieldUploadStarted field: DDMFieldDocument)

	/// Called when a block of bytes in a Documents and Media field is uploaded.
	/// This method is intended to track progress of the uploads.
	///
	/// - Parameters:
	///   - screenlet
	///   - field: document field of the form.
	///   - bytes: uploaded bytes.
	///   - total: total bytes.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDMFieldDocument,
			uploadedBytes bytes: UInt64,
			totalBytes total: UInt64)

	/// Called when a Documents and Media field upload is completed.
	///
	/// - Parameters:
	///   - screenlet
	///   - field: document field of the form.
	///   - result: document data attributes.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDMFieldDocument,
			uploadResult result: [String:AnyObject])

	/// Called when an error occurs in the Documents and Media upload process.
	/// The NSError object describes the error.
	///
	/// - Parameters:
	///   - screenlet
	///   - field: document field of the form.
	///   - error: error when uploading the document.
	optional func screenlet(screenlet: DDLFormScreenlet,
			onDocumentField field: DDMFieldDocument,
			uploadError error: NSError)

}


public class DDLFormScreenlet: BaseScreenlet {

	private enum UploadStatus {
		case Idle
		case Uploading(Int, Bool)
		case Failed(NSError)
	}

	public class var LoadFormAction: String { return "load-form" }
	public class var LoadRecordAction: String { return "load-record" }
	public class var SubmitFormAction: String { return "submit-form" }
	public class var UploadDocumentAction: String { return "upload-document" }


	//MARK: Inspectables

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

	@IBInspectable public var editable: Bool = true {
		didSet {
			screenletView?.editable = editable
		}
	}

	@IBInspectable public var offlinePolicy: String? = CacheStrategyType.RemoteFirst.rawValue


	public var ddlFormDelegate: DDLFormScreenletDelegate? {
		return delegate as? DDLFormScreenletDelegate
	}

	public var viewModel: DDLFormViewModel {
		return screenletView as! DDLFormViewModel
	}

	public var isFormLoaded: Bool {
		return !((screenletView as? DDLFormView)?.isRecordEmpty ?? true)
	}

	internal var formView: DDLFormView {
		return screenletView as! DDLFormView
	}

	private var uploadStatus = UploadStatus.Idle


	//MARK: BaseScreenlet

	override public func onCreated() {
		formView.showSubmitButton = showSubmitButton
		screenletView?.editable = self.editable
	}

	override public func onShow() {
		if autoLoad {
			if recordId != 0 {
				loadRecord()
			}
			else {
				loadForm()
			}
		}
	}

	override public func createInteractor(name name: String, sender: AnyObject?) -> Interactor? {
		switch name {
			case DDLFormScreenlet.LoadFormAction:
				return createLoadFormInteractor()
			case DDLFormScreenlet.LoadRecordAction:
				return createLoadRecordInteractor()
			case DDLFormScreenlet.SubmitFormAction:
				return createSubmitFormInteractor()
			case DDLFormScreenlet.UploadDocumentAction:
				if sender is DDMFieldDocument {
					return createUploadDocumentInteractor(sender as! DDMFieldDocument)
				}
			default: ()
		}

		return nil
	}

	override public func onAction(name name: String, interactor: Interactor, sender: AnyObject?) -> Bool {
		let result = super.onAction(name: name, interactor: interactor, sender: sender)

		if result && name == DDLFormScreenlet.UploadDocumentAction {
			let uploadInteractor = interactor as! DDLFormUploadDocumentInteractor

			ddlFormDelegate?.screenlet?(self,
					onDocumentFieldUploadStarted: uploadInteractor.document)

			switch uploadStatus {
				case .Uploading(let uploadCount, let submitRequested):
					uploadStatus = .Uploading(uploadCount + 1, submitRequested)

				default:
					uploadStatus = .Uploading(1, false)
			}

		}

		return result
	}


	//MARK: Internal methods

	internal func createLoadFormInteractor() -> DDLFormLoadFormInteractor {
		let interactor = DDLFormLoadFormInteractor(screenlet: self)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultRecordValue = interactor.resultRecord {
				self.userId = interactor.resultUserId ?? self.userId
				self.formView.record = resultRecordValue

				self.ddlFormDelegate?.screenlet?(self,
						onFormLoaded: resultRecordValue)
			}
		}

		interactor.onFailure = {
			self.ddlFormDelegate?.screenlet?(self, onFormLoadError: $0)
		}

		return interactor
	}

	internal func createSubmitFormInteractor() -> DDLFormSubmitFormInteractor? {
		let interactor = DDLFormSubmitFormInteractor(screenlet: self, record: self.formView.record!)

		if waitForInProgressUpload(interactor) {
			return nil
		}
		if self.formView.record == nil {
			return nil
		}

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			if let resultRecordIdValue = interactor.resultRecordId {
				self.recordId = resultRecordIdValue
				self.formView.record!.recordId = resultRecordIdValue

				self.ddlFormDelegate?.screenlet?(self,
						onFormSubmitted: self.formView.record!)
			}
		}

		interactor.onFailure = {
			self.ddlFormDelegate?.screenlet?(self, onFormSubmitError: $0)
		}

		return interactor
	}

	internal func createLoadRecordInteractor() -> DDLFormLoadRecordInteractor {
		let neededStructureId: Int64? =
			formView.isRecordEmpty ? structureId : nil

		let interactor = DDLFormLoadRecordInteractor(screenlet: self, recordId: self.recordId, structureId: neededStructureId)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			// first set structure if loaded
			if let resultFormRecordValue = interactor.resultFormRecord {
				self.userId = interactor.resultFormUserId ?? self.userId
				self.formView.record = resultFormRecordValue

				self.ddlFormDelegate?.screenlet?(self,
						onFormLoaded: resultFormRecordValue)
			}

			// then set data
			if let recordValue = self.formView.record,
					data = interactor.resultRecordData,
					recordId = interactor.resultRecordId
				where interactor.lastError == nil {

				recordValue.updateCurrentValues(values: data)
				recordValue.recordId = recordId

				self.formView.refresh()

				self.ddlFormDelegate?.screenlet?(self, onRecordLoaded: recordValue)
			}
			else {
				self.ddlFormDelegate?.screenlet?(self,
						onRecordLoadError: interactor.lastError
								?? NSError.errorWithCause(.InvalidServerResponse,
										message: "Could not load record data."))
			}
		}

		interactor.onFailure = {
			self.ddlFormDelegate?.screenlet?(self, onRecordLoadError: $0)
		}

		return interactor
	}

	internal func createUploadDocumentInteractor(
			document: DDMFieldDocument)
			-> DDLFormUploadDocumentInteractor {

		func onUploadedBytes(document: DDMFieldDocument, sent: UInt64, total: UInt64) {
			switch uploadStatus {
				case .Uploading(_, _):
					formView.changeDocumentUploadStatus(document)

					ddlFormDelegate?.screenlet?(self,
						onDocumentField: document,
						uploadedBytes: sent,
						totalBytes: total)

				default: ()
			}
		}

		let interactor = DDLFormUploadDocumentInteractor(
				screenlet: self,
				document: document,
				onProgressClosure: onUploadedBytes)

		interactor.cacheStrategy = CacheStrategyType(rawValue: self.offlinePolicy ?? "") ?? .RemoteFirst

		interactor.onSuccess = {
			self.formView.changeDocumentUploadStatus(interactor.document)

			self.ddlFormDelegate?.screenlet?(self,
					onDocumentField: interactor.document,
					uploadResult: interactor.resultResponse!)

			// set new status
			switch self.uploadStatus {
				case .Uploading(let uploadCount, let submitRequest)
				where uploadCount > 1:
					// more than one upload in progress
					self.uploadStatus = .Uploading(uploadCount - 1, submitRequest)

				case .Uploading(let uploadCount, let submitRequested)
				where uploadCount == 1:
					self.uploadStatus = .Idle

					if submitRequested {
						// waiting for upload completion to submit the form
						self.submitForm()
				}

				default: ()
			}
		}

		interactor.onFailure = {
			self.formView.changeDocumentUploadStatus(interactor.document)

			if !document.validate() {
				self.formView.showField(interactor.document)
			}

			self.ddlFormDelegate?.screenlet?(self,
					onDocumentField: interactor.document,
					uploadError: $0)

			self.uploadStatus = .Failed($0)
		}

		return interactor
	}


	//MARK: Public methods

	/// Performs LoadFormAction
	public func loadForm() -> Bool {
		return performAction(name: DDLFormScreenlet.LoadFormAction)
	}

	/// Clears record definitions and refresh the form.
	public func clearForm() {
		formView.record?.clearValues()
		formView.refresh()
	}

	/// Performs LoadRecordAction.
	public func loadRecord() -> Bool {
		return performAction(name: DDLFormScreenlet.LoadRecordAction)
	}

	/// Performs SubmitFormAction.
	public func submitForm() -> Bool {
		return performAction(name: DDLFormScreenlet.SubmitFormAction)
	}


	//MARK: Private methods

	private func waitForInProgressUpload(interactor: Interactor) -> Bool {
		switch uploadStatus {
			case .Failed(_):
				retryUploads(interactor)

				return true

			case .Uploading(_, let submitRequested)
			where submitRequested:
				return true

			case .Uploading(let uploadCount, let submitRequested)
			where !submitRequested:
				uploadStatus = .Uploading(uploadCount, true)

				let uploadMessage = (uploadCount == 1)
						? "uploading-message-singular" : "uploading-message-plural"

				showHUDWithMessage(LocalizedString("ddlform-screenlet", key: uploadMessage, obj: self),
					forInteractor: interactor)

				return true

			default: ()
		}

		return false
	}

	private func retryUploads(interactor: Interactor) {
		let failedDocumentFields = formView.record?.fields.filter() {
			if let fieldUploadStatus = ($0 as? DDMFieldDocument)?.uploadStatus {
				switch fieldUploadStatus {
					case .Failed(_): return true
					default: ()
				}
			}

			return false
		}

		if let failedUploads = failedDocumentFields {
			if failedUploads.count > 0 {
				showHUDWithMessage(LocalizedString("ddlform-screenlet", key: "uploading-retry", obj: self),
					forInteractor: interactor)

				for failedDocumentField in failedUploads {
					performAction(
						name: DDLFormScreenlet.UploadDocumentAction,
						sender: failedDocumentField)
				}

				uploadStatus = .Uploading(failedUploads.count, true)

				return
			}
		}

		fatalError("[ERROR] Inconsistency: No failedUploads but uploadState is failed")
	}

}
