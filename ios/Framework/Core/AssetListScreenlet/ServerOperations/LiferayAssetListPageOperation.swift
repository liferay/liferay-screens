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
	public var classNameId: Int?

	internal var assetListScreenlet: AssetListScreenlet {
		return self.screenlet as! AssetListScreenlet
	}


	//MARK: ServerOperation

	override func validateData() -> Bool {
		var valid = super.validateData()

		valid = valid && (groupId != nil)
		valid = valid && (classNameId != nil)

		return valid
	}


	//MARK: LiferayPaginationOperation

	override internal func doGetPageRowsOperation(#session: LRBatchSession, page: Int) {
		let screenletsService = LRScreensassetentryService_v62(session: session)

		var entryQueryAttributes = configureEntryQueryAttributes()

		entryQueryAttributes["start"] = assetListScreenlet.firstRowForPage(page)
		entryQueryAttributes["end"] = assetListScreenlet.firstRowForPage(page + 1)

		let entryQuery = LRJSONObjectWrapper(JSONObject: entryQueryAttributes)

		screenletsService.getAssetEntriesWithAssetEntryQuery(entryQuery,
				locale: NSLocale.currentLocaleString,
				error: nil)
	}

	override internal func doGetRowCountOperation(#session: LRBatchSession) {
		let assetsService = LRAssetEntryService_v62(session: session)
		let entryQueryAttributes = configureEntryQueryAttributes()
		let entryQuery = LRJSONObjectWrapper(JSONObject: entryQueryAttributes)

		assetsService.getEntriesCountWithEntryQuery(entryQuery, error: nil)
	}


	//MARK: Private methods

	private func configureEntryQueryAttributes() -> [NSString : AnyObject] {
		var entryQueryAttributes: [NSString : AnyObject] = [:]

		entryQueryAttributes["classNameIds"] = NSNumber(long: classNameId!)
		entryQueryAttributes["groupIds"] = NSNumber(longLong: groupId!)
		entryQueryAttributes["visible"] = "true"

		return entryQueryAttributes
	}

}
