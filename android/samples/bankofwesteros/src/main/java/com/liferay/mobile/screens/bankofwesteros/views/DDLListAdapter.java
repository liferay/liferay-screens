/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.bankofwesteros.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.list.DDLEntry;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListAdapter
	extends BaseListAdapter<DDLEntry, DDLListAdapter.SwipeActionsViewHolder> {

	public static class SwipeActionsViewHolder extends BaseListAdapter.ViewHolder implements View.OnClickListener {

		public SwipeActionsViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			this._subtitleTextView = (TextView) view.findViewById(com.liferay.mobile.screens.viewsets.R.id.subtitle);
			this._stateIconView = (ImageView) view.findViewById(R.id.state_list_icon);

			_listener = listener;

			view.findViewById(R.id.list_handle).setOnClickListener(this);
			view.findViewById(R.id.list_edit).setOnClickListener(this);
			view.findViewById(R.id.list_view).setOnClickListener(this);
			_swipeLayout = (SwipeLayout) view.findViewById(R.id.swipe_layout);

		}

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.list_handle) {
				_swipeLayout.open(true);
			}
			else if (SwipeLayout.Status.Open.equals(_swipeLayout.getOpenStatus())) {
				_listener.onItemClick(getPosition(), v);
			}
		}

		private final BaseListAdapterListener _listener;
		private final TextView _subtitleTextView;
		private final ImageView _stateIconView;
		private final SwipeLayout _swipeLayout;

	}

	public DDLListAdapter(
		int layoutId, int progressLayoutId, BaseListAdapterListener listener) {

		super(layoutId, progressLayoutId, listener);
	}

	public void setLabelFields(List<String> labelFields) {
		_labelFields = labelFields;
	}

	@Override
	public SwipeActionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		View view;

		if (viewType == LAYOUT_TYPE_DEFAULT) {
			view = inflater.inflate(getLayoutId(), parent, false);
			SwipeLayout swipe = (SwipeLayout) view.findViewById(R.id.swipe_layout);
			swipe.setShowMode(SwipeLayout.ShowMode.LayDown);
			swipe.setDragEdge(SwipeLayout.DragEdge.Right);
		}
		else {
			view = inflater.inflate(getProgressLayoutId(), parent, false);
		}

		return new SwipeActionsViewHolder(view, getListener());
	}

	@Override
	protected void fillHolder(DDLEntry entry, SwipeActionsViewHolder holder) {

		StringBuilder builder = new StringBuilder();

		String titleField = entry.getValue(_labelFields.get(0));

		for (int i = 1; i < _labelFields.size(); ++i) {
			String field = _labelFields.get(i);
			String value = entry.getValue(field);
			if (value != null && !value.isEmpty()) {
				builder.append(value);
				builder.append(" ");
			}
		}
		if (builder.length() == 0) {
			String date = new SimpleDateFormat("dd/MM/yyyy").format(entry.getAttributes("createDate"));
			builder.append("Created ");
			builder.append(date);
		}

		holder.textView.setText(titleField);
		holder._subtitleTextView.setText(builder.toString());

		int drawableId = getDrawable(getEntries().indexOf(entry));
		if (holder._stateIconView != null) {
			holder._stateIconView.setImageResource(drawableId);
		}
	}

	private int getDrawable(int position) {
		//FIXME demo code
		int drawable = R.drawable.issue_open;
		if (position < 1) {
			drawable = R.drawable.issue_reject;
		}
		else if (position < 2) {
			drawable = R.drawable.done;
		}
		else if (position < 3) {
			drawable = R.drawable.issue_waiting;
		}
		return drawable;
	}

	private List<String> _labelFields;

}