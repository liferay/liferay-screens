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


@objc public protocol AssetListWidgetDelegate {

	optional func onAssetListResponse(entries:[AssetEntry])
	optional func onAssetListError(error: NSError)

	optional func onAssetSelected(entry:AssetEntry)

}


@IBDesignable public class AssetListWidget: BaseWidget {

	public enum AssetClassNameId: Int {

		// Users and sites
		case Group = 10001
		case Layout = 10002
		case Organization = 10003
		case User = 10005
		case UserGroup = 10006

		// Blogs
		case BlogsEntry = 10007

		// Bookmarks
		case BookmarksEntry = 10008
		case BookmarksFolder = 10009

		// Calendar
		case CalendarEvent = 10010

		// Document Library
		case DLFileEntry = 10011
		case DLFileEntryMetadata = 10091
		case DLFileEntryType = 10092
		case DLFileRank = 10093
		case DLFileShortcut = 10094
		case DLFileVersion = 10095

		// DDL
		case DDLRecord = 10097
		case DDLRecordSet = 10098

		// Journal
		case JournalArticle = 10109
		case JournalFolder = 10013

		// MessageBoard
		case MBMessage = 10014
		case MBThread = 10015
		case MBCategory = 10115
		case MBDiscussion = 10116
		case MBMailingList = 10117

		// Wiki
		case WikiPage = 10016
		case WikiPageResource = 10153
		case WikiNode = 10152

	}


	@IBInspectable public var groupId = 0
	@IBInspectable public var classNameId = 0

	@IBInspectable public var firstPageSize = 5
	@IBInspectable public var pageSize = 2

	@IBOutlet public var delegate: AssetListWidgetDelegate?

	internal var assetListView: AssetListView {
		return widgetView as AssetListView
	}

	private var loadPageOperations: [Int:LoadPageOperation] = [:]


	//MARK: BaseWidget

	override public func onCreated() {
		assetListView.onSelectedEntryClosure = onSelectedEntry
		assetListView.fetchPageForRow = loadPageForRow
	}


	//MARK: Public methods

	public func loadList() -> Bool {
		if LiferayContext.instance().currentSession == nil {
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


	//MARK: Internal methods

	internal func loadPage(page:Int) -> Bool {
		let operation = LoadPageOperation(page:page)

		operation.onOperationSuccess = onLoadPageResult
		operation.onOperationFailure = onLoadPageError

		loadPageOperations[page] = operation

		let session = LRBatchSession(session: LiferayContext.instance().currentSession)
		session.callback = operation

		let groupIdToUse = (groupId != 0 ? groupId : LiferayContext.instance().groupId) as NSNumber

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

		for (index, assetEntry) in enumerate(assetListView.entries) {
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

		assetListView.entryCount = entryCount
		assetListView.entries = allAssetEntries

		if page == 0 {
			finishOperation()
		}

		loadPageOperations.removeValueForKey(page)
	}

	internal func onSelectedEntry(entry:AssetEntry) {
		delegate?.onAssetSelected?(entry)
	}

}
