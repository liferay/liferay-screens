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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.list;

import androidx.annotation.NonNull;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.model.Record;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListAdapter extends BaseListAdapter<Record, BaseListAdapter.ViewHolder> {

    public DDLListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @NonNull
    @Override
    public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
        return new ViewHolder(view, listener);
    }

    @Override
    protected void fillHolder(Record entry, ViewHolder holder) {

        StringBuilder builder = new StringBuilder();

        for (String field : getLabelFields()) {
            Object value = entry.getServerValue(field);
            if (value != null) {
                builder.append(value.toString());
                builder.append(" ");
            }
        }

        holder.textView.setText(builder.toString());
    }
}