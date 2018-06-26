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

// Blogs
public let AssetClassNameKey_BlogsEntry = "BlogsEntry"

// Bookmarks
public let AssetClassNameKey_BookmarksEntry = "BookmarksEntry"
public let AssetClassNameKey_BookmarksFolder = "BookmarksFolder"

// Calendar
public let AssetClassNameKey_CalendarBooking = "CalendarBooking"

// DDL
public let AssetClassNameKey_DDLRecord = "DDLRecord"

// DDM
public let AssetClassNameKey_DDLFormRecord = "DDLFormRecord"

// Document Library
public let AssetClassNameKey_DLFileEntry = "DLFileEntry"
public let AssetClassNameKey_DLFolder = "DLFolder"

// Journal
public let AssetClassNameKey_JournalArticle = "JournalArticle"
public let AssetClassNameKey_JournalFolder = "JournalFolder"

// Users and sites
public let AssetClassNameKey_Layout = "Layout"
public let AssetClassNameKey_LayoutRevision = "LayoutRevision"
public let AssetClassNameKey_Organization = "Organization"
public let AssetClassNameKey_Site = "Group"
public let AssetClassNameKey_User = "User"

// Comment
public let AssetClassNameKey_MBDiscussion = "MBDiscussion"
public let AssetClassNameKey_MBMessage = "MBMessage"
public let AssetClassNameKey_MBThread = "MBThread"

// Microblogs
public let AssetClassNameKey_MicroblogsEntry = "MicroblogsEntry"

// Wiki
public let AssetClassNameKey_WikiPage = "WikiPage"

@objc(AssetClassEntry)
open class AssetClassEntry: NSObject {

	open let classNameId: Int64
	open let className: String

	public init(_ classNameId: Int64, _ className: String) {
		self.classNameId = classNameId
		self.className = className

		super.init()
	}

}

@objc(AssetClasses)
@objcMembers
open class AssetClasses: NSObject {

	fileprivate static var classNameEntries: [String: AssetClassEntry] = {

		// These are the default values for a Liferay Portal 7.0 GA3
		// installation.
		// Be aware your installation probably have different identifiers, so
		// you probably we'll need to overwrite these values like this:
		//		AssetClassNameKey_s.setClassNameId(AssetClassNameKey_Group, 1234)
		return [
			// Blogs
			AssetClassNameKey_BlogsEntry: AssetClassEntry(20011, "com.liferay.blogs.kernel.model.BlogsEntry"),

			// Bookmarks
			AssetClassNameKey_BookmarksEntry: AssetClassEntry(28401, "com.liferay.bookmarks.model.BookmarksEntry"),
			AssetClassNameKey_BookmarksFolder: AssetClassEntry(28402, "com.liferay.bookmarks.model.BookmarksFolder"),

			// Calendar
			AssetClassNameKey_CalendarBooking: AssetClassEntry(27702, "com.liferay.calendar.model.CalendarBooking"),

			// DDL
			AssetClassNameKey_DDLRecord: AssetClassEntry(29501, "com.liferay.dynamic.data.lists.model.DDLRecord"),

			// DDMForm
			AssetClassNameKey_DDLFormRecord: AssetClassEntry(31330, "com.liferay.dynamic.data.lists.model.DDLFormRecord"),

			// Document Library
			AssetClassNameKey_DLFileEntry: AssetClassEntry(20015, "com.liferay.document.library.kernel.model.DLFileEntry"),
			AssetClassNameKey_DLFolder: AssetClassEntry(20021, "com.liferay.document.library.kernel.model.DLFolder"),

			// WebContent
			AssetClassNameKey_JournalArticle: AssetClassEntry(29634, "com.liferay.journal.model.JournalArticle"),
			AssetClassNameKey_JournalFolder: AssetClassEntry(29639, "com.liferay.journal.model.JournalFolder"),

			// Users and sites
			AssetClassNameKey_Layout: AssetClassEntry(20047, "com.liferay.portal.kernel.model.User"),
			AssetClassNameKey_LayoutRevision: AssetClassEntry(20051, "com.liferay.portal.kernel.model.User"),
			AssetClassNameKey_Organization: AssetClassEntry(20059, "com.liferay.portal.kernel.model.User"),
			AssetClassNameKey_Site: AssetClassEntry(20045, "com.liferay.portal.kernel.model.User"),
			AssetClassNameKey_User: AssetClassEntry(20087, "com.liferay.portal.kernel.model.User"),

			// Message boards
			AssetClassNameKey_MBDiscussion: AssetClassEntry(20030, "com.liferay.message.boards.kernel.model.MBDiscussion"),
			AssetClassNameKey_MBMessage: AssetClassEntry(20032, "com.liferay.message.boards.kernel.model.MBMessage"),
			AssetClassNameKey_MBThread: AssetClassEntry(20034, "com.liferay.message.boards.kernel.model.MBThread"),

			// Microblogs
			AssetClassNameKey_MicroblogsEntry: AssetClassEntry(28701, "com.liferay.microblogs.model.MicroblogsEntry"),

			// Wiki
			AssetClassNameKey_WikiPage: AssetClassEntry(28802, "com.liferay.wiki.model.WikiPage")
		]
	}()

	open class func getClassNameId(_ key: String) -> Int64? {
		return classNameEntries[key]?.classNameId
	}

	open class func getClassName(_ key: String) -> String? {
		return classNameEntries[key]?.className
	}

	open class func getClassNameFromId(_ classNameId: Int64) -> String? {
		return classNameEntries.filter {
				$0.1.classNameId == classNameId
			}
			.first?.1.className
	}

	open class func set(_ key: String, newId: Int64) {
		if let currentEntry = classNameEntries[key] {
			classNameEntries[key] = AssetClassEntry(newId, currentEntry.className)
		}
	}

	open class func set(_ key: String, newClassName: String) {
		if let currentEntry = classNameEntries[key] {
			classNameEntries[key] = AssetClassEntry(currentEntry.classNameId, newClassName)
		}
	}
}
