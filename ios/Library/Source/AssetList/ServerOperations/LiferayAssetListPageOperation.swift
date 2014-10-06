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

	public var groupId: Int64 = 0
	public var classNameId: Int64 = 0

	internal var assetListWidget: AssetListWidget {
		return self.widget as AssetListWidget
	}

	override internal func doGetPageRowsOperation(#session: LRBatchSession, page: Int) {
		let widgetsService = LRMobilewidgetsassetentryService_v62(session: session)

		var entryQueryAttributes: [NSString : AnyObject] = [:]

		entryQueryAttributes["start"] = assetListWidget.firstRowForPage(page)
		entryQueryAttributes["end"] = assetListWidget.firstRowForPage(page + 1)
		entryQueryAttributes["classNameIds"] = NSNumber(longLong: classNameId)
		entryQueryAttributes["groupIds"] = NSNumber(longLong: groupId)

		let entryQuery = LRJSONObjectWrapper(JSONObject: entryQueryAttributes)

		widgetsService.getAssetEntriesWithAssetEntryQuery(entryQuery,
				locale: NSLocale.currentLocaleString(),
				error: nil)
	}

	override internal func doGetRowCountOperation(#session: LRBatchSession) {
		let assetsService = LRAssetEntryService_v62(session: session)

		var entryQueryAttributes: [NSString : AnyObject] = [:]

		entryQueryAttributes["classNameIds"] = NSNumber(longLong: classNameId)
		entryQueryAttributes["groupIds"] = NSNumber(longLong: groupId)

		let entryQuery = LRJSONObjectWrapper(JSONObject: entryQueryAttributes)

		assetsService.getEntriesCountWithEntryQuery(entryQuery, error: nil)
	}

}
