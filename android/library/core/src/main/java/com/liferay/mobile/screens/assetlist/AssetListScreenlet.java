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
	implements AssetListListener {

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

	@Override
	public AssetListInteractor getInteractor() {
		AssetListInteractor interactor = super.getInteractor();

		if (interactor == null) {
			interactor = new AssetListInteractorImpl(
				getScreenletId(), _firstPageSize, _pageSize);

			setInteractor(interactor);
		}

		return interactor;
	}

	public void load(int page) {
		Locale locale = getResources().getConfiguration().locale;

		try {
			getInteractor().loadPage(_groupId, _classNameId, page, locale);
		}
		catch (Exception e) {
			onAssetListLoadFailure(e);
		}
	}

	public void loadPageForRow(int row) {
		Locale locale = getResources().getConfiguration().locale;

		try {
			getInteractor().loadPageForRow(_groupId, _classNameId, row, locale);
		}
		catch (Exception e) {
			onAssetListLoadFailure(e);
		}
	}

	@Override
	public void onAssetListLoadFailure(Exception e) {
		AssetListListener listenerView = (AssetListListener)getScreenletView();
		listenerView.onAssetListLoadFailure(e);

		if (_listener != null) {
			_listener.onAssetListLoadFailure(e);
		}
	}

	@Override
	public void onAssetListPageReceived(
		int page, List<AssetEntry> entries, int rowCount) {

		AssetListListener listenerView = (AssetListListener)getScreenletView();
		listenerView.onAssetListPageReceived(page, entries, rowCount);

		if (_listener != null) {
			_listener.onAssetListPageReceived(page, entries, rowCount);
		}
	}

	public void setListener(AssetListListener listener) {
		_listener = listener;
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
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (_autoLoad) {
			Locale locale = getResources().getConfiguration().locale;

			try {
				getInteractor().loadPage(_groupId, _classNameId, 0, locale);
			}
			catch (Exception e) {
				onAssetListLoadFailure(e);
			}
		}
	}

	@Override
	protected void onUserAction(String userActionName) {
	}

	private static final int _FIRST_PAGE_SIZE = 50;

	private static final int _PAGE_SIZE = 25;

	private boolean _autoLoad;
	private int _classNameId;
	private int _firstPageSize;
	private int _groupId;
	private AssetListListener _listener;
	private int _pageSize;

}