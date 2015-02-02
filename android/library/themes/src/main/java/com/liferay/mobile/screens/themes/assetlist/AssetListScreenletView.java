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

package com.liferay.mobile.screens.themes.assetlist;

import android.content.Context;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.AttributeSet;

import com.liferay.mobile.screens.assetlist.AssetListListener;

import java.util.List;

/**
 * @author Silvio Santos
 */
public class AssetListScreenletView extends RecyclerView
	implements AssetListListener
{
	public AssetListScreenletView(Context context) {
		this(context, null);
	}

	public AssetListScreenletView(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public AssetListScreenletView(
		Context context, AttributeSet attrs, int defaultStyle) {

		super(context, attrs, defaultStyle);

		setHasFixedSize(true);
		setLayoutManager(new LinearLayoutManager(context));
	}

	@Override
	public void onAssetListLoadFailure(Exception e) {
	}

	@Override
	public void onAssetListPageReceived(
		int firstRowForPage, List<AssetListScreenletEntry> serverEntries,
		int rowCount) {

	}
}