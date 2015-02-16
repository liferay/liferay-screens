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

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.themes.list.ListAdapterListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Santos
 */
public class AssetListAdapter
	extends RecyclerView.Adapter<AssetListAdapter.ViewHolder> {

	public AssetListAdapter(
		int layoutId, int progressLayoutId, ListAdapterListener listener) {

		_entries = new ArrayList<>();
		_layoutId = layoutId;
		_progressLayoutId = progressLayoutId;
		_listener = listener;
	}

	public List<AssetEntry> getEntries() {
		return _entries;
	}

	@Override
	public int getItemCount() {
		return _rowCount;
	}

	@Override
	public int getItemViewType(int position) {
		AssetEntry entry = _entries.get(position);

		if (entry != null) {
			return _LAYOUT_TYPE_DEFAULT;
		}

		return _LAYOUT_TYPE_PROGRESS;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		AssetEntry entry = _entries.get(position);

		if (entry != null) {
			holder.textView.setText(entry.getTitle());
		}
		else {
			_listener.onPageNotFound(position);
		}
	}

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

	public void setEntries(List<AssetEntry> entries) {
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

	private List<AssetEntry> _entries;
	private int _layoutId;
	private ListAdapterListener _listener;
	private int _progressLayoutId;
	private int _rowCount;

}