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

package com.liferay.mobile.screens.base.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.base.view.BaseViewModel;

import java.util.List;
import java.util.Locale;

/**
 * @author Silvio Santos
 */
public abstract class BaseListScreenlet<E, N extends Interactor>
        extends BaseScreenlet<BaseViewModel, N>
        implements BaseListInteractorListener<E> {

    public BaseListScreenlet(Context context) {
        this(context, null);
    }

    public BaseListScreenlet(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public BaseListScreenlet(
            Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    public void loadPageForRow(int row) {
        loadPage(getPageFromRow(row));
    }

    @Override
    public void onListRowsFailure(int startRow, int endRow, Exception e) {
        int page = getPageFromRow(startRow);
        if (_listener != null) {
            _listener.onListPageFailed(this, page, e);
        }
    }

    @Override
    public void onListRowsReceived(int startRow, int endRow, List<E> entries, int rowCount) {
        int page = getPageFromRow(startRow);

        BaseListViewModel<E> view = (BaseListViewModel<E>) getScreenletView();
		view.setListPage(page, entries, rowCount);

        if (_listener != null) {
            _listener.onListPageReceived(this, page, entries, rowCount);
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

    public void loadPage(int page) {
        Locale locale = getResources().getConfiguration().locale;

        int startRow = getFirstRowForPage(page);
        int endRow = getFirstRowForPage(page + 1);

        try {
            loadRows(startRow, endRow, locale);
        }
        catch (Exception e) {
            onListRowsFailure(startRow, endRow, e);
        }
    }

    protected abstract void loadRows(int startRow, int endRow, Locale locale) throws Exception;

    public boolean isAutoLoad() {
        return _autoLoad;
    }

    public void setAutoLoad(boolean autoLoad) {
        _autoLoad = autoLoad;
    }

    public int getFirstPageSize() {
        return _firstPageSize;
    }

    public void setFirstPageSize(int firstPageSize) {
        _firstPageSize = firstPageSize;
    }

    public BaseListListener<E> getListener() {
        return _listener;
    }

    public void setListener(BaseListListener<E> listener) {
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
                attributes, R.styleable.AssetListScreenlet, 0, 0);

        int layoutId = typedArray.getResourceId(
                R.styleable.AssetListScreenlet_layoutId, 0);

        _firstPageSize = typedArray.getInteger(
                R.styleable.AssetListScreenlet_firstPageSize, _FIRST_PAGE_SIZE);

        _pageSize = typedArray.getInteger(
                R.styleable.AssetListScreenlet_pageSize, _PAGE_SIZE);

        _autoLoad = typedArray.getBoolean(
                R.styleable.AssetListScreenlet_autoLoad, true);

        typedArray.recycle();

        return LayoutInflater.from(getContext()).inflate(layoutId, null);
    }

    @Override
    protected void onScreenletAttached() {
        super.onScreenletAttached();

        if (_autoLoad) {
            //LMW-176 TODO handle when first page is already loaded
            loadPage(0);
        }
    }

    @Override
    protected void onUserAction(String userActionName) {
    }

    protected static final int _FIRST_PAGE_SIZE = 50;
    protected static final int _PAGE_SIZE = 25;

    protected boolean _autoLoad;
    protected int _firstPageSize;
    protected BaseListListener<E> _listener;
    protected int _pageSize;

}