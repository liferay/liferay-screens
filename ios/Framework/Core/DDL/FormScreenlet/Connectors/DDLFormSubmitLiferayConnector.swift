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
import LRMobileSDK


public class DDLFormSubmitLiferayConnector: ServerConnector {

	public var groupId: Int64?
	public var userId: Int64?
	public var recordId: Int64?
	public var recordSetId: Int64?

	public var autoscrollOnValidation = true

	public var resultRecordId: Int64?
	public var resultAttributes: NSDictionary?

	private let values: [String:AnyObject]
	private let viewModel: DDLFormViewModel?


	public init(values: [String:AnyObject], viewModel: DDLFormViewModel?) {
		self.values = values
		self.viewModel = viewModel

		super.init()
	}


	//MARK: ServerConnector

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		guard error == nil else {
			return error
		}
		guard (userId ?? 0) != 0 else {
			return ValidationError("ddlform-screenlet", "undefined-user")
		}
		guard groupId != nil else {
			return ValidationError("ddlform-screenlet", "undefined-group")
		}
		guard recordSetId != nil else {
			return ValidationError("ddlform-screenlet", "undefined-recordset")
		}
		guard !values.isEmpty else {
			return ValidationError("ddlform-screenlet", "undefined-values")
		}

		return viewModel?.validateForm(autoscroll: autoscrollOnValidation)
	}

}


public class Liferay62DDLFormSubmitConnector: DDLFormSubmitLiferayConnector {

	override public func doRun(session session: LRSession) {
		let service = LRDDLRecordService_v62(session: session)

		let serviceContextAttributes = [
			"userId": NSNumber(longLong: userId!),
			"scopeGroupId": NSNumber(longLong: groupId!)]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		do {
			let recordDictionary: [NSObject : AnyObject]?

			if recordId == nil {
				recordDictionary = try service.addRecordWithGroupId(groupId!,
					recordSetId: recordSetId!,
					displayIndex: 0,
					fieldsMap: values,
					serviceContext: serviceContextWrapper)
			}
			else {
				recordDictionary = try service.updateRecordWithRecordId(recordId!,
					displayIndex: 0,
					fieldsMap: values,
					mergeFields: true,
					serviceContext: serviceContextWrapper)
			}

			if let recordIdValue = recordDictionary?["recordId"]?.longLongValue {
				resultRecordId = recordIdValue
				resultAttributes = recordDictionary
				lastError = nil
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
		catch let error as NSError {
			lastError = error
			resultRecordId = nil
			resultAttributes = nil
		}
	}
	
}


public class Liferay70DDLFormSubmitConnector: DDLFormSubmitLiferayConnector {

	override public func doRun(session session: LRSession) {
		let service = LRDDLRecordService_v7(session: session)

		let serviceContextAttributes = [
			"userId": NSNumber(longLong: userId!),
			"scopeGroupId": NSNumber(longLong: groupId!)]

		let serviceContextWrapper = LRJSONObjectWrapper(JSONObject: serviceContextAttributes)

		do {
			let recordDictionary: [NSObject : AnyObject]?

			if recordId == nil {
				recordDictionary = try service.addRecordWithGroupId(groupId!,
					recordSetId: recordSetId!,
					displayIndex: 0,
					fieldsMap: values,
					serviceContext: serviceContextWrapper)
			}
			else {
				recordDictionary = try service.updateRecordWithRecordId(recordId!,
					displayIndex: 0,
					fieldsMap: values,
					mergeFields: false,
					serviceContext: serviceContextWrapper)
			}

			if let recordIdValue = recordDictionary?["recordId"]?.longLongValue {
				resultRecordId = recordIdValue
				resultAttributes = recordDictionary
				lastError = nil
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
		catch let error as NSError {
			lastError = error
			resultRecordId = nil
			resultAttributes = nil
		}
	}
	
}
