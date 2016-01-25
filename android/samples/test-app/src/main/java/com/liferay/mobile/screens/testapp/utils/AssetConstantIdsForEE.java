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
	BOOKMARKS_ENTRY(20382),
	BOOKMARKS_FOLDER(20383),

	// Calendar
	CALENDAR_EVENT(20084),

	// Document Library
	DLFILE_ENTRY(20008),
	DLFILE_ENTRY_METADATA(20086),
	DLFILE_ENTRY_TYPE(20087),
	DLFILE_RANK(20088),
	DLFILE_SHORTCUT(20089),
	DLFILE_VERSION(20090),

	// DDL
	DDLRECORD(20413),
	DDLRECORD_SET(20414),

	// Journal

	JOURNAL_ARTICLE(20453),
	JOURNAL_FOLDER(20458),

	// MessageBoard
	MBMESSAGE(20010),
	MBTHREAD(20011),
	MBCATEGORY(20098),
	MBDISCUSSION(20099),
	MBMAILING_LIST(20100),

	// Wiki
	WIKI_PAGE(20375),
	WIKI_PAGE_RESOURCE(20376),
	WIKI_NODE(20374);

	AssetConstantIdsForEE(long value) {
		_value = value;
	}

	public long getValue() {
		return _value;
	}

	private long _value;

}