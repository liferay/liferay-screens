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
public let AssetClassNameKey_Group = "Group"
public let AssetClassNameKey_Layout = "Layout"
public let AssetClassNameKey_Organization = "Organization"
public let AssetClassNameKey_User = "User"
public let AssetClassNameKey_UserGroup = "UserGroup"

// Blogs
public let AssetClassNameKey_BlogsEntry = "BlogsEntry"

// Bookmarks
public let AssetClassNameKey_BookmarksEntry = "BookmarksEntry"
public let AssetClassNameKey_BookmarksFolder = "BookmarksFolder"

// Document Library
public let AssetClassNameKey_DLFileEntry = "DLFileEntry"
public let AssetClassNameKey_DLFolder = "DLFolder"
public let AssetClassNameKey_DLFileEntryMetadata = "DLFileEntryMetadata"
public let AssetClassNameKey_DLFileEntryType = "DLFileEntryType"
public let AssetClassNameKey_DLFileRank = "DLFileRank"
public let AssetClassNameKey_DLFileShortcut = "DLFileShortcut"
public let AssetClassNameKey_DLFileVersion = "DLFileVersion"

// DDL
public let AssetClassNameKey_DDLRecord = "DDLRecord"
public let AssetClassNameKey_DDLRecordSet = "DDLRecordSet"
public let AssetClassNameKey_DDLRecordVersion = "DDLRecordVersion"

// Journal
public let AssetClassNameKey_JournalArticle = "JournalArticle"
public let AssetClassNameKey_JournalArticleImage = "JournalArticleImage"
public let AssetClassNameKey_JournalFolder = "JournalFolder"

// MessageBoard
public let AssetClassNameKey_MBMessage = "MBMessage"
public let AssetClassNameKey_MBThread = "MBThread"
public let AssetClassNameKey_MBCategory = "MBCategory"
public let AssetClassNameKey_MBDiscussion = "MBDiscussion"
public let AssetClassNameKey_MBMailingList = "MBMailingList"

// Wiki
public let AssetClassNameKey_WikiPage = "WikiPage"
public let AssetClassNameKey_WikiPageResource = "WikiPageResource"
public let AssetClassNameKey_WikiNode = "WikiNode"


@objc public class AssetClassNameIds : NSObject {

	private static var classNameIds: [String:Int64] = {

		// These are the default identifiers for a Liferay Portal 7.0 GA3
		// installation.
		// Be aware your installation probably have different identifiers, so
		// you probably we'll need to overwrite these values like this:
		//		AssetClassNameKey_s.set(AssetClassNameKey_Group, 1234)

		return [
			// Users and sites
			AssetClassNameKey_Group : 20045,
			AssetClassNameKey_Layout : 20047,
			AssetClassNameKey_Organization : 20059,
			AssetClassNameKey_User : 20087,
			AssetClassNameKey_UserGroup : 20088,

			// Blogs
			AssetClassNameKey_BlogsEntry : 20011,

			// Bookmarks
			AssetClassNameKey_BookmarksEntry : 27401,
			AssetClassNameKey_BookmarksFolder : 27402,

			// Document Library
			AssetClassNameKey_DLFileEntry : 20015,
			AssetClassNameKey_DLFolder: 20021,
			AssetClassNameKey_DLFileEntryMetadata : 20016,
			AssetClassNameKey_DLFileEntryType : 20017,
			AssetClassNameKey_DLFileRank : 20018,
			AssetClassNameKey_DLFileShortcut : 20019,
			AssetClassNameKey_DLFileVersion : 20020,

			// DDL
			AssetClassNameKey_DDLRecord : 29101,
			AssetClassNameKey_DDLRecordSet : 29102,
			AssetClassNameKey_DDLRecordVersion : 29103,

			// Journal
			AssetClassNameKey_JournalArticle : 29501,
			AssetClassNameKey_JournalArticleImage : 29502,
			AssetClassNameKey_JournalFolder : 29506,

			// MessageBoard
			AssetClassNameKey_MBMessage : 20032,
			AssetClassNameKey_MBThread : 20034,
			AssetClassNameKey_MBCategory : 20029,
			AssetClassNameKey_MBDiscussion : 20030,
			AssetClassNameKey_MBMailingList : 20031,

			// Wiki
			AssetClassNameKey_WikiPage : 27902,
			AssetClassNameKey_WikiPageResource : 27903,
			AssetClassNameKey_WikiNode : 27901
		]
	}()

