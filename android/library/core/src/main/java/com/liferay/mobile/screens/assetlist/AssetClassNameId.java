/**
 * Copyright (c) 2000-present Liferay), Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License), or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful), but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.assetlist;

/**
 * @author Silvio Santos
 */
public enum AssetClassNameId {

	// Users and sites
	GROUP(10001),
	LAYOUT(10002),
	ORGANIZATION(10003),
	USER(10005),
	USER_GROUP(10006),

	// Blogs
	BLOGS_ENTRY(10007),

	// Bookmarks
	BOOKMARKS_ENTRY(10008),
	BOOKMARKS_FOLDER(10009),

	// Calendar
	CALENDAR_EVENT(10010),

	// Document Library
	DLFILE_ENTRY(10011),
	DLFILE_ENTRY_METADATA(10091),
	DLFILE_ENTRY_TYPE(10092),
	DLFILE_RANK(10093),
	DLFILE_SHORTCUT(10094),
	DLFILE_VERSION(10095),

	// DDL
	DDLRECORD(10097),
	DDLRECORD_SET(10098),

	// Journal
	JOURNAL_ARTICLE(10109),
	JOURNAL_FOLDER(10013),

	// MessageBoard
	MBMESSAGE(10014),
	MBTHREAD(10015),
	MBCATEGORY(10115),
	MBDISCUSSION(10116),
	MBMAILING_LIST(10117),

	// Wiki
	WIKI_PAGE(10016),
	WIKI_PAGE_RESOURCE(10153),
	WIKI_NODE(10152);

	public int getValue() {
		return _value;
	}

	AssetClassNameId(int value) {
		_value = value;
	}

	private int _value;

}