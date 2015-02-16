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
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.util.LiferayServerContext;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class AssetListScreenlet
	extends BaseListScreenlet<AssetEntry, AssetListInteractor>
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

	@Override
	public AssetListInteractor getInteractor() {
		AssetListInteractor interactor = super.getInteractor();

		if (interactor == null) {
			interactor = new AssetListInteractorImpl(getScreenletId());

			setInteractor(interactor);
		}

		return interactor;
	}

	public void loadPage(int page) {
		Locale locale = getResources().getConfiguration().locale;

		int startRow = getFirstRowForPage(page);
		int endRow = getFirstRowForPage(page + 1);

		try {
			getInteractor().loadRows(_groupId, _classNameId, startRow, endRow, locale);
		}
		catch (Exception e) {
			onListRowsFailure(startRow, endRow, e);
		}
	}

	public int getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(int classNameId) {
		_classNameId = classNameId;
	}

	public int getGroupId() {
		return _groupId;
	}

	public void setGroupId(int groupId) {
		_groupId = groupId;
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

	private int _classNameId;
	private int _groupId;

}