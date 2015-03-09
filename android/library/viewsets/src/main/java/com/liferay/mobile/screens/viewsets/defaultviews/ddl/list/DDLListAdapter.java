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

import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;

import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListAdapter extends BaseListAdapter<DDLEntry, BaseListAdapter.ViewHolder> {

    public DDLListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    public void setLabelFields(List<String> labelFields) {
        _labelFields = labelFields;
    }

    @Override
    protected void fillHolder(DDLEntry entry, ViewHolder holder) {
        StringBuilder builder = new StringBuilder();

		for (String field : _labelFields) {
			String value = entry.getValue(field);
			if (value!= null && !value.isEmpty()) {
				builder.append(value);
				builder.append(" ");
			}
		}

        holder.textView.setText(builder.toString());
    }

    private List<String> _labelFields;

}