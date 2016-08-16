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

	private static var classNames: [String:String] = {

		// These are the default className for a Liferay Portal 7.0 CE GA2
		// installation.
		// Be aware your installation probably have different className, so
		// you probably we'll need to overwrite these values like this:
		//		AssetClassNameIds.set(AssetClassNameIdGroup, "com.liferay.portal.kernel.model.Group")

		return [
			// Users and sites
			AssetClassNameIdGroup : "com.liferay.portal.kernel.model.Group",
			AssetClassNameIdLayout : "com.liferay.portal.kernel.model.Layout",
			AssetClassNameIdOrganization : "com.liferay.portal.kernel.model.Organization",
			AssetClassNameIdUser : "com.liferay.portal.kernel.model.User",
			AssetClassNameIdUserGroup : "com.liferay.portal.kernel.model.UserGroup",

			// Blogs
			AssetClassNameIdBlogsEntry : "com.liferay.blogs.kernel.model.BlogsEntry",

			// Bookmarks
			AssetClassNameIdBookmarksEntry : "com.liferay.bookmarks.model.BookmarksEntry",
			AssetClassNameIdBookmarksFolder : "com.liferay.bookmarks.model.BookmarksFolder",

			// Document Library
			AssetClassNameIdDLFileEntry : "com.liferay.document.library.kernel.model.DLFileEntry",
			AssetClassNameIdDLFolder: "com.liferay.document.library.kernel.model.DLFolder",
			AssetClassNameIdDLFileEntryMetadata : "com.liferay.document.library.kernel.model.DLFileEntryMetadata",
			AssetClassNameIdDLFileEntryType : "com.liferay.document.library.kernel.model.DLFileEntryType",
			AssetClassNameIdDLFileRank : "com.liferay.document.library.kernel.model.DLFileRank",
			AssetClassNameIdDLFileShortcut : "com.liferay.document.library.kernel.model.DLFileShortcut",
			AssetClassNameIdDLFileVersion : "com.liferay.document.library.kernel.model.DLFileVersion",

			// DDL
			AssetClassNameIdDDLRecord : "com.liferay.dynamic.data.lists.model.DDLRecord",
			AssetClassNameIdDDLRecordSet : "com.liferay.dynamic.data.lists.model.DDLRecordSet",
			AssetClassNameIdDDLRecordVersion : "com.liferay.dynamic.data.lists.model.DDLRecordVersion",

			// Journal
			AssetClassNameIdJournalArticle : "com.liferay.journal.model.JournalArticle",
			AssetClassNameIdJournalArticleImage : "com.liferay.journal.model.JournalArticleImage",
			AssetClassNameIdJournalFolder : "com.liferay.journal.model.JournalFolder",

			// MessageBoard
			AssetClassNameIdMBMessage : "com.liferay.message.boards.kernel.model.MBMessage",
			AssetClassNameIdMBThread : "com.liferay.message.boards.kernel.model.MBThread",
			AssetClassNameIdMBCategory : "com.liferay.message.boards.kernel.model.MBCategory",
			AssetClassNameIdMBDiscussion : "com.liferay.message.boards.kernel.model.MBDiscussion",
			AssetClassNameIdMBMailingList : "com.liferay.message.boards.kernel.model.MBMailingList",

			// Wiki
			AssetClassNameIdWikiPage : "com.liferay.wiki.model.WikiPage",
			AssetClassNameIdWikiPageResource : "com.liferay.wiki.model.WikiPageResource",
			AssetClassNameIdWikiNode : "com.liferay.wiki.model.WikiNode"
		]
	}()

	public class func get(className: String) -> Int64? {
		return classNameIds[className]
	}

	public class func get(classNameId: Int64) -> String? {
		return classNameIds.filter({$0.1 == classNameId}).first?.0
	}

	public class func set(className: String, newId: Int64) {
		classNameIds[className] = newId
	}

}
