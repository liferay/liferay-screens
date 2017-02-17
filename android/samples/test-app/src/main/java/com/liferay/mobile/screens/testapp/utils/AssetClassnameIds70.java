package com.liferay.mobile.screens.testapp.utils;

/**
 * @author Javier Gamarra
 */
public enum AssetClassnameIds70 {

	USER(20087, "com.liferay.portal.kernel.model.User"), BLOGS_ENTRY(20011,
		"com.liferay.blogs.kernel.model.BlogsEntry"), BOOKMARKS_ENTRY(27301,
		"com.liferay.bookmarks.model.BookmarksEntry"), BOOKMARKS_FOLDER(27302,
		"com.liferay.bookmarks.model.BookmarksFolder"), DL_FILE_ENTRY(20015,
		"com.liferay.document.library.kernel.model.DLFileEntry"), DL_FOLDER_ENTRY(20021,
		"com.liferay.document.library.kernel.model.DLFolder"), //DDLRECORD(29101, "com.liferay.dynamic.data.lists.model.DDLRecord"),
	//DDLRECORD_SET(29102, "com.liferay.dynamic.data.lists.model.DDLRecordSet"),
	JOURNAL_ARTICLE(29591, "com.liferay.journal.model.JournalArticle"), JOURNAL_FOLDER(29596,
		"com.liferay.journal.model.JournalFolder");

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
