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

	optional func onAssetListResponse(entries:[AssetListWidget.Entry])
	optional func onAssetListError(error: NSError)

	optional func onAssetSelected(entry: AssetListWidget.Entry)

}


@IBDesignable public class AssetListWidget: BaseListWidget {

	@objc public class Entry {

		public var title:String

		public init(title:String) {
			self.title = title
		}

	}


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


	@IBInspectable public var groupId: Int64 = 0
	@IBInspectable public var classNameId = 0

	@IBOutlet public var delegate: AssetListWidgetDelegate?

	internal var assetListView: AssetListView {
		return widgetView as AssetListView
	}


	//MARK: BaseListWidget

	override internal func doGetPageRowsOperation(#session: LRSession, page: Int) {
		let groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId

		let widgetsService = LRMobilewidgetsassetentryService_v62(session: session)

		let entryQueryAttributes = [
				"start": firstRowForPage(page),
				"end": firstRowForPage(page + 1),
				"classNameIds": classNameId,
				"groupIds": NSNumber(longLong: groupId)]

		let entryQuery = LRJSONObjectWrapper(JSONObject: entryQueryAttributes)

		widgetsService.getAssetEntriesWithAssetEntryQuery(entryQuery,
				locale: NSLocale.currentLocaleString(),
				error: nil)
	}

	override internal func doGetRowCountOperation(#session: LRSession) {
		let groupId = (self.groupId != 0) ? self.groupId : LiferayServerContext.groupId

		let assetsService = LRAssetEntryService_v62(session: session)

		let entryQueryAttributes = [
				"classNameIds": classNameId,
				"groupIds": NSNumber(longLong: groupId)]

		let entryQuery = LRJSONObjectWrapper(JSONObject: entryQueryAttributes)

		assetsService.getEntriesCountWithEntryQuery(entryQuery, error: nil)
	}

	override internal func convert(#serverResult:[String:AnyObject]) -> AnyObject {
		let title = (serverResult["title"] ?? "") as String

		return Entry(title: title)
	}

	override internal func onLoadPageError(page: Int, error: NSError) {
		super.onLoadPageError(page, error: error)

		delegate?.onAssetListError?(error)
	}

	override internal func onLoadPageResult(page: Int,
			serverRows: [[String:AnyObject]],
			rowCount: Int)
			-> [AnyObject] {

		let rowObjects = super.onLoadPageResult(page, serverRows: serverRows, rowCount: rowCount)

		let assetEntries = rowObjects.map() { $0 as Entry }

		delegate?.onAssetListResponse?(assetEntries)

		return rowObjects
	}

	override internal func onSelectedRow(row: AnyObject) {
		delegate?.onAssetSelected?(row as Entry)
	}

}
