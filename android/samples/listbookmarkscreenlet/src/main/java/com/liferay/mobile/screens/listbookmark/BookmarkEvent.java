package com.liferay.mobile.screens.listbookmark;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;

public class BookmarkEvent extends ListEvent<Bookmark> {

	private final Bookmark bookmark;

	public BookmarkEvent(Bookmark bookmark) {
		this.bookmark = bookmark;
	}

	@Override
	public String getCacheKey() {
		return bookmark.getUrl();
	}

	@Override
	public Bookmark getModel() {
		return bookmark;
	}
}
