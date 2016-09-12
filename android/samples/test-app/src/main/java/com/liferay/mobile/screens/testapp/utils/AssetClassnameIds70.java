package com.liferay.mobile.screens.testapp.utils;

/**
 * @author Javier Gamarra
 */
public enum AssetClassnameIds70 {

	// Users and sites
	GROUP(20045, "com.liferay.portal.kernel.model.Group"),
	LAYOUT(20047, "com.liferay.portal.kernel.model.Layout"),
	ORGANIZATION(20059, "com.liferay.portal.kernel.model.Organization"),
	USER(20087, "com.liferay.portal.kernel.model.User"),
	USER_GROUP(20088, "com.liferay.portal.kernel.model.UserGroup"),

	// Blogs
	BLOGS_ENTRY(20011, "com.liferay.blogs.kernel.model.BlogsEntry"),

	// Bookmarks
	BOOKMARKS_ENTRY(27401, "com.liferay.bookmarks.model.BookmarksEntry"),
	BOOKMARKS_FOLDER(27402, "com.liferay.bookmarks.model.BookmarksFolder"),

	// Calendar
	CALENDAR_EVENT(28001, "com.liferay.calendar.model.Calendar"),

	// Document Library
	DLFILE_ENTRY(20015, "com.liferay.document.library.kernel.model.DLFileEntry"),
	DLFILE_ENTRY_METADATA(20016, "com.liferay.document.library.kernel.model.DLFileEntryMetadata"),
	DLFILE_ENTRY_TYPE(20017, "com.liferay.document.library.kernel.model.DLFileEntryType"),
	DLFILE_RANK(20018, "com.liferay.document.library.kernel.model.DLFileRank"),
	DLFILE_SHORTCUT(20019, "com.liferay.document.library.kernel.model.DLFileShortcut"),
	DLFILE_VERSION(20020, "com.liferay.document.library.kernel.model.DLFileVersion"),

	// DDL
	DDLRECORD(29101, "com.liferay.dynamic.data.lists.model.DDLRecord"),
	DDLRECORD_SET(29102, "com.liferay.dynamic.data.lists.model.DDLRecordSet"),

	// Journal
	JOURNAL_ARTICLE(29501, "com.liferay.journal.model.JournalArticle"),
	JOURNAL_FOLDER(29506, "com.liferay.journal.model.JournalFolder"),

	// Message Board
	MBMESSAGE(20032, "com.liferay.message.boards.kernel.model.MBMessage"),
	MBTHREAD(20034, "com.liferay.message.boards.kernel.model.MBThread"),
	MBCATEGORY(20029, "com.liferay.message.boards.kernel.model.MBCategory"),
	MBDISCUSSION(20030, "com.liferay.message.boards.kernel.model.MBDiscussion"),
	MBMAILING_LIST(20031, "com.liferay.message.boards.kernel.model.MBMailingList"),

	// Wiki
	WIKI_PAGE(27902, "com.liferay.wiki.model.WikiPage"),
	WIKI_PAGE_RESOURCE(27903, "com.liferay.wiki.model.WikiPageResource"),
	WIKI_NODE(27901, "com.liferay.wiki.model.WikiNode");

	private final long value;
	private final String className;

	AssetClassnameIds70(long value, String className) {
		this.value = value;
		this.className = className;
	}

	public long getValue() {
		return value;
	}

	public String getClassName() {
		return className;
	}
}
