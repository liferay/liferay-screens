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

public class LiferayAssetListPageOperation: LiferayPaginationOperation {

	public var groupId: Int64?
	public var classNameId: Int64?
	public var portletItemName: String?
	public var customEntryQuery: [String:AnyObject]?


	//MARK: ServerOperation

	override public func validateData() -> ValidationError? {
		let error = super.validateData()

		if error == nil {
			if groupId == nil {
				return ValidationError("assetlist-screenlet", "undefined-group")
			}

			if classNameId == nil && portletItemName == nil {
				return ValidationError("assetlist-screenlet", "undefined-classname")
			}
		}

		return error
	}

	override public func doRun(session session: LRSession) {
		if let portletItemName = portletItemName {
			let service = LRScreensassetentryService_v62(session: session)

			if startRow == 0 {
				// since the service doesn't support pagination, we ask for
				// rows from the top to the endRow (whole single page)
				let rowCount = endRow

				do {
					let responses = try service.getAssetEntriesWithCompanyId(LiferayServerContext.companyId,
						groupId: groupId!,
						portletItemName: portletItemName,
						locale: NSLocale.currentLocaleString,
						max: Int32(rowCount))

					if let entriesResponse = responses as? [[String:AnyObject]] {
						let serverPageContent = entriesResponse

						resultPageContent = serverPageContent
						resultRowCount = serverPageContent.count
						lastError = nil
					}
					else {
						lastError = NSError.errorWithCause(.InvalidServerResponse)
						resultPageContent = nil
					}
				}
				catch let error as NSError {
					lastError = error
					resultPageContent = nil
				}
			}
			else {
				// return empty content for pages different from the first one
				resultPageContent = []
				resultRowCount = 0
			}
		}
		else {
			super.doRun(session: session)
		}
	}

	//MARK: LiferayPaginationOperation

	override internal func doGetPageRowsOperation(session session: LRBatchSession, startRow: Int, endRow: Int) {
		let service = LRScreensassetentryService_v62(session: session)

		var entryQuery = configureEntryQuery()

		entryQuery["start"] = startRow
		entryQuery["end"] = endRow

		let entryQueryWrapper = LRJSONObjectWrapper(JSONObject: entryQuery)

		do {
			try service.getAssetEntriesWithAssetEntryQuery(entryQueryWrapper,
					locale: NSLocale.currentLocaleString)
		}
		catch _ as NSError {
		}
	}

	override internal func doGetRowCountOperation(session session: LRBatchSession) {
		let service = LRAssetEntryService_v62(session: session)
		let entryQuery = configureEntryQuery()
		let entryQueryWrapper = LRJSONObjectWrapper(JSONObject: entryQuery)

		do {
			try service.getEntriesCountWithEntryQuery(entryQueryWrapper)
		}
		catch _ as NSError {
		}
	}


	//MARK: Private methods

	private func configureEntryQuery() -> [String:AnyObject] {
		var entryQuery = (customEntryQuery != nil)
			? customEntryQuery!
			: [String:AnyObject]()

		let defaultValues = [
			"classNameIds" : NSNumber(longLong: classNameId!),
			"groupIds" : NSNumber(longLong: groupId!),
			"visible" : "true"
		]

		for (k,v) in defaultValues {
			if entryQuery[k] == nil {
				entryQuery[k] = v
			}
		}

		return entryQuery
	}

}