	private static var classNames: [String:String] = {

		// These are the default className for a Liferay Portal 7.0 GA3
		// installation.
		// Be aware your installation probably have different className, so
		// you probably we'll need to overwrite these values like this:
		//		AssetClassNameIds.set(AssetClassNameKey_Group, "com.liferay.portal.kernel.model.Group")

		return [
			// Users and sites
			AssetClassNameKey_Group : "com.liferay.portal.kernel.model.Group",
			AssetClassNameKey_Layout : "com.liferay.portal.kernel.model.Layout",
			AssetClassNameKey_Organization : "com.liferay.portal.kernel.model.Organization",
			AssetClassNameKey_User : "com.liferay.portal.kernel.model.User",
			AssetClassNameKey_UserGroup : "com.liferay.portal.kernel.model.UserGroup",

			// Blogs
			AssetClassNameKey_BlogsEntry : "com.liferay.blogs.kernel.model.BlogsEntry",

			// Bookmarks
			AssetClassNameKey_BookmarksEntry : "com.liferay.bookmarks.model.BookmarksEntry",
			AssetClassNameKey_BookmarksFolder : "com.liferay.bookmarks.model.BookmarksFolder",

			// Document Library
			AssetClassNameKey_DLFileEntry : "com.liferay.document.library.kernel.model.DLFileEntry",
			AssetClassNameKey_DLFolder : "com.liferay.document.library.kernel.model.DLFolder",
			AssetClassNameKey_DLFileEntryMetadata : "com.liferay.document.library.kernel.model.DLFileEntryMetadata",
			AssetClassNameKey_DLFileEntryType : "com.liferay.document.library.kernel.model.DLFileEntryType",
			AssetClassNameKey_DLFileRank : "com.liferay.document.library.kernel.model.DLFileRank",
			AssetClassNameKey_DLFileShortcut : "com.liferay.document.library.kernel.model.DLFileShortcut",
			AssetClassNameKey_DLFileVersion : "com.liferay.document.library.kernel.model.DLFileVersion",

			// DDL
			AssetClassNameKey_DDLRecord : "com.liferay.dynamic.data.lists.model.DDLRecord",
			AssetClassNameKey_DDLRecordSet : "com.liferay.dynamic.data.lists.model.DDLRecordSet",
			AssetClassNameKey_DDLRecordVersion : "com.liferay.dynamic.data.lists.model.DDLRecordVersion",

			// Journal
			AssetClassNameKey_JournalArticle : "com.liferay.journal.model.JournalArticle",
			AssetClassNameKey_JournalArticleImage : "com.liferay.journal.model.JournalArticleImage",
			AssetClassNameKey_JournalFolder : "com.liferay.journal.model.JournalFolder",

			// MessageBoard
			AssetClassNameKey_MBMessage : "com.liferay.message.boards.kernel.model.MBMessage",
			AssetClassNameKey_MBThread : "com.liferay.message.boards.kernel.model.MBThread",
			AssetClassNameKey_MBCategory : "com.liferay.message.boards.kernel.model.MBCategory",
			AssetClassNameKey_MBDiscussion : "com.liferay.message.boards.kernel.model.MBDiscussion",
			AssetClassNameKey_MBMailingList : "com.liferay.message.boards.kernel.model.MBMailingList",

			// Wiki
			AssetClassNameKey_WikiPage : "com.liferay.wiki.model.WikiPage",
			AssetClassNameKey_WikiPageResource : "com.liferay.wiki.model.WikiPageResource",
			AssetClassNameKey_WikiNode : "com.liferay.wiki.model.WikiNode"
		]
	}()

	public class func getClassNameId(key: String) -> Int64? {
		return classNameIds[key]
	}

	public class func getClassName(key: String) -> String? {
		return classNames[key]
	}

	public class func getClassNameFromId(classNameId: Int64) -> String? {
		return classNameIds.filter({$0.1 == classNameId}).first?.0
	}

	public class func set(key: String, newId: Int64) {
		classNameIds[key] = newId
	}

	public class func set(key: String, newClassName: String) {
		classNames[key] = newClassName
	}
}
