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
import com.liferay.mobile.screens.viewsets.base.list.BaseListScreenletView;


/**
 * @author Silvio Santos
 */
public class AssetListDefaultView extends BaseListScreenletView<AssetEntry, AssetListAdapter>
	implements AssetListViewModel {

	public AssetListDefaultView(Context context) {
		super(context, null);

		init(context);
	}

	public AssetListDefaultView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);

		init(context);
	}

	public AssetListDefaultView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		init(context);
	}

    @Override
    protected AssetListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new AssetListAdapter(itemLayoutId, itemProgressLayoutId, this);
    }

}