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
import com.liferay.mobile.screens.assetlist.interactor.AssetListPageListener;
import com.liferay.mobile.screens.themes.R;

import java.util.List;

/**
 * @author Silvio Santos
 */
public class AssetListScreenletView extends RecyclerView
	implements AssetListListener, AssetListPageListener {

	public AssetListScreenletView(Context context) {
		this(context, null);
	}

	public AssetListScreenletView(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public AssetListScreenletView(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);

		int itemLayoutId = R.layout.asset_list_item_default;
		int itemProgressLayoutId = R.layout.asset_list_item_progress_default;

		AssetListAdapter adapter = new AssetListAdapter(
			itemLayoutId, itemProgressLayoutId, this);

		setAdapter(adapter);
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

	@Override
	public void onPageNotFound(int row) {
	}

}