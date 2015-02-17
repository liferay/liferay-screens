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
import com.liferay.mobile.screens.context.LiferayServerContext;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class AssetListScreenlet
	extends BaseListScreenlet<AssetEntry, AssetListInteractor>
	implements AssetListInteractorListener {

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

    @Override
    protected void loadRows(int startRow, int endRow, Locale locale) throws Exception {
        getInteractor().loadRows(_groupId, _classNameId, startRow, endRow, locale);
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
		_classNameId = typedArray.getInt(
			R.styleable.AssetListScreenlet_classNameId, 0);
		_groupId = typedArray.getInteger(
			R.styleable.AssetListScreenlet_groupId,
			(int) LiferayServerContext.getGroupId());
		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	private int _classNameId;
	private int _groupId;

}