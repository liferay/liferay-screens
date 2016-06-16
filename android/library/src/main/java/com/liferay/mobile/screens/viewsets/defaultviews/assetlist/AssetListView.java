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

package com.liferay.mobile.screens.viewsets.defaultviews.assetlist;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.assetlist.view.AssetListViewModel;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;


/**
 * @author Silvio Santos
 */
public class AssetListView
	extends BaseListScreenletView<AssetEntry, BaseListAdapter.ViewHolder, AssetListAdapter>
	implements AssetListViewModel {

	public AssetListView(Context context) {
		super(context);
	}

	public AssetListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected AssetListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new AssetListAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

}