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

	public var result: (record: [String:AnyObject], recordId: Int64)?


	internal override var hudLoadingMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "loading-record-message", self),
				details: LocalizedString("ddlform-screenlet", "loading-record-details", self))
	}
	internal override var hudFailureMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "loading-record-error", self), details: nil)
	}

	internal var formData: DDLFormData {
		return screenlet.screenletView as DDLFormData
	}


	//MARK: ServerOperation

	override func validateData() -> Bool {
		if recordId == nil {
			return false
		}

		return true
	}

	override func preRun() -> Bool {
		if formData.isRecordEmpty {
			return false
		}

		return true
	}

	override internal func doRun(#session: LRSession) {
		let service = LRMobilewidgetsddlrecordService_v62(session: session)

		result = nil

		let recordDictionary = service.getDdlRecordWithDdlRecordId(recordId!,
				locale: NSLocale.currentLocaleString,
				error: &lastError)

		if lastError == nil {
			if recordDictionary is [String:AnyObject] {
				result = (recordDictionary as [String:AnyObject], self.recordId!)
			}
			else {
				lastError = createError(cause: .InvalidServerResponse)
			}
		}
	}

}
