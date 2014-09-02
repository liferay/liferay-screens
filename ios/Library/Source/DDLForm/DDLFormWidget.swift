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

@objc protocol DDLFormWidgetDelegate {

	optional func onFormLoaded(elements: [DDLElement])
	optional func onFormLoadError(error: NSError)

	optional func onFormSubmitted(elements: [DDLElement])
	optional func onFormSubmitError(error: NSError)

	optional func onDocumentUploadStarted(element:DDLElementDocument)
	optional func onDocumentUploadedBytes(element:DDLElementDocument, bytes: UInt, sent: Int64, total: Int64);
	optional func onDocumentUploadCompleted(element:DDLElementDocument, result:[String:AnyObject]);
	optional func onDocumentUploadError(element:DDLElementDocument, error: NSError);

}

@IBDesignable public class DDLFormWidget: BaseWidget, LRProgressDelegate {

	@IBInspectable var structureId: Int = 0
	@IBInspectable var groupId: Int = 0
	@IBInspectable var recordSetId: Int = 0
	@IBInspectable var recordId:Int = 0

	@IBInspectable var repositoryId:Int = 0
	@IBInspectable var folderId:Int = 0
	@IBInspectable var filePrefix = "form-file-"

	@IBInspectable var autoLoad:Bool = true
	@IBInspectable var autoscrollOnValidation:Bool = true
	@IBInspectable var showSubmitButton:Bool = true

	@IBOutlet var delegate: DDLFormWidgetDelegate?

	private var userId:Int = 0

	private var currentOperation:FormOperation = .Idle


	// MARK: BaseWidget METHODS

	override public func becomeFirstResponder() -> Bool {
		return formView().becomeFirstResponder()
	}

	override public func onCreate() {
		formView().showSubmitButton = showSubmitButton
	}

	override public func onShow() {
		if autoLoad && structureId != 0 {
			loadForm()
		}
	}

	override public func onCustomAction(actionName: String?, sender: AnyObject?) {
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

	override public func onServerError(error: NSError) {
		switch currentOperation {
			case .Submitting:
				delegate?.onFormSubmitError?(error)
				finishOperationWithMessage("An error happened submitting form")

			case .Loading:
				delegate?.onFormLoadError?(error)
				finishOperationWithMessage("An error happened loading form")

			case .Uploading(let document, _):
				document.uploadStatus = .Failed(error)

				formView().changeDocumentUploadStatus(document)

				if !document.validate() {
					formView().showElement(document)
				}

				delegate?.onDocumentUploadError?(document, error: error)

				showHUDWithMessage("An error happened uploading file", details: nil, secondsToShow: 3.0)

			default: ()
		}

		currentOperation = .Idle
	}

	override public func onServerResult(result: [String:AnyObject]) {
		switch currentOperation {
			case .Submitting:
				if let recordIdValue = result["recordId"]! as? Int {
					recordId = recordIdValue
				}
				finishOperationWithMessage("Form submitted")
				currentOperation = .Idle

			case .Loading:
				onFormLoadResult(result)
				currentOperation = .Idle

			case .Uploading(let document, let submitAfter):
				document.uploadStatus = .Uploaded(result)

				formView().changeDocumentUploadStatus(document)
				delegate?.onDocumentUploadCompleted?(document, result: result)

				currentOperation = .Idle

				if submitAfter {
					submitForm()
				}

			default: ()
		}

	}

	private func onFormLoadResult(result: [String:AnyObject]) {
		if let xml = result["xsd"]! as? String {
			if let userIdValue = result["userId"]! as? Int {
				userId = userIdValue
			}

			let parser = DDLParser(locale:NSLocale.currentLocale())

			parser.xml = xml

			if let elements = parser.parse() {
				formView().rows = elements

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

	public func loadForm() -> Bool {
		if LiferayContext.instance.currentSession == nil {
			println("ERROR: No session initialized. Can't load form without session")
			return false
		}

		if structureId == 0 {
			println("ERROR: StructureId is empty. Can't load form without it.")
			return false
		}

		startOperationWithMessage("Loading form...", details: "Wait a second...")

		let session = LRSession(session: LiferayContext.instance.currentSession)
		session.callback = self

		let service = LRDDMStructureService_v62(session: session)

		currentOperation = .Loading

		var outError: NSError?

		service.getStructureWithStructureId((structureId as NSNumber).longLongValue, error: &outError)

		if let error = outError {
			onFailure(error)
			return false
		}

		return true
	}

	public func submitForm() -> Bool {
		if LiferayContext.instance.currentSession == nil {
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

			case .Loading, .Submitting:
				println("ERROR: Cannot submit a form while it's being loading or submitting")
				return false

			default: ()
		}

		if !formView().validateForm(autoscroll:autoscrollOnValidation) {
			showHUDWithMessage("Some values are not valid", details: "Please, review your form", secondsToShow: 1.5)
			return false
		}

		currentOperation = .Submitting

		startOperationWithMessage("Submitting form...", details: "Wait a second...")

		let session = LRSession(session: LiferayContext.instance.currentSession)
		session.callback = self

		let service = LRDDLRecordService_v62(session: session)

		var outError: NSError?

		let groupIdToUse = (groupId != 0 ? groupId : LiferayContext.instance.groupId) as NSNumber

		let serviceContextAttributes = [
				"userId":userId,
				"scopeGroupId":groupIdToUse]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		if recordId == 0 {
			service.addRecordWithGroupId(
				groupIdToUse.longLongValue,
				recordSetId: (recordSetId as NSNumber).longLongValue,
				displayIndex: 0,
				fieldsMap: formView().values,
				serviceContext: serviceContextWrapper,
				error: &outError)
		}
		else {
			service.updateRecordWithRecordId(
				(recordId as NSNumber).longLongValue,
				displayIndex: 0,
				fieldsMap: formView().values,
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

	private func uploadDocument(document:DDLElementDocument) -> Bool {
		if LiferayContext.instance.currentSession == nil {
			println("ERROR: No session initialized. Can't upload a document without session")
			return false
		}

		if document.currentValue == nil {
			println("ERROR: No current value in the document. Can't upload a document without a value")
			return false
		}

		let repoId = (repositoryId != 0) ? repositoryId : groupId
		let fileName = "\(filePrefix)-\(NSUUID.UUID().UUIDString)"
		var size:Int64 = 0
		let stream = document.getStream(&size)
		let uploadData = LRUploadData(inputStream: stream, length:size, fileName: fileName, mimeType: document.mimeType)
		uploadData.progressDelegate = self

		let session = LRSession(session: LiferayContext.instance.currentSession)
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


	//MARK LRProgressDelegate

	public func onProgressBytes(bytes: UInt, sent: Int64, total: Int64) {
		switch currentOperation {
			case .Uploading(let document, _):
				document.uploadStatus = .Uploading(UInt(sent), UInt(total))
				formView().changeDocumentUploadStatus(document)

				delegate?.onDocumentUploadedBytes?(document, bytes: bytes, sent: sent, total: total)

			default: ()
		}
	}

	private func formView() -> DDLFormView {
		return widgetView as DDLFormView
	}

}

private enum FormOperation {
	case Idle
	case Loading
	case Submitting
	case Uploading(DDLElementDocument, Bool)
}
