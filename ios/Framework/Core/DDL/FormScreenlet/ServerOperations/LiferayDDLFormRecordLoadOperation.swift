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


public class LiferayDDLFormRecordLoadOperation: ServerOperation {

	public var recordId: Int64?

	public var resultRecord: [String:AnyObject]?
	public var resultRecordId: Int64?


	//MARK: ServerOperation

	override func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if recordId == nil {
				return ValidationError("ddlform-screenlet", "undefined-record")
			}
		}

		return error
	}

	override internal func doRun(#session: LRSession) {
		let service = LRScreensddlrecordService_v62(session: session)

		resultRecord = nil
		resultRecordId = nil

		let recordDictionary = service.getDdlRecordWithDdlRecordId(recordId!,
				locale: NSLocale.currentLocaleString,
				error: &lastError)

		if lastError == nil {
			if recordDictionary is [String:AnyObject] {
				resultRecord = recordDictionary as? [String:AnyObject]
				resultRecordId = self.recordId!
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
	}

}
