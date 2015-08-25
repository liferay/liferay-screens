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


public class LiferayDDLFormSubmitOperation: ServerOperation {

	public var groupId: Int64?
	public var userId: Int64?
	public var recordId: Int64?
	public var recordSetId: Int64?

	public var autoscrollOnValidation = true

	public var resultRecordId: Int64?
	public var resultAttributes: NSDictionary?


	override public var hudLoadingMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "submitting-message", self),
				details: LocalizedString("ddlform-screenlet", "submitting-details", self))
	}

	override public var hudSuccessMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "submitted", self), details: nil)
	}

	override public var hudFailureMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "submitting-error", self), details: nil)
	}

	internal let viewModel: DDLFormViewModel


	public init(viewModel: DDLFormViewModel) {
		self.viewModel = viewModel

		super.init()
	}


	//MARK: ServerOperation

	override func validateData() -> ValidationError? {
		var error = super.validateData()

		if error == nil {
			if (userId ?? 0) == 0 {
				return ValidationError(message: "User is undefined")
			}

			if groupId == nil {
				return ValidationError(message: "Group is undefined")
			}

			if recordId != nil && recordSetId == nil {
				return ValidationError(message: "Record set is undefined")
			}

			if viewModel.values.isEmpty {
				return ValidationError(message: "Values are empty")
			}

			error = viewModel.validateForm(autoscroll: autoscrollOnValidation)
		}

		return error
	}

	override internal func doRun(#session: LRSession) {
		let service = LRDDLRecordService_v62(session: session)

		let serviceContextAttributes = [
				"userId": NSNumber(longLong: userId!),
				"scopeGroupId": NSNumber(longLong: groupId!)]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		resultRecordId = nil
		resultAttributes = nil

		var recordDictionary: NSDictionary

		if recordId == nil {
			recordDictionary = service.addRecordWithGroupId(groupId!,
					recordSetId: recordSetId!,
					displayIndex: 0,
					fieldsMap: viewModel.values,
					serviceContext: serviceContextWrapper,
					error: &lastError)
		}
		else {
			recordDictionary = service.updateRecordWithRecordId(recordId!,
					displayIndex: 0,
					fieldsMap: viewModel.values,
					mergeFields: true,
					serviceContext: serviceContextWrapper,
					error: &lastError)
		}

		if lastError == nil {
			if let recordIdValue = recordDictionary["recordId"]! as? Int {
				resultRecordId = Int64(recordIdValue)
				resultAttributes = recordDictionary
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
	}

}
