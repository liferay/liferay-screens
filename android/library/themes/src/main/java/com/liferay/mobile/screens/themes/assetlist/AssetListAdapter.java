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

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.themes.list.ListAdapter;
import com.liferay.mobile.screens.themes.list.ListAdapterListener;

/**
 * @author Silvio Santos
 */
public class AssetListAdapter
        extends ListAdapter<AssetEntry> {

    public AssetListAdapter(int layoutId, int progressLayoutId, ListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @Override
    protected void fillHolder(AssetEntry entry, ListAdapter.ViewHolder holder) {
        holder.textView.setText(entry.getTitle());
    }

}