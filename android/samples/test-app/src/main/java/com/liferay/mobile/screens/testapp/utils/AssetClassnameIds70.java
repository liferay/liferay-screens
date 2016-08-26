package com.liferay.mobile.screens.testapp.utils;

/**
 * @author Javier Gamarra
 */
public enum AssetClassnameIds70 {

	// Users and sites
	GROUP(20045),
	LAYOUT(20047),
	ORGANIZATION(20059),
	USER(20087),
	USER_GROUP(20088),

	// Blogs
	BLOGS_ENTRY(20011),

	// Bookmarks
	BOOKMARKS_ENTRY(27401),
	BOOKMARKS_FOLDER(27402),

	// Calendar
	CALENDAR_EVENT(28001),

	// Document Library
	DLFILE_ENTRY(20015),
	DLFILE_ENTRY_METADATA(20016),
	DLFILE_ENTRY_TYPE(20017),
	DLFILE_RANK(20018),
	DLFILE_SHORTCUT(20019),
	DLFILE_VERSION(20020),

	// DDL
	DDLRECORD(29101),
	DDLRECORD_SET(29102),

	// Journal
	JOURNAL_ARTICLE(29501),
	JOURNAL_FOLDER(29506),

	// Message Board
	MBMESSAGE(20032),
	MBTHREAD(20034),
	MBCATEGORY(20029),
	MBDISCUSSION(20030),
	MBMAILING_LIST(20031),

	// Wiki
	WIKI_PAGE(27902),
	WIKI_PAGE_RESOURCE(27903),
	WIKI_NODE(27901);

	public long getValue() {
		return _value;
	}

	AssetClassnameIds70(long value) {
		_value = value;
	}

	private long _value;

}
