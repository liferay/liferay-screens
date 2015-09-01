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
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractor;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractorImpl;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractorListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.context.LiferayServerContext;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class AssetListScreenlet
	extends BaseListScreenlet<AssetEntry, AssetListInteractor>
	implements AssetListInteractorListener {

	public AssetListScreenlet(Context context) {
		super(context);
	}

	public AssetListScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetListScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
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
	protected void loadRows(AssetListInteractor interactor, int startRow, int endRow, Locale locale)
		throws Exception {

		interactor.loadRows(_groupId, _classNameId, startRow, endRow, locale);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.AssetListScreenlet, 0, 0);
		_classNameId = typedArray.getInt(
			R.styleable.AssetListScreenlet_classNameId, 0);
		_groupId = typedArray.getInteger(
			R.styleable.AssetListScreenlet_groupId,
			(int) LiferayServerContext.getGroupId());

		int cachePolicy = typedArray.getInt(R.styleable.WebContentDisplayScreenlet_cachePolicy,
			CachePolicy.ONLINE_ONLY.ordinal());
		_cachePolicy = CachePolicy.values()[cachePolicy];

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected AssetListInteractor createInteractor(String actionName) {
		return new AssetListInteractorImpl(getScreenletId(), _cachePolicy);
	}

	private int _classNameId;
	private int _groupId;
	private CachePolicy _cachePolicy;

}