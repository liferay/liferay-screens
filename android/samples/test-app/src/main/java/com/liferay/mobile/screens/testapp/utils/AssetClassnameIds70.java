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
 * @author Javier Gamarra
 */
public enum AssetClassnameIds70 {

    // Blogs
    BLOGS_ENTRY(20011, "com.liferay.blogs.kernel.model.BlogsEntry"),

    // Bookmarks
    BOOKMARKS_ENTRY(28401, "com.liferay.bookmarks.model.BookmarksEntry"), BOOKMARKS_FOLDER(28402,
        "com.liferay.bookmarks.model.BookmarksFolder"),

    // Calendar
    CALENDAR_BOOKING(27702, "com.liferay.calendar.model.CalendarBooking"),

    // DDL
    DDL_RECORD(29501, "com.liferay.dynamic.data.lists.model.DDLRecord"),

    // DDMForm
    DDL_FORM_RECORD(31330, "com.liferay.dynamic.data.lists.model.DDLFormRecord"),

    // Document Library
    DL_FILE_ENTRY(20015, "com.liferay.document.library.kernel.model.DLFileEntry"), DL_FOLDER(20021,
        "com.liferay.document.library.kernel.model.DLFolder"),

    // WebContent
    JOURNAL_ARTICLE(29634, "com.liferay.journal.model.JournalArticle"), JOURNAL_FOLDER(29639,
        "com.liferay.journal.model.JournalFolder"),

    // Users and sites
    LAYOUT(20047, "com.liferay.portal.kernel.model.User"), LAYOUT_REVISION(20051,
        "com.liferay.portal.kernel.model.User"), ORGANIZATION(20059, "com.liferay.portal.kernel.model.User"), GROUP(
        20045, "com.liferay.portal.kernel.model.User"), USER(20087, "com.liferay.portal.kernel.model.User"),

    // Message boards
    MB_DISCUSSION(20030, "com.liferay.message.boards.kernel.model.MBDiscussion"), MB_MESSAGE(20032,
        "com.liferay.message.boards.kernel.model.MBMessage"), MB_THREAD(20034,
        "com.liferay.message.boards.kernel.model.MBThread"),

    // Microblogs
    MICROBLOG_ENTRY(28701, "com.liferay.microblogs.model.MicroblogsEntry"),

    // Wiki
    WIKI_PAGE(28802, "com.liferay.wiki.model.WikiPage");

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
