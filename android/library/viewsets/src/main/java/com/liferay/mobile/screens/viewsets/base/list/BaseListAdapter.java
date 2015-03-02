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

package com.liferay.mobile.screens.viewsets.base.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liferay.mobile.screens.viewsets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public abstract class BaseListAdapter<E>
	extends RecyclerView.Adapter<BaseListAdapter.ViewHolder> {

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

	@Override
	public int getItemCount() {
		return _rowCount;
	}

	@Override
	public int getItemViewType(int position) {
		E entry = _entries.get(position);

		if (entry != null) {
			return _LAYOUT_TYPE_DEFAULT;
		}

		return _LAYOUT_TYPE_PROGRESS;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		E entry = _entries.get(position);

		if (entry != null) {
			fillHolder(entry, holder);
		}
		else {
			_listener.onPageNotFound(position);
		}
	}

    protected abstract void fillHolder(E entry, ViewHolder holder);

    @Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());

		View view;

		if (viewType == _LAYOUT_TYPE_DEFAULT) {
			view = inflater.inflate(_layoutId, parent, false);
		}
		else {
			view = inflater.inflate(_progressLayoutId, parent, false);
		}

		return new ViewHolder(view);
	}

	public void setEntries(List<E> entries) {
		_entries = entries;
	}

	public void setRowCount(int rowCount) {
		_rowCount = rowCount;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {

		public TextView textView;

		public ViewHolder(View view) {
			super(view);

			this.textView = (TextView)view.findViewById(R.id.title);
		}
	}

	private static final int _LAYOUT_TYPE_DEFAULT = 0;

	private static final int _LAYOUT_TYPE_PROGRESS = 1;

	private List<E> _entries;
	private int _layoutId;
	private BaseListAdapterListener _listener;
	private int _progressLayoutId;
	private int _rowCount;

}