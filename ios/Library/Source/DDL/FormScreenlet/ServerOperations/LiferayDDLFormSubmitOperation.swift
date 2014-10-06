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


public class LiferayDDLFormSubmitOperation: ServerOperation, NSCopying {

	public var groupId: Int64?
	public var userId: Int64?
	public var recordId: Int64?
	public var recordSetId: Int64?

	public var autoscrollOnValidation = true


	internal override var hudLoadingMessage: HUDMessage? {
		return ("Submitting form...", details: "Wait few seconds...")
	}
	internal override var hudFailureMessage: HUDMessage? {
		return ("An error happened submitting form", details: nil)
	}

	internal var formData: DDLFormData {
		return screenlet.screenletView as DDLFormData
	}


	//MARK: ServerOperation

	override func validateData() -> Bool {
		if userId == nil {
			return false
		}

		if groupId == nil {
			return false
		}

		if recordId != nil && recordSetId == nil {
			return false
		}

		if formData.values.isEmpty {
			return false
		}

		if !formData.validateForm(autoscroll: autoscrollOnValidation) {
			showHUD(message: "Some values are not valid",
					details: "Please, review your form",
					closeMode: .AutocloseDelayed(3.0, true),
					spinnerMode: .NoSpinner)

			return false
		}

		return true
	}

	override internal func doRun(#session: LRSession) {
		let service = LRDDLRecordService_v62(session: session)

		let serviceContextAttributes = [
				"userId": NSNumber(longLong: userId!),
				"scopeGroupId": NSNumber(longLong: groupId!)]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		var result: NSDictionary

		if recordId == nil {
			result = service.addRecordWithGroupId(groupId!,
					recordSetId: recordSetId!,
					displayIndex: 0,
					fieldsMap: formData.values,
					serviceContext: serviceContextWrapper,
					error: &lastError)
		}
		else {
			result = service.updateRecordWithRecordId(recordId!,
					displayIndex: 0,
					fieldsMap: formData.values,
					mergeFields: true,
					serviceContext: serviceContextWrapper,
					error: &lastError)
		}

		if lastError == nil {
			if let recordIdValue = result["recordId"]! as? Int {
				recordId = Int64(recordIdValue)
			}
			else {
				lastError = createError(cause: .InvalidServerResponse)
				recordId = nil
			}
		}
	}


	//MARK: NSCopying

	public func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferayDDLFormSubmitOperation(screenlet: self.screenlet)

		result.onComplete = self.onComplete

		result.groupId = self.groupId
		result.userId = self.userId
		result.recordId = self.recordId
		result.recordSetId = self.recordSetId

		result.autoscrollOnValidation = self.autoscrollOnValidation

		return result
	}


}
