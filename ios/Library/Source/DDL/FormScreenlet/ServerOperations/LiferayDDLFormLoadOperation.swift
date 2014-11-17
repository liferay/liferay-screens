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


public class LiferayDDLFormLoadOperation: ServerOperation {

	public var structureId: Int64?
	public var userId: Int64?

	public var result: (record: DDLRecord, userId: Int64?)?


	internal override var hudLoadingMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "loading-message", self),
				details: LocalizedString("ddlform-screenlet", "loading-details", self))
	}
	internal override var hudFailureMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "loading-error", self), details: nil)
	}

	internal var formData: DDLFormData {
		return screenlet.screenletView as DDLFormData
	}


	//MARK: ServerOperation

	override func validateData() -> Bool {
		if structureId == nil {
			return false
		}

		return true
	}

	override internal func doRun(#session: LRSession) {
		let service = LRDDMStructureService_v62(session: session)

		result = nil

		let structureDataDictionary = service.getStructureWithStructureId(structureId!, error: &lastError)

		if lastError == nil {
			if let xsd = structureDataDictionary["xsd"]! as? String {
				var userId: Int64?

				if let userIdValue = structureDataDictionary["userId"]! as? Int {
					userId = Int64(userIdValue)
				}

				let record = DDLRecord(xsd: xsd, locale: NSLocale(localeIdentifier: NSLocale.currentLocaleString))

				result = (record, userId)
			}
			else {
				lastError = createError(cause: .InvalidServerResponse)
			}
		}
	}

}
