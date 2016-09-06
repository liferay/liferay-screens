package com.liferay.mobile.screens.listbookmark;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;

public class BookmarkEvent extends ListEvent<Bookmark> {

	private Bookmark bookmark;

	public BookmarkEvent() {
		super();
	}

	public BookmarkEvent(Bookmark bookmark) {
		this.bookmark = bookmark;
	}

	@Override
	public String getListKey() {
		return bookmark.getUrl();
	}

	@Override
	public Bookmark getModel() {
		return bookmark;
	}
}
