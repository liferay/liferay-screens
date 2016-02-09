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


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if structureId == nil {
				return ValidationError("ddlform-screenlet", "undefined-structure")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		let service = LRDDMStructureService_v70(session: session)

		do {
			let structureDataDictionary = try service.getStructureWithStructureId(structureId!)

			if let jsonDefinition = structureDataDictionary["definition"]! as? String {
				if let userIdStr = structureDataDictionary["userId"]?.description {
					resultUserId = Int64(userIdStr)
				}

				resultRecord = DDLRecord(
					json: jsonDefinition,
					locale: NSLocale(localeIdentifier: NSLocale.currentLocaleString))
				lastError = nil
			}
			else {
				lastError = NSError.errorWithCause(.InvalidServerResponse)
				resultRecord = nil
				resultUserId = nil
			}
		}
		catch let error as NSError {
			lastError = error
			resultRecord = nil
			resultUserId = nil
		}
	}

}
