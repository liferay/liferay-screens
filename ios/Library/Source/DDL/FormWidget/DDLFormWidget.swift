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
			total: Int64);
	optional func onDocumentUploadCompleted(field:DDLFieldDocument, result:[String:AnyObject]);
	optional func onDocumentUploadError(field:DDLFieldDocument, error: NSError);

}


@IBDesignable public class DDLFormWidget: BaseWidget, LRProgressDelegate {

	private enum FormOperation {

		case Idle
		case LoadingForm
		case LoadingRecord(Bool)
		case Submitting
		case Uploading(DDLFieldDocument, Bool)

	}

	@IBInspectable public var structureId: Int64 = 0
	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var recordSetId: Int64 = 0
	@IBInspectable public var recordId: Int64 = 0

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

	private var userId: Int64 = 0
	private var currentOperation = FormOperation.Idle


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

	override internal func onServerError(error: NSError) {
		switch currentOperation {
			case .Submitting:
				delegate?.onFormSubmitError?(error)
				finishOperationWithError(error, message:"An error happened submitting form")

			case .LoadingForm:
				delegate?.onFormLoadError?(error)
				finishOperationWithError(error, message:"An error happened loading form")

			case .LoadingRecord:
				delegate?.onRecordLoadError?(error)
				finishOperationWithError(error, message:"An error happened loading the record")

			case .Uploading(let document, _):
				document.uploadStatus = .Failed(error)

				formView.changeDocumentUploadStatus(document)

				if !document.validate() {
					formView.showField(document)
				}

				delegate?.onDocumentUploadError?(document, error: error)

				finishOperationWithError(error, message:"An error happened uploading file")

			default: ()
		}

		currentOperation = .Idle
	}

	override internal func onServerResult(result: [String:AnyObject]) {
		switch currentOperation {
			case .Submitting:
				if let recordIdValue = result["recordId"]! as? Int {
					recordId = Int64(recordIdValue)
					formView.record!.recordId = recordId
				}

				finishOperation()
				currentOperation = .Idle

			case .LoadingForm:
				if onFormLoadResult(result) {
					finishOperation()
					currentOperation = .Idle
				}

			case .LoadingRecord(let includesForm):
				let responses = (result["result"] ?? []) as [AnyObject]

				var success = true

				if includesForm && responses.count > 1 {
					success = onFormLoadResult(responses[1] as [String:AnyObject])
				}

				if success {
					onRecordLoadResult(responses[0] as [String:AnyObject])
					formView.record!.recordId = recordId

					finishOperation()
					currentOperation = .Idle
				}

			case .Uploading(let document, let submitAfter):
				document.uploadStatus = .Uploaded(result)

				formView.changeDocumentUploadStatus(document)
				delegate?.onDocumentUploadCompleted?(document, result: result)

				currentOperation = .Idle

				if submitAfter {
					submitForm()
				}

			default: ()
		}

	}


	//MARK: LRProgressDelegate

	public func onProgressBytes(bytes: UInt, sent: Int64, total: Int64) {
		switch currentOperation {
			case .Uploading(let document, _):
				document.uploadStatus = .Uploading(UInt(sent), UInt(total))
				formView.changeDocumentUploadStatus(document)

				delegate?.onDocumentUploadedBytes?(document, bytes: bytes, sent: sent, total: total)

			default: ()
		}
	}


	//MARK: Public methods

	public func loadForm() -> Bool {
		if !SessionContext.hasSession {
			println("ERROR: No session initialized. Can't load form without session")

			return false
		}

		if structureId == 0 {
			println("ERROR: StructureId is empty. Can't load form without it.")

			return false
		}

		startOperationWithMessage("Loading form...", details: "Wait a second...")

		let session = SessionContext.createSessionFromCurrentSession()!
		session.callback = self

		let service = LRDDMStructureService_v62(session: session)

		currentOperation = .LoadingForm

		var outError: NSError?

		service.getStructureWithStructureId(structureId, error: &outError)

		if let error = outError {
			onFailure(error)

			return false
		}

		return true
	}

