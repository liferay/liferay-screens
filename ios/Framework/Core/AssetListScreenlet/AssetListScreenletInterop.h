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
#import <UIKit/UIKit.h>

typedef NS_ENUM(NSInteger, AssetClassNameId) {

	// Users and sites
	AssetClassNameIdGroup = 10001,
	AssetClassNameIdLayout = 10002,
	AssetClassNameIdOrganization = 10003,
	AssetClassNameIdUser = 10005,
	AssetClassNameIdUserGroup = 10006,

	// Blogs
	AssetClassNameIdBlogsEntry = 10007,

	// Bookmarks
	AssetClassNameIdBookmarksEntry = 10008,
	AssetClassNameIdBookmarksFolder = 10009,

	// Calendar
	AssetClassNameIdCalendarEvent = 10010,

	// Document Library
	AssetClassNameIdDLFileEntry = 10011,
	AssetClassNameIdDLFileEntryMetadata = 10091,
	AssetClassNameIdDLFileEntryType = 10092,
	AssetClassNameIdDLFileRank = 10093,
	AssetClassNameIdDLFileShortcut = 10094,
	AssetClassNameIdDLFileVersion = 10095,

	// DDL
	AssetClassNameIdDDLRecord = 10097,
	AssetClassNameIdDDLRecordSet = 10098,

	// Journal
	AssetClassNameIdJournalArticle = 10109,
	AssetClassNameIdJournalFolder = 10013,

	// MessageBoard
	AssetClassNameIdMBMessage = 10014,
	AssetClassNameIdMBThread = 10015,
	AssetClassNameIdMBCategory = 10115,
	AssetClassNameIdMBDiscussion = 10116,
	AssetClassNameIdMBMailingList = 10117,

	// Wiki
	AssetClassNameIdWikiPage = 10016,
	AssetClassNameIdWikiPageResource = 10153,
	AssetClassNameIdWikiNode = 10152

};