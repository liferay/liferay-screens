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

package com.liferay.mobile.screens.base.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liferay.mobile.screens.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public abstract class BaseListAdapter<E, H extends BaseListAdapter.ViewHolder>
	extends RecyclerView.Adapter<H> {

	public BaseListAdapter(
		int layoutId, int progressLayoutId, BaseListAdapterListener listener) {

		_entries = new ArrayList<>();
		_layoutId = layoutId;
		_progressLayoutId = progressLayoutId;
		_listener = listener;
	}

	public List<E> getEntries() {
		return _entries;
	}

	public void setEntries(List<E> entries) {
		_entries = entries;
	}

	@Override
	public int getItemCount() {
		return _rowCount;
	}

	public BaseListAdapterListener getListener() {
		return _listener;
	}

	@Override
	public int getItemViewType(int position) {
		E entry = _entries.get(position);

		return (entry != null) ? LAYOUT_TYPE_DEFAULT : LAYOUT_TYPE_PROGRESS;
	}

	public int getLayoutId() {
		return _layoutId;
	}

	public int getProgressLayoutId() {
		return _progressLayoutId;
	}

	@Override
	public void onBindViewHolder(H holder, int position) {
		E entry = _entries.get(position);

		if (entry != null) {
			fillHolder(entry, holder);
		}
		else {
			_listener.onPageNotFound(position);
		}
	}

	@Override
	public H onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		View view;

		if (viewType == LAYOUT_TYPE_DEFAULT) {
			view = inflater.inflate(_layoutId, parent, false);
		}
		else {
			view = inflater.inflate(_progressLayoutId, parent, false);
		}

		return (H) new ViewHolder(view, _listener);
	}

	public void setRowCount(int rowCount) {
		_rowCount = rowCount;
	}

	protected abstract void fillHolder(E entry, H holder);
	protected static final int LAYOUT_TYPE_DEFAULT = 0;
	protected static final int LAYOUT_TYPE_PROGRESS = 1;
	private List<E> _entries;
	private int _layoutId;
	private BaseListAdapterListener _listener;
	private int _progressLayoutId;
	private int _rowCount;

	public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		public TextView textView;

		public ViewHolder(View view, BaseListAdapterListener listener) {
			super(view);

			this.textView = (TextView) view.findViewById(R.id.liferay_list_title);
			_listener = listener;
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			_listener.onItemClick(getPosition(), v);
		}

		private BaseListAdapterListener _listener;
	}

}