package com.liferay.mobile.screens.listbookmark;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayServerContext;

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
		if (getListener() != null) {
			getListener().loadingFromCache(success);
		}
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		if (getListener() != null) {
			getListener().retrievingOnline(triedInCache, e);
		}
	}

	@Override
	public void storingToCache(Object object) {
		if (getListener() != null) {
			getListener().storingToCache(object);
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.BookmarkListScreenlet, 0, 0);
		Integer offlinePolicy = typedArray.getInteger(
			R.styleable.BookmarkListScreenlet_offlinePolicy,
			OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];
		_groupId = typedArray.getInt(R.styleable.BookmarkListScreenlet_groupId,
			(int) LiferayServerContext.getGroupId());
		_folderId = typedArray.getInt(R.styleable.BookmarkListScreenlet_folderId, 0);
		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected void loadRows(BookmarkListInteractorImpl interactor, int startRow, int endRow, Locale locale) throws Exception {

		((BookmarkListListener) getListener()).interactorCalled();

		interactor.loadRows(startRow, endRow, locale, _groupId, _folderId);
	}

	@Override
	protected BookmarkListInteractorImpl createInteractor(String actionName) {
		return new BookmarkListInteractorImpl(getScreenletId(), _offlinePolicy);
	}

	private long _groupId;
	private long _folderId;
	private OfflinePolicy _offlinePolicy;
}
