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

import android.support.annotation.NonNull;
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

	public List<String> getLabelFields() {
		return _labelFields;
	}

	public void setLabelFields(List<String> labelFields) {
		_labelFields = labelFields;
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
		View view = inflater.inflate(
			viewType == LAYOUT_TYPE_DEFAULT ? _layoutId : _progressLayoutId, parent, false);
		return createViewHolder(view, _listener);
	}

	@NonNull
	public abstract H createViewHolder(View view, BaseListAdapterListener listener);

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
	private List<String> _labelFields;

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
			_listener.onItemClick(getAdapterPosition(), v);
		}

		private BaseListAdapterListener _listener;
	}

}