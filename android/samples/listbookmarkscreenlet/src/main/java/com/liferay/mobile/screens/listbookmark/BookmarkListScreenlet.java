package com.liferay.mobile.screens.listbookmark;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;

/**
 * @author Javier Gamarra
 */
public class BookmarkListScreenlet extends BaseListScreenlet<Bookmark, BookmarkListInteractorImpl> {

	public BookmarkListScreenlet(Context context) {
		super(context);
	}

	public BookmarkListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BookmarkListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BookmarkListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
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
	public void error(Exception e, String userAction) {

	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.BookmarkListScreenlet, 0, 0);
		groupId = typedArray.getInt(R.styleable.BookmarkListScreenlet_groupId, (int) LiferayServerContext.getGroupId());
		_folderId = typedArray.getInt(R.styleable.BookmarkListScreenlet_folderId, 0);
		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected void loadRows(BookmarkListInteractorImpl interactor) throws Exception {

		((BookmarkListListener) getListener()).interactorCalled();

		interactor.start(_folderId);
	}

	@Override
	protected BookmarkListInteractorImpl createInteractor(String actionName) {
		return new BookmarkListInteractorImpl();
	}

	private long _folderId;
}
