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

	override public func doRun(#session: LRSession) {
		if let portletItemName = portletItemName {
			let service = LRScreensassetentryService_v62(session: session)

			if startRow == 0 {
				// since the service doesn't support pagination, we ask for
				// rows from the top to the endRow (whole single page)
				let rowCount = endRow

				let responses = service.getAssetEntriesWithCompanyId(LiferayServerContext.companyId,
					groupId: groupId!,
					portletItemName: portletItemName,
					locale: NSLocale.currentLocaleString,
					max: Int32(endRow),
					error: &lastError)

				if lastError == nil {
					if let entriesResponse = responses as? [[String:AnyObject]] {
						let serverPageContent = entriesResponse

						resultPageContent = serverPageContent
						resultRowCount = serverPageContent.count
					}
					else {
						lastError = NSError.errorWithCause(.InvalidServerResponse, userInfo: nil)
					}
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

	override internal func doGetPageRowsOperation(#session: LRBatchSession, startRow: Int, endRow: Int) {
		let service = LRScreensassetentryService_v62(session: session)

		var entryQuery = configureEntryQuery()

		entryQuery["start"] = startRow
		entryQuery["end"] = endRow

		let entryQueryWrapper = LRJSONObjectWrapper(JSONObject: entryQuery)

		service.getAssetEntriesWithAssetEntryQuery(entryQueryWrapper,
			locale: NSLocale.currentLocaleString,
			error: nil)
	}

	override internal func doGetRowCountOperation(#session: LRBatchSession) {
		let service = LRAssetEntryService_v62(session: session)
		let entryQuery = configureEntryQuery()
		let entryQueryWrapper = LRJSONObjectWrapper(JSONObject: entryQuery)

		service.getEntriesCountWithEntryQuery(entryQueryWrapper, error: nil)
	}


	//MARK: Private methods

	private func configureEntryQuery() -> [String:AnyObject] {
		var entryQuery = (customEntryQuery != nil)
			? customEntryQuery!
			: [String:AnyObject]()

		if entryQuery["classNameIds"] == nil {
			entryQuery["classNameIds"] = NSNumber(longLong: classNameId!)
		}

		if entryQuery["groupIds"] == nil {
			entryQuery["groupIds"] = NSNumber(longLong: groupId!)
		}

		if entryQuery["visible"] == nil {
			entryQuery["visible"] = "true"
		}

		return entryQuery
	}

}
