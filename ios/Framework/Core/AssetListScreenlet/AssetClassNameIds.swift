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

// Users and sites
public let AssetClassNameIdGroup = "Group"
public let AssetClassNameIdLayout = "Layout"
public let AssetClassNameIdOrganization = "Organization"
public let AssetClassNameIdUser = "User"
public let AssetClassNameIdUserGroup = "UserGroup"

// Blogs
public let AssetClassNameIdBlogsEntry = "BlogsEntry"

// Bookmarks
public let AssetClassNameIdBookmarksEntry = "BookmarksEntry"
public let AssetClassNameIdBookmarksFolder = "BookmarksFolder"

// Calendar
public let AssetClassNameIdCalendarEvent = "CalendarEvent"

// Document Library
public let AssetClassNameIdDLFileEntry = "DLFileEntry"
public let AssetClassNameIdDLFolder = "DLFolder"
public let AssetClassNameIdDLFileEntryMetadata = "DLFileEntryMetadata"
public let AssetClassNameIdDLFileEntryType = "DLFileEntryType"
public let AssetClassNameIdDLFileRank = "DLFileRank"
public let AssetClassNameIdDLFileShortcut = "DLFileShortcut"
public let AssetClassNameIdDLFileVersion = "DLFileVersion"

// DDL
public let AssetClassNameIdDDLRecord = "DDLRecord"
public let AssetClassNameIdDDLRecordSet = "DDLRecordSet"
public let AssetClassNameIdDDLRecordVersion = "DDLRecordVersion"

// Journal
public let AssetClassNameIdJournalArticle = "JournalArticle"
public let AssetClassNameIdJournalArticleImage = "JournalArticleImage"
public let AssetClassNameIdJournalFolder = "JournalFolder"

// MessageBoard
public let AssetClassNameIdMBMessage = "MBMessage"
public let AssetClassNameIdMBThread = "MBThread"
public let AssetClassNameIdMBCategory = "MBCategory"
public let AssetClassNameIdMBDiscussion = "MBDiscussion"
public let AssetClassNameIdMBMailingList = "MBMailingList"

// Wiki
public let AssetClassNameIdWikiPage = "WikiPage"
public let AssetClassNameIdWikiPageResource = "WikiPageResource"
public let AssetClassNameIdWikiNode = "WikiNode"


@objc public class AssetClassNameIds : NSObject {

	private static var classNameIds: [String:Int64] = {

		// These are the default identifiers for a Liferay Portal 6.2 CE GA2
		// installation.
		// Be aware your installation probably have different identifiers, so
		// you probably we'll need to overwrite these values like this:
		//		AssetClassNameIds.set(AssetClassNameIdGroup, 1234)

		return [
			// Users and sites
			AssetClassNameIdGroup : 10001,
			AssetClassNameIdLayout : 10002,
			AssetClassNameIdOrganization : 10003,
			AssetClassNameIdUser : 10005,
			AssetClassNameIdUserGroup : 10006,

			// Blogs
			AssetClassNameIdBlogsEntry : 10007,

			// Bookmarks
			AssetClassNameIdBookmarksEntry : 10008,
			AssetClassNameIdBookmarksFolder : 10009,

			// Calendar
			AssetClassNameIdCalendarEvent : 10010,

			// Document Library
			AssetClassNameIdDLFileEntry : 10011,
			AssetClassNameIdDLFolder: 10012,
			AssetClassNameIdDLFileEntryMetadata : 10091,
			AssetClassNameIdDLFileEntryType : 10092,
			AssetClassNameIdDLFileRank : 10093,
			AssetClassNameIdDLFileShortcut : 10094,
			AssetClassNameIdDLFileVersion : 10095,

			// DDL
			AssetClassNameIdDDLRecord : 10097,
			AssetClassNameIdDDLRecordSet : 10098,
			AssetClassNameIdDDLRecordVersion : 10099,

			// Journal
			AssetClassNameIdJournalArticle : 10109,
			AssetClassNameIdJournalArticleImage : 10110,
			AssetClassNameIdJournalFolder : 10013,

			// MessageBoard
			AssetClassNameIdMBMessage : 10014,
			AssetClassNameIdMBThread : 10015,
			AssetClassNameIdMBCategory : 10115,
			AssetClassNameIdMBDiscussion : 10116,
			AssetClassNameIdMBMailingList : 10117,

			// Wiki
			AssetClassNameIdWikiPage : 10016,
			AssetClassNameIdWikiPageResource : 10153,
			AssetClassNameIdWikiNode : 10152
		]
	}()


	public class func get(className: String) -> Int64? {
		return classNameIds[className]
	}

	public class func set(className: String, newId: Int64) {
		classNameIds[className] = newId
	}

}
