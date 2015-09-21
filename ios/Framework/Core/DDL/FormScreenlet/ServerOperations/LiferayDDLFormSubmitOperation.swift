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

	private let values: [String:AnyObject]
	private let viewModel: DDLFormViewModel?


	public convenience init(viewModel: DDLFormViewModel) {
		self.init(values: viewModel.values, viewModel: viewModel)
	}

	public convenience init(values: [String:AnyObject]) {
		self.init(values: values, viewModel: nil)
	}

	private init(values: [String:AnyObject], viewModel: DDLFormViewModel?) {
		self.values = values
		self.viewModel = viewModel

		super.init()
	}


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		var error = super.validateData()

		if error == nil {
			if (userId ?? 0) == 0 {
				return ValidationError("ddlform-screenlet", "undefined-user")
			}

			if groupId == nil {
				return ValidationError("ddlform-screenlet", "undefined-group")
			}

			if recordSetId == nil {
				return ValidationError("ddlform-screenlet", "undefined-recordset")
			}

			if values.isEmpty {
				return ValidationError("ddlform-screenlet", "undefined-values")
			}

			if let viewModel = viewModel {
				error = viewModel.validateForm(autoscroll: autoscrollOnValidation)
			}
		}

		return error
	}

	override public func doRun(#session: LRSession) {
		let service = LRDDLRecordService_v62(session: session)

		let serviceContextAttributes = [
			"userId": NSNumber(longLong: userId!),
			"scopeGroupId": NSNumber(longLong: groupId!)]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		resultRecordId = nil
		resultAttributes = nil

		var recordDictionary: [NSObject : AnyObject]?

		if recordId == nil {
			recordDictionary = service.addRecordWithGroupId(groupId!,
				recordSetId: recordSetId!,
				displayIndex: 0,
				fieldsMap: values,
				serviceContext: serviceContextWrapper,
				error: &lastError)
		}
		else {
			recordDictionary = service.updateRecordWithRecordId(recordId!,
				displayIndex: 0,
				fieldsMap: values,
				mergeFields: true,
				serviceContext: serviceContextWrapper,
				error: &lastError)
		}

		if lastError == nil {
			if let recordIdValue = recordDictionary?["recordId"] as? Int {
				resultRecordId = Int64(recordIdValue)
				resultAttributes = recordDictionary
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
	}

}
