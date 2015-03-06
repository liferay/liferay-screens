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

package com.liferay.mobile.screens.viewsets.material.ddl.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.viewsets.R;

import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListMaterialAdapter
	extends BaseListAdapter<DDLEntry, DDLListMaterialAdapter.TwoTextsViewHolder> {

	public static class TwoTextsViewHolder extends BaseListAdapter.ViewHolder {

		public TextView subtitleTextView;

		public TwoTextsViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			this.subtitleTextView = (TextView) view.findViewById(R.id.subtitle);
		}
	}

    public DDLListMaterialAdapter(
		int layoutId, int progressLayoutId, BaseListAdapterListener listener) {

        super(layoutId, progressLayoutId, listener);
    }

    public void setLabelFields(List<String> labelFields) {
        _labelFields = labelFields;
    }

	@Override
	public TwoTextsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		View view;

		if (viewType == LAYOUT_TYPE_DEFAULT) {
			view = inflater.inflate(getLayoutId(), parent, false);
		}
		else {
			view = inflater.inflate(getProgressLayoutId(), parent, false);
		}

		return new TwoTextsViewHolder(view, getListener());
	}

    @Override
    protected void fillHolder(DDLEntry entry, TwoTextsViewHolder holder) {
        StringBuilder builder = new StringBuilder();

		String titleField = entry.getValue(_labelFields.get(0));

		for (int i = 1; i < _labelFields.size(); ++i) {
			String field = _labelFields.get(i);
			String value = entry.getValue(field);
			if (value!= null && !value.isEmpty()) {
				builder.append(value);
				builder.append(" ");
			}
		}

        holder.textView.setText(titleField);
		holder.subtitleTextView.setText(builder.toString());
    }

    private List<String> _labelFields;

}