	public func loadRecord() -> Bool {
		if !SessionContext.hasSession {
			println("ERROR: No session initialized. Can't load a record without session")
			return false
		}

		if structureId == 0 {
			println("ERROR: StructureId is empty. Can't load a record without it.")
			return false
		}

		if recordId == 0 {
			println("ERROR: RecordId is empty. Can't load a record without it.")
			return false
		}

		currentOperation = .LoadingRecord(formView.isRecordEmpty)

		startOperationWithMessage("Loading record...", details: "Wait a second...")

		let session = SessionContext.createBatchSessionFromCurrentSession()!
		session.callback = self

		var outError: NSError?

		let ddlService = LRMobilewidgetsddlrecordService_v62(session: session)

		ddlService.getDdlRecordWithDdlRecordId(recordId,
				locale: NSLocale.currentLocaleString(),
				error: &outError)

		if formView.isRecordEmpty {
			let structureService = LRDDMStructureService_v62(session: session)

			structureService.getStructureWithStructureId(structureId, error: &outError)
		}

		session.invoke(&outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		return true
	}


	public func submitForm() -> Bool {
		if !SessionContext.hasSession {
			println("ERROR: No session initialized. Can't submit form without session")
			return false
		}

		if recordSetId == 0 {
			println("ERROR: RecordSetId is empty. Can't submit form without it.")
			return false
		}

		if userId == 0 {
			println("ERROR: UserId is empty. Can't submit form without loading the form before")
			return false
		}

		switch currentOperation {
			case .Uploading(let doc, _):
				currentOperation = .Uploading(doc, true)
				showHUDWithMessage("Uploading file...", details: "Wait a second...")
				return true

			case .LoadingForm, .LoadingRecord(_), .Submitting:
				println("ERROR: Cannot submit a form while it's being loading or submitting")
				return false

			default: ()
		}

		if !formView.validateForm(autoscroll:autoscrollOnValidation) {
			showHUDWithMessage("Some values are not valid",
					details: "Please, review your form",
					closeMode:.AutocloseDelayed(3.0, true),
					spinnerMode:.NoSpinner)
			return false
		}

		currentOperation = .Submitting

		startOperationWithMessage("Submitting form...", details: "Wait a second...")

		let session = SessionContext.createSessionFromCurrentSession()!
		session.callback = self

		let service = LRDDLRecordService_v62(session: session)

		var outError: NSError?

		let groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId

		let serviceContextAttributes = [
				"userId": NSNumber(longLong: userId),
				"scopeGroupId": NSNumber(longLong: groupId)]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		if recordId == 0 {
			service.addRecordWithGroupId(groupId,
					recordSetId: recordSetId,
					displayIndex: 0,
					fieldsMap: formView.values,
					serviceContext: serviceContextWrapper,
					error: &outError)
		}
		else {
			service.updateRecordWithRecordId(recordId,
					displayIndex: 0,
					fieldsMap: formView.values,
					mergeFields: true,
					serviceContext: serviceContextWrapper,
					error: &outError)
		}

		if let error = outError {
			onFailure(error)
			return false
		}

		return true
	}


	//MARK: Private methods

	private func onFormLoadResult(result: [String:AnyObject]) -> Bool {
		if let xsd = result["xsd"]! as? String {
			if let userIdValue = result["userId"]! as? Int {
				userId = Int64(userIdValue)
			}

			formView.record = DDLRecord(xsd: xsd, locale: NSLocale.currentLocale())

			if !formView.record!.fields.isEmpty {
				delegate?.onFormLoaded?(formView.record!)

				return true
			}
		}

		onFailure(createError(cause: .InvalidServerResponse, userInfo: ["ServerResponse" : result]))

		return false
	}

	private func onRecordLoadResult(result: [String:AnyObject]) {
		if let recordValue = formView.record {
			recordValue.updateCurrentValues(result)
			formView.onChangedRecord()
		}
	}

	private func uploadDocument(document:DDLFieldDocument) -> Bool {
		if !SessionContext.hasSession {
			println("ERROR: No session initialized. Can't upload a document without session")
			return false
		}

		if document.currentValue == nil {
			println("ERROR: No current value in the document. " +
					"Can't upload a document without a value")
			return false
		}

		let groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId
		let repositoryId = (self.repositoryId != 0) ? self.repositoryId : groupId

		let fileName = "\(filePrefix)-\(NSUUID.UUID().UUIDString)"
		var size:Int64 = 0
		let stream = document.getStream(&size)
		let uploadData = LRUploadData(
				inputStream: stream,
				length: size,
				fileName: fileName,
				mimeType: document.mimeType)
		uploadData.progressDelegate = self

		let session = SessionContext.createSessionFromCurrentSession()!
		session.callback = self

		let service = LRDLAppService_v62(session: session)

		currentOperation = .Uploading(document, false)

		var outError: NSError?

		service.addFileEntryWithRepositoryId(repositoryId,
				folderId: folderId,
				sourceFileName: fileName,
				mimeType: document.mimeType,
				title: fileName,
				description: "",
				changeLog: "Uploaded from Liferay Screens app",
				file: uploadData,
				serviceContext: nil,
				error: &outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		delegate?.onDocumentUploadStarted?(document)

		return true
	}

}
