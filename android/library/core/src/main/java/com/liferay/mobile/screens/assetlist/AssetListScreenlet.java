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

package com.liferay.mobile.screens.assetlist;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractor;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractorImpl;
import com.liferay.mobile.screens.assetlist.interactor.AssetListRowsListener;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.util.LiferayServerContext;

import java.util.List;
import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class AssetListScreenlet
	extends BaseScreenlet<BaseViewModel, AssetListInteractor>
	implements AssetListRowsListener {

	public AssetListScreenlet(Context context) {
		this(context, null);
	}

	public AssetListScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public AssetListScreenlet(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	public void loadPage(int page) {
		Locale locale = getResources().getConfiguration().locale;

		int startRow = getFirstRowForPage(page);
		int endRow = getFirstRowForPage(page + 1);

		try {
			getInteractor(_LOAD_PAGE_ACTION).loadRows(
				_groupId, _classNameId, startRow, endRow, locale);
		}
		catch (Exception e) {
			onAssetListRowsFailure(startRow, endRow, e);
		}
	}

	public void loadPageForRow(int row) {
		loadPage(getPageFromRow(row));
	}

	@Override
	public void onAssetListRowsFailure(int startRow, int endRow, Exception e) {
		int page = getPageFromRow(startRow);

		AssetListListener listenerView = (AssetListListener)getScreenletView();
		listenerView.onAssetListPageFailed(page, e);

		if (_listener != null) {
			_listener.onAssetListPageFailed(page, e);
		}
	}

	@Override
	public void onAssetListRowsReceived(
		int startRow, int endRow, List<AssetEntry> entries, int rowCount) {

		int page = getPageFromRow(startRow);

		AssetListListener listenerView = (AssetListListener)getScreenletView();
		listenerView.onAssetListPageReceived(page, entries, rowCount);

		if (_listener != null) {
			_listener.onAssetListPageReceived(page, entries, rowCount);
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

	public int getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(int classNameId) {
		_classNameId = classNameId;
	}

	public int getFirstPageSize() {
		return _firstPageSize;
	}

	public void setFirstPageSize(int firstPageSize) {
		_firstPageSize = firstPageSize;
	}

	public int getGroupId() {
		return _groupId;
	}

	public void setGroupId(int groupId) {
		_groupId = groupId;
	}

	public AssetListListener getListener() {
		return _listener;
	}

	public void setListener(AssetListListener listener) {
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

		_classNameId = typedArray.getInt(
			R.styleable.AssetListScreenlet_classNameId, 0);

		_groupId = typedArray.getInteger(
			R.styleable.AssetListScreenlet_groupId,
			(int)LiferayServerContext.getGroupId());

		typedArray.recycle();

		return LayoutInflater.from(getContext()).inflate(layoutId, null);
	}

	@Override
	protected AssetListInteractor createInteractor(String actionName) {
		return new AssetListInteractorImpl(getScreenletId());
	}

	@Override
	protected void onUserAction(String userActionName, AssetListInteractor interactor) {
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (_autoLoad) {
			//TODO handle when first page is already loaded
			loadPage(0);
		}
	}

	private static final String _LOAD_PAGE_ACTION = "loadPage";

	private static final int _FIRST_PAGE_SIZE = 50;

	private static final int _PAGE_SIZE = 25;

	private boolean _autoLoad;
	private int _classNameId;
	private int _firstPageSize;
	private int _groupId;
	private AssetListListener _listener;
	private int _pageSize;

}