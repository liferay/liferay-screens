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


@objc public protocol AssetListScreenletDelegate {

	optional func onAssetListResponse(entries:[AssetListScreenletEntry])
	optional func onAssetListError(error: NSError)

	optional func onAssetSelected(entry: AssetListScreenletEntry)

}


@objc public class AssetListScreenletEntry {

	public let attributes:[String:AnyObject]

	public var title: String {
		return attributes["title"] as String
	}

	public var description: String {
		return attributes["description"] as String
	}

	public var classNameId: Int64 {
		return Int64(attributes["classNameId"] as Int)
	}

	public var classPK: Int64 {
		return Int64(attributes["classPK"] as Int)
	}

	public var groupId: Int64 {
		return Int64(attributes["groupId"] as Int)
	}

	public var companyId: Int64 {
		return Int64(attributes["companyId"] as Int)
	}

	public var entryId: Int64 {
		return Int64(attributes["entryId"] as Int)
	}


	//MARK: Init

	public init(attributes:[String:AnyObject]) {
		self.attributes = attributes
	}

}


@IBDesignable public class AssetListScreenlet: BaseListScreenlet {



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
	@IBInspectable public var classNameId: Int = 0

	@IBOutlet public var delegate: AssetListScreenletDelegate?


	//MARK: BaseListScreenlet

	override internal func createPaginationOperation(
			#page: Int,
			computeRowCount: Bool)
			-> LiferayPaginationOperation {

		let operation = LiferayAssetListPageOperation(
				screenlet: self,
				page: page,
				computeRowCount: computeRowCount)

		operation.groupId = (self.groupId != 0)
				? self.groupId : LiferayServerContext.groupId

		operation.classNameId = Int64(self.classNameId)

		return operation
	}

	override internal func convert(#serverResult:[String:AnyObject]) -> AnyObject {
		return AssetListScreenletEntry(attributes: serverResult)
	}

	override internal func onLoadPageError(#page: Int, error: NSError) {
		super.onLoadPageError(page: page, error: error)

		delegate?.onAssetListError?(error)
	}

	override internal func onLoadPageResult(
			#page: Int,
			serverRows: [[String:AnyObject]],
			rowCount: Int)
			-> [AnyObject] {

		let rowObjects = super.onLoadPageResult(
				page: page,
				serverRows: serverRows,
				rowCount: rowCount)

		let assetEntries = rowObjects.map() { $0 as AssetListScreenletEntry }

		delegate?.onAssetListResponse?(assetEntries)

		return rowObjects
	}

	override internal func onSelectedRow(row: AnyObject) {
		delegate?.onAssetSelected?(row as AssetListScreenletEntry)
	}

}
