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


@objc public class AssetEntry {

	public var title:String

	public init(title:String) {
		self.title = title
	}

}

@objc protocol AssetListWidgetDelegate {

	optional func onAssetListResponse(entries:[AssetEntry])
	optional func onAssetListError(error: NSError)

	optional func onAssetSelected(entry:AssetEntry)

}

@IBDesignable public class AssetListWidget: BaseWidget {

	@IBInspectable var groupId: Int = 0
	@IBInspectable var classNameId: Int = 0

	@IBInspectable var firstPageSize = 5
	@IBInspectable var pageSize = 2

	private var loadPageOperations: [Int:LoadPageOperation] = [:]

	public enum AssetClassNameId: Int {
		case WebContent = 10109
	}

	@IBOutlet var delegate: AssetListWidgetDelegate?

	override public func onCreated() {
		assetListView().onSelectedEntryClosure = onSelectedEntry
		assetListView().fetchPageForRow = loadPageForRow
	}

	internal func loadPageForRow(row:Int) {
		let page = pageFromRow(row)

		if loadPageOperations.indexForKey(page) == nil {
			loadPage(page)
		}
	}

	internal func pageFromRow(row:Int) -> Int {
		if row < firstPageSize {
			return 0
		}

		return ((row - firstPageSize) / pageSize) + 1
	}

	internal func firstRowForPage(page:Int) -> Int {
		if page == 0 {
			return 0
		}

		return firstPageSize + (page - 1) * pageSize
	}

	internal func loadPage(page:Int) -> Bool {
		let operation = LoadPageOperation(page:page)

		operation.onOperationSuccess = onLoadPageResult
		operation.onOperationFailure = onLoadPageError

		loadPageOperations[page] = operation

		let session = LRBatchSession(session: LiferayContext.instance.currentSession)
		session.callback = operation

		let groupIdToUse = (groupId != 0 ? groupId : LiferayContext.instance.groupId) as NSNumber

		let widgetsService = LRMobilewidgetsassetentryService_v62(session: session)

		let entryQueryAttributes = [
				"start":firstRowForPage(page),
				"end":firstRowForPage(page + 1),
				"classNameIds":classNameId,
				"groupIds":groupIdToUse]

		let entryQuery = LRJSONObjectWrapper(JSONObject: entryQueryAttributes)

		var outError: NSError?

		widgetsService.getAssetEntriesWithAssetEntryQuery(entryQuery,
				locale: NSLocale.currentLocaleString(),
				error: &outError)

		let assetsService = LRAssetEntryService_v62(session: session)

		assetsService.getEntriesCountWithEntryQuery(entryQuery, error: &outError)

		session.invoke(&outError)

		if let error = outError {
			operation.onFailure(error)
			return false
		}

		return true
	}

	public func loadList() -> Bool {
		if LiferayContext.instance.currentSession == nil {
			println("ERROR: No session initialized. Can't load the asset list without session")
			return false
		}

		if classNameId == 0 {
			println("ERROR: ClassNameId is empty. Can't load the asset list without it.")
			return false
		}

		startOperationWithMessage("Loading list...", details:"Wait few seconds...")

		return loadPage(0)
	}

	internal func onLoadPageError(page: Int, error: NSError) {
		delegate?.onAssetListError?(error)

		if page == 0 {
			finishOperationWithError(error, message:"Error getting list!")
		}

		loadPageOperations.removeValueForKey(page)
	}

	internal func onLoadPageResult(page: Int, entries: [[String:AnyObject]], entryCount: Int) {
		let assetEntries = entries.map() {
			attrs -> AssetEntry in

			let title = (attrs["title"] ?? "") as String

			return AssetEntry(title: title)
		}

		delegate?.onAssetListResponse?(assetEntries)

		var allAssetEntries = Array<AssetEntry?>(count: entryCount, repeatedValue: nil)

		for (index, assetEntry) in enumerate(assetListView().entries) {
			allAssetEntries[index] = assetEntry
		}

		var offset = (page == 0) ? 0 : firstPageSize + (page - 1) * pageSize

		// last page could be incomplete
		if offset >= entryCount {
			offset = entryCount - 1
		}

		for (index, assetEntry) in enumerate(assetEntries) {
			allAssetEntries[offset + index] = assetEntry
		}

		assetListView().entryCount = entryCount
		assetListView().entries = allAssetEntries

		if page == 0 {
			finishOperation()
		}

		loadPageOperations.removeValueForKey(page)
	}

	internal func onSelectedEntry(entry:AssetEntry) {
		delegate?.onAssetSelected?(entry)
	}

	internal func assetListView() -> AssetListView {
		return widgetView as AssetListView
	}
}

internal class LoadPageOperation: NSObject, LRCallback {

	internal var onOperationSuccess: ((Int, [[String:AnyObject]], Int) -> ())?
	internal var onOperationFailure: ((Int, NSError) -> ())?

	private let page:Int

	internal init(page:Int) {
		self.page = page
	}

	internal func onFailure(error: NSError!) {
		onOperationFailure?(page, error)
	}

	internal func onSuccess(result: AnyObject!) {
		if let responses = result as? NSArray {
			if let entriesResponse = responses.firstObject as? NSArray {
				if let countResponse = responses.objectAtIndex(1) as? NSNumber {
					onOperationSuccess?(page,
						entriesResponse as [[String:AnyObject]],
						countResponse as Int)
				}
				else {
					// TODO error handling
				}
			}
			else {
				// TODO error handling
			}
		}
		else {
			// TODO error handling
		}
	}


}
