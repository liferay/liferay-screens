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

	optional func onFormLoaded(elements: [DDLElement])
	optional func onFormLoadError(error: NSError)

	optional func onRecordLoaded(elements: [DDLElement])
	optional func onRecordLoadError(error: NSError)

	optional func onFormSubmitted(elements: [DDLElement])
	optional func onFormSubmitError(error: NSError)

	optional func onDocumentUploadStarted(element:DDLElementDocument)
	optional func onDocumentUploadedBytes(element:DDLElementDocument, bytes: UInt, sent: Int64,
			total: Int64);
	optional func onDocumentUploadCompleted(element:DDLElementDocument, result:[String:AnyObject]);
	optional func onDocumentUploadError(element:DDLElementDocument, error: NSError);

}


@IBDesignable public class DDLFormWidget: BaseWidget, LRProgressDelegate {

	private enum FormOperation {

		case Idle
		case LoadingForm
		case LoadingRecord(Bool)
		case Submitting
		case Uploading(DDLElementDocument, Bool)

	}

	@IBInspectable public var structureId = 0
	@IBInspectable public var groupId = 0
	@IBInspectable public var recordSetId = 0
	@IBInspectable public var recordId = 0

	@IBInspectable public var repositoryId = 0
	@IBInspectable public var folderId = 0
	@IBInspectable public var filePrefix = "form-file-"

	@IBInspectable public var autoLoad = true
	@IBInspectable public var autoscrollOnValidation = true
	@IBInspectable public var showSubmitButton = true

	@IBOutlet public var delegate: DDLFormWidgetDelegate?

	internal var formView: DDLFormView {
		return widgetView as DDLFormView
	}

	private var userId = 0
	private var currentOperation = FormOperation.Idle


	//MARK: BaseWidget

	override public func becomeFirstResponder() -> Bool {
		return formView.becomeFirstResponder()
	}

	override internal func onCreated() {
		formView.showSubmitButton = showSubmitButton
	}

	override internal func onShow() {
		if autoLoad && structureId != 0 {
			loadForm()
		}
	}

	override internal func onCustomAction(actionName: String?, sender: AnyObject?) {
		switch actionName! {
			case "submit-form":
				submitForm()
			case "upload-document":
				if let document = sender as? DDLElementDocument {
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
					formView.showElement(document)
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
					recordId = recordIdValue
				}
				finishOperationWithMessage("Form submitted")
				currentOperation = .Idle

			case .LoadingForm:
				onFormLoadResult(result)
				currentOperation = .Idle

			case .LoadingRecord(let includesForm):
				let responses = (result["result"] ?? []) as [AnyObject]

				if includesForm && responses.count > 1 {
					onFormLoadResult(responses[1] as [String:AnyObject])
				}

				onRecordLoadResult(responses[0] as [String:AnyObject])

				currentOperation = .Idle

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
		if LiferayContext.instance().currentSession == nil {
			println("ERROR: No session initialized. Can't load form without session")
			return false
		}

		if structureId == 0 {
			println("ERROR: StructureId is empty. Can't load form without it.")
			return false
		}

		startOperationWithMessage("Loading form...", details: "Wait a second...")

		let session = LRSession(session: LiferayContext.instance().currentSession)
		session.callback = self

		let service = LRDDMStructureService_v62(session: session)

		currentOperation = .LoadingForm

		var outError: NSError?

		service.getStructureWithStructureId((structureId as NSNumber).longLongValue,
				error: &outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		return true
	}

	public func loadRecord() -> Bool {
		if LiferayContext.instance().currentSession == nil {
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

		currentOperation = .LoadingRecord(formView.rows.isEmpty)

		startOperationWithMessage("Loading record...", details: "Wait a second...")

		let session = LRBatchSession(session: LiferayContext.instance().currentSession)
		session.callback = self

		var outError: NSError?

		let ddlService = LRMobilewidgetsddlrecordService_v62(session: session)

		ddlService.getDdlRecordValuesWithDdlRecordId((recordId as NSNumber).longLongValue,
				locale: NSLocale.currentLocaleString(),
				error: &outError)

		if formView.rows.isEmpty {
			let structureService = LRDDMStructureService_v62(session: session)

			structureService.getStructureWithStructureId((structureId as NSNumber).longLongValue,
					error: &outError)
		}

		session.invoke(&outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		return true
	}


	public func submitForm() -> Bool {
		if LiferayContext.instance().currentSession == nil {
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

		let session = LRSession(session: LiferayContext.instance().currentSession)
		session.callback = self

		let service = LRDDLRecordService_v62(session: session)

		var outError: NSError?

		let groupIdToUse = (groupId != 0 ? groupId : LiferayContext.instance().groupId) as NSNumber

		let serviceContextAttributes = [
				"userId":userId,
				"scopeGroupId":groupIdToUse]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		if recordId == 0 {
			service.addRecordWithGroupId(
				groupIdToUse.longLongValue,
				recordSetId: (recordSetId as NSNumber).longLongValue,
				displayIndex: 0,
				fieldsMap: formView.values,
				serviceContext: serviceContextWrapper,
				error: &outError)
		}
		else {
			service.updateRecordWithRecordId(
				(recordId as NSNumber).longLongValue,
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

	private func onFormLoadResult(result: [String:AnyObject]) {
		if let xml = result["xsd"]! as? String {
			if let userIdValue = result["userId"]! as? Int {
				userId = userIdValue
			}

			let parser = DDLParser(locale:NSLocale.currentLocale())

			parser.xml = xml

			if let elements = parser.parse() {
				formView.rows = elements

				delegate?.onFormLoaded?(elements)

				finishOperationWithMessage("Form loaded")
			}
			else {
				//TODO error
			}
		}
		else {
			//TODO error
		}
	}

	private func onRecordLoadResult(result: [String:AnyObject]) {
		for (index,element) in enumerate(formView.rows) {
			let elementValue = (result[element.name] ?? nil) as? String
			if let elementStringValue = elementValue {
				element.currentStringValue = elementStringValue
				formView.rows[index] = element
			}
		}
	}

	private func uploadDocument(document:DDLElementDocument) -> Bool {
		if LiferayContext.instance().currentSession == nil {
			println("ERROR: No session initialized. Can't upload a document without session")
			return false
		}

		if document.currentValue == nil {
			println("ERROR: No current value in the document. " +
					"Can't upload a document without a value")
			return false
		}

		let repoId = (repositoryId != 0) ? repositoryId : groupId
		let fileName = "\(filePrefix)-\(NSUUID.UUID().UUIDString)"
		var size:Int64 = 0
		let stream = document.getStream(&size)
		let uploadData = LRUploadData(
				inputStream: stream,
				length: size,
				fileName: fileName,
				mimeType: document.mimeType)
		uploadData.progressDelegate = self

		let session = LRSession(session: LiferayContext.instance().currentSession)
		session.callback = self

		let service = LRDLAppService_v62(session: session)

		currentOperation = .Uploading(document, false)

		var outError: NSError?

		service.addFileEntryWithRepositoryId(
			(repoId as NSNumber).longLongValue,
			folderId: (folderId as NSNumber).longLongValue,
			sourceFileName: fileName, mimeType: document.mimeType,
			title: fileName, description: "", changeLog: "Uploaded from Liferay Screens app",
			file: uploadData, serviceContext: nil, error: &outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		delegate?.onDocumentUploadStarted?(document)

		return true
	}

}
