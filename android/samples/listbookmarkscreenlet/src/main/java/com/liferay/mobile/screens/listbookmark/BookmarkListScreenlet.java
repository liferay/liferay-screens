package com.liferay.mobile.screens.listbookmark;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class BookmarkListScreenlet extends BaseListScreenlet<Bookmark, BookmarkListInteractorImpl> {

	public BookmarkListScreenlet(Context context) {
		super(context);
	}

	public BookmarkListScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public BookmarkListScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	protected void loadRows(BookmarkListInteractorImpl interactor, int startRow, int endRow, Locale locale) throws Exception {
		interactor.loadRows(startRow, endRow, locale, groupId, folderId);
	}

	@Override
	protected BookmarkListInteractorImpl createInteractor(String actionName) {
		return new BookmarkListInteractorImpl(getScreenletId(), OfflinePolicy.REMOTE_ONLY);
	}

	private long groupId = 20525;
	private long folderId = 20622;
}
