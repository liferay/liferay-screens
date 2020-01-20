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

package com.liferay.mobile.screens.viewsets.defaultviews.asset.list;

import android.support.annotation.NonNull;
import android.view.View;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;

/**
 * @author Silvio Santos
 */
public class AssetListAdapter extends BaseListAdapter<AssetEntry, BaseListAdapter.ViewHolder> {

    public AssetListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new ViewHolder(view, listener);
    }

    @Override
    protected void fillHolder(AssetEntry entry, ViewHolder holder) {
        if (!entry.getTitle().isEmpty()) {
            holder.textView.setText(entry.getTitle());
        } else {
            holder.textView.setText(String.valueOf(entry.getEntryId()));
        }
    }
}