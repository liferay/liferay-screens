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

package com.liferay.mobile.screens.themes.ddl.list;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.themes.list.ListAdapterListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListScreenletView extends RecyclerView
	implements BaseListListener<DDLEntry>, ListAdapterListener {

	public DDLListScreenletView(Context context) {
		this(context, null);
	}

	public DDLListScreenletView(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public DDLListScreenletView(
            Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);

		int itemLayoutId = R.layout.ddl_list_item_default;
		int itemProgressLayoutId = R.layout.ddl_list_item_progress_default;

		DDLListAdapter adapter = new DDLListAdapter(
			itemLayoutId, itemProgressLayoutId, this);

		setAdapter(adapter);
		setHasFixedSize(true);
		setLayoutManager(new LinearLayoutManager(context));
	}

	@Override
	public void onListPageFailed(int page, Exception e) {
		//TODO what should we do when the page load fails?
	}

	@Override
	public void onListPageReceived(
		int page, List<DDLEntry> serverEntries, int rowCount) {

		DDLListAdapter adapter = (DDLListAdapter)getAdapter();
		List<DDLEntry> entries = adapter.getEntries();
		List<DDLEntry> allEntries = new ArrayList<>(
			Collections.<DDLEntry>nCopies(rowCount, null));

		for (int i = 0; i < entries.size(); i++) {
			allEntries.set(i, entries.get(i));
		}

		DDLListScreenlet screenlet = ((DDLListScreenlet)getParent());

		int firstRowForPage = screenlet.getFirstRowForPage(page);

		for (int i = 0; i < (serverEntries.size()); i++) {
			allEntries.set(i + firstRowForPage, serverEntries.get(i));
		}

		adapter.setRowCount(rowCount);
		adapter.setEntries(allEntries);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onPageNotFound(int row) {
		DDLListScreenlet screenlet = ((DDLListScreenlet)getParent());

		screenlet.loadPageForRow(row);
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle)inState;
		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		List<DDLEntry> entries = state.getParcelableArrayList(_STATE_ENTRIES);

		DDLListAdapter adapter = (DDLListAdapter)getAdapter();
		adapter.setRowCount(state.getInt(_STATE_ROW_COUNT));
		adapter.setEntries(entries);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		DDLListAdapter adapter = (DDLListAdapter)getAdapter();
		ArrayList<DDLEntry> entries = (ArrayList<DDLEntry>)
			adapter.getEntries();

		Bundle state = new Bundle();
		state.putParcelableArrayList(_STATE_ENTRIES, entries);
		state.putSerializable(_STATE_ROW_COUNT, adapter.getItemCount());
		state.putParcelable(_STATE_SUPER, superState);

		return state;
	}

	private static final String _STATE_ENTRIES = "entries";

	private static final String _STATE_ROW_COUNT = "rowCount";

	private static final String _STATE_SUPER = "super";

}