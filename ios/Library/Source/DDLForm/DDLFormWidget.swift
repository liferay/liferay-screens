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

}

@IBDesignable public class DDLFormWidget: BaseWidget {

	@IBInspectable var structureId: Int = 0
	@IBInspectable var groupId: Int = 0
	@IBInspectable var recordSetId: Int = 0
	@IBInspectable var recordId:Int = 0
	@IBInspectable var autoLoad:Bool = true
	@IBInspectable var autoscrollOnValidation:Bool = true

	@IBOutlet var delegate: DDLFormWidgetDelegate?

	private var userId:Int = 0

	private var submitting = false


	// MARK: BaseWidget METHODS

	override public func becomeFirstResponder() -> Bool {
		return formView().becomeFirstResponder()
	}

	override public func onCreate() {
	}

	override public func onShow() {
		if autoLoad && structureId != 0 {
			loadForm()
		}
	}

	override public func onCustomAction(actionName: String?, sender: UIControl?) {
		if actionName == "submit" {
			submitForm()
		}
	}

	override public func onServerError(error: NSError) {
		if submitting {
			delegate?.onFormSubmitError?(error)
			finishOperationWithMessage("An error happened submitting form")
		}
		else {
			delegate?.onFormLoadError?(error)
			finishOperationWithMessage("An error happened loading form")
		}
	}

	override public func onServerResult(result: [String:AnyObject]) {
		if submitting {
			submitting = false

			if let recordIdValue = result["recordId"]! as? Int {
				recordId = recordIdValue
			}

			finishOperationWithMessage("Form submitted")
		}
		else {
			onFormLoadResult(result)
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
			return false
		}

		if structureId == 0 {
			return false
		}

		startOperationWithMessage("Loading form...", details: "Wait a second...")

		let session = LRSession(session: LiferayContext.instance.currentSession)
		session.callback = self

		let service = LRDDMStructureService_v62(session: session)

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
			return false
		}

		if groupId == 0 || recordSetId == 0 {
			return false
		}

		if userId == 0 {
			return false
		}

		if !formView().validateForm(autoscroll:autoscrollOnValidation) {
			showHUDWithMessage("Some values are not valid", details: "Please, review your form", secondsToShow: 1.5)
			return false
		}

		submitting = true

		startOperationWithMessage("Submitting form...", details: "Wait a second...")

		let session = LRSession(session: LiferayContext.instance.currentSession)
		session.callback = self

		let service = LRDDLRecordService_v62(session: session)

		var outError: NSError?

		let serviceContextAttributes = ["userId":userId, "scopeGroupId":groupId]
		//uuid??

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		if recordId == 0 {
			service.addRecordWithGroupId(
				(groupId as NSNumber).longLongValue,
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

	private func formView() -> DDLFormView {
		return widgetView as DDLFormView
	}

}
