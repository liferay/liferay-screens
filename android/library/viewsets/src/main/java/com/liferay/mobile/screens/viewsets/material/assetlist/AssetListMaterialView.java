/*
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

package com.liferay.mobile.screens.viewsets.material.assetlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.assetlist.view.AssetListViewModel;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.assetlist.AssetListAdapter;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;


/**
 * @author Silvio Santos
 */
public class AssetListMaterialView
	extends BaseListScreenletView<AssetEntry, AssetListAdapter.ViewHolder, AssetListAdapter>
	implements AssetListViewModel {

	public AssetListMaterialView(Context context) {
		super(context);
	}

	public AssetListMaterialView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetListMaterialView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

    @Override
    protected AssetListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new AssetListAdapter(itemLayoutId, itemProgressLayoutId, this);
    }

	protected void init() {
		DefaultTheme.initIfThemeNotPresent(getContext());

		int itemLayoutId = R.layout.list_item_material;
		int itemProgressLayoutId = R.layout.list_item_progress_material;

		_recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		_progressBar = (ProgressBar) findViewById(R.id.progress_bar);

		AssetListAdapter adapter = createListAdapter(itemLayoutId, itemProgressLayoutId);
		_recyclerView.setAdapter(adapter);
		_recyclerView.setHasFixedSize(true);
		_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		_recyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R
			.drawable.pixel_grey)));

	}

}