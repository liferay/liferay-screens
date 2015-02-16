/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractor;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractorImpl;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListRowsListener;
import com.liferay.mobile.screens.util.LiferayServerContext;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListScreenlet
	extends BaseScreenlet<BaseViewModel, DDLListInteractor>
	implements DDLListRowsListener {

	public DDLListScreenlet(Context context) {
		this(context, null);
	}

	public DDLListScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public DDLListScreenlet(
            Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	public DDLListInteractor getInteractor() {
		DDLListInteractor interactor = super.getInteractor();

		if (interactor == null) {
			interactor = new DDLListInteractorImpl(getScreenletId());

			setInteractor(interactor);
		}

		return interactor;
	}

	public void loadPage(int page) {
		Locale locale = getResources().getConfiguration().locale;

		int startRow = getFirstRowForPage(page);
		int endRow = getFirstRowForPage(page + 1);

		try {
			getInteractor().loadRows(_recordSetId, startRow, endRow, locale);
		}
		catch (Exception e) {
			onDDLListRowsFailure(startRow, endRow, e);
		}
	}

	public void loadPageForRow(int row) {
		loadPage(getPageFromRow(row));
	}

	@Override
	public void onDDLListRowsFailure(int startRow, int endRow, Exception e) {
		int page = getPageFromRow(startRow);

		DDLListListener listenerView = (DDLListListener)getScreenletView();
		listenerView.onDDLListPageFailed(page, e);

		if (_listener != null) {
			_listener.onDDLListPageFailed(page, e);
		}
	}

	@Override
	public void onDDLListRowsReceived(
		int startRow, int endRow, List<DDLEntry> entries, int rowCount) {

		int page = getPageFromRow(startRow);

		DDLListListener listenerView = (DDLListListener)getScreenletView();
		listenerView.onDDLListPageReceived(page, entries, rowCount);

		if (_listener != null) {
			_listener.onDDLListPageReceived(page, entries, rowCount);
		}
	}

	public int getFirstRowForPage(int page) {
		if (page == 0) {
			return 0;
		}

		return (_firstPageSize + (page - 1) * _pageSize);
	}

	public int getPageFromRow(int row) {
		if (row < _firstPageSize) {
			return 0;
		}

		return ((row - _firstPageSize) / _pageSize) + 1;
	}

	public boolean isAutoLoad() {
		return _autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		_autoLoad = autoLoad;
	}

    public int getRecordSetId() {
        return _recordSetId;
    }

    public void setRecordSetId(int recordSetId) {
        _recordSetId = recordSetId;
    }

	public int getFirstPageSize() {
		return _firstPageSize;
	}

	public void setFirstPageSize(int firstPageSize) {
		_firstPageSize = firstPageSize;
	}

	public DDLListListener getListener() {
		return _listener;
	}

	public void setListener(DDLListListener listener) {
		_listener = listener;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public void setPageSize(int pageSize) {
		_pageSize = pageSize;
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.DDLListScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(
			R.styleable.DDLListScreenlet_layoutId, 0);

		_firstPageSize = typedArray.getInteger(
			R.styleable.DDLListScreenlet_firstPageSize, _FIRST_PAGE_SIZE);

		_pageSize = typedArray.getInteger(
			R.styleable.DDLListScreenlet_pageSize, _PAGE_SIZE);

		_autoLoad = typedArray.getBoolean(
			R.styleable.DDLListScreenlet_autoLoad, true);

        _recordSetId = typedArray.getInteger(
                R.styleable.DDLListScreenlet_recordSetId, 0);

		typedArray.recycle();

		return LayoutInflater.from(getContext()).inflate(layoutId, null);
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (_autoLoad) {
			//TODO handle when first page is already loaded
			loadPage(0);
		}
	}

	@Override
	protected void onUserAction(String userActionName) {
	}

	private static final int _FIRST_PAGE_SIZE = 50;

	private static final int _PAGE_SIZE = 25;

	private boolean _autoLoad;
	private int _recordSetId;
	private int _firstPageSize;
	private DDLListListener _listener;
	private int _pageSize;

}