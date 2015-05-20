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

package com.liferay.mobile.screens.testapp.utils;

/**
 * @author Silvio Santos
 */
public enum AssetConstantIdsForEE {

	// Users and sites
	GROUP(20001),
	LAYOUT(20002),
	ORGANIZATION(20003),
	USER(20005),
	USER_GROUP(20006),

	// Blogs
	BLOGS_ENTRY(20007),

	// Bookmarks
	BOOKMARKS_ENTRY(20008),
	BOOKMARKS_FOLDER(20009),

	// Calendar
	CALENDAR_EVENT(20010),

	// Document Library
	DLFILE_ENTRY(20011),
	DLFILE_ENTRY_METADATA(20091),
	DLFILE_ENTRY_TYPE(20092),
	DLFILE_RANK(20093),
	DLFILE_SHORTCUT(20094),
	DLFILE_VERSION(20095),

	// DDL
	DDLRECORD(20097),
	DDLRECORD_SET(20098),

	// Journal
	JOURNAL_ARTICLE(20109),
	JOURNAL_FOLDER(20013),

	// MessageBoard
	MBMESSAGE(20014),
	MBTHREAD(20015),
	MBCATEGORY(20115),
	MBDISCUSSION(20116),
	MBMAILING_LIST(20117),

	// Wiki
	WIKI_PAGE(20016),
	WIKI_PAGE_RESOURCE(20153),
	WIKI_NODE(20152);

	public int getValue() {
		return _value;
	}

	AssetConstantIdsForEE(int value) {
		_value = value;
	}

	private int _value;

}