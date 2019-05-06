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
public enum AssetClassnameIds {

    // Users and sites
    GROUP(20001, "com.liferay.portal.kernel.model.Group"), LAYOUT(20002,
        "com.liferay.portal.kernel.model.Layout"), ORGANIZATION(20003,
        "com.liferay.portal.kernel.model.Organization"), USER(20087,
        "com.liferay.portal.kernel.model.User"), USER_GROUP(20006, "com.liferay.portal.kernel.model.UserGroup"),

    // Blogs
    BLOGS_ENTRY(20011, "com.liferay.blogs.kernel.model.BlogsEntry"),

    // Bookmarks
    BOOKMARKS_ENTRY(20008, "com.liferay.bookmarks.model.BookmarksEntry"), BOOKMARKS_FOLDER(20009,
        "com.liferay.bookmarks.model.BookmarksFolder"),

    // Calendar
    CALENDAR_EVENT(20010, "com.liferay.calendar.model.Calendar"),

    // Document Library
    DLFILE_ENTRY(20015, "com.liferay.document.library.kernel.model.DLFileEntry"), DLFILE_ENTRY_METADATA(20091,
        "com.liferay.document.library.kernel.model.DLFileEntryMetadata"), DLFILE_ENTRY_TYPE(20092,
        "com.liferay.document.library.kernel.model.DLFileEntryType"), DLFILE_RANK(20093,
        "com.liferay.document.library.kernel.model.DLFileRank"), DLFILE_SHORTCUT(20094,
        "com.liferay.document.library.kernel.model.DLFileShortcut"), DLFILE_VERSION(20095,
        "com.liferay.document.library.kernel.model.DLFileVersion"),

    // DDL
    DDLRECORD(20097, "com.liferay.dynamic.data.lists.model.DDLRecord"), DDLRECORD_SET(20098,
        "com.liferay.dynamic.data.lists.model.DDLRecordSet"),

    // Journal
    JOURNAL_ARTICLE(20109, "com.liferay.journal.model.JournalArticle"), JOURNAL_FOLDER(20013,
        "com.liferay.journal.model.JournalFolder"),

    // MessageBoard
    MBMESSAGE(20014, "com.liferay.message.boards.kernel.model.MBMessage"), MBTHREAD(20015,
        "com.liferay.message.boards.kernel.model.MBThread"), MBCATEGORY(20115,
        "com.liferay.message.boards.kernel.model.MBCategory"), MBDISCUSSION(20116,
        "com.liferay.message.boards.kernel.model.MBDiscussion"), MBMAILING_LIST(20117,
        "com.liferay.message.boards.kernel.model.MBMailingList"),

    // Wiki
    WIKI_PAGE(20016, "com.liferay.wiki.model.WikiPage"), WIKI_PAGE_RESOURCE(20153,
        "com.liferay.wiki.model.WikiPageResource"), WIKI_NODE(20152, "com.liferay.wiki.model.WikiNode");

    private final long value;
    private final String className;

    AssetClassnameIds(long value, String className) {
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