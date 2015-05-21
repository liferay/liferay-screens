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

	public var resultRecord: DDLRecord?
	public var resultUserId: Int64?


	override public var hudLoadingMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "loading-message", self),
				details: LocalizedString("ddlform-screenlet", "loading-details", self))
	}
	override public var hudFailureMessage: HUDMessage? {
		return (LocalizedString("ddlform-screenlet", "loading-error", self), details: nil)
	}

	//MARK: ServerOperation

	override func validateData() -> Bool {
		var valid = super.validateData()

		valid = valid && (structureId != nil)

		return valid
	}

	override internal func doRun(#session: LRSession) {
		let service = LRDDMStructureService_v62(session: session)

		resultRecord = nil
		resultUserId = nil

		let structureDataDictionary = service.getStructureWithStructureId(structureId!,
				error: &lastError)

		if lastError == nil {
			if let xsd = structureDataDictionary["xsd"]! as? String {
				if let userIdValue = structureDataDictionary["userId"]! as? Int {
					resultUserId = Int64(userIdValue)
				}

				resultRecord = DDLRecord(
						xsd: xsd,
						locale: NSLocale(localeIdentifier: NSLocale.currentLocaleString))
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
			}
		}
	}

}
