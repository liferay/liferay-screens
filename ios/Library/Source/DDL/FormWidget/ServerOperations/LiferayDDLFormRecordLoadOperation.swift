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


public class LiferayDDLFormRecordLoadOperation: ServerOperation, NSCopying {

	public var recordId: Int64?

	public var loadedRecord: [String:AnyObject]?


	internal override var hudLoadingMessage: HUDMessage? {
		return ("Loading data...", details: "Wait few seconds...")
	}
	internal override var hudFailureMessage: HUDMessage? {
		return ("An error happened loading the record", details: nil)
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

		loadedRecord = nil

		let result = service.getDdlRecordWithDdlRecordId(recordId!,
				locale: NSLocale.currentLocaleString(),
				error: &lastError)

		if lastError == nil {
			if result is [String:AnyObject] {
				loadedRecord = (result as [String:AnyObject])
			}
			else {
				lastError = createError(cause: .InvalidServerResponse)
			}
		}
	}


	//MARK: NSCopying

	public func copyWithZone(zone: NSZone) -> AnyObject {
		let result = LiferayDDLFormRecordLoadOperation(screenlet: self.screenlet)

		result.onComplete = self.onComplete

		result.recordId = self.recordId

		return result
	}


}
