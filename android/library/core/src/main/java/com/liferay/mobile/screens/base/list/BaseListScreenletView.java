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

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public abstract class BaseListScreenletView<
	E extends Parcelable, H extends BaseListAdapter.ViewHolder, A extends BaseListAdapter<E, H>>
	extends FrameLayout
	implements BaseListViewModel<E>, BaseListAdapterListener {

	public BaseListScreenletView(Context context) {
		super(context);
	}

	public BaseListScreenletView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public BaseListScreenletView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onItemClick(int position, View view) {
		BaseListScreenlet screenlet = ((BaseListScreenlet) getParent());
		List<E> entries = getAdapter().getEntries();

		// we do not want to crash if the user manages to do a phantom click
		if (!entries.isEmpty() && entries.size() > position && screenlet.getListener() != null) {
			screenlet.getListener().onListItemSelected(entries.get(position), view);
		}
	}

	@Override
	public void showStartOperation(String actionName) {
		_progressBar.setVisibility(View.VISIBLE);
		_recyclerView.setVisibility(View.GONE);
		LiferayLogger.i("loading list");
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new AssertionError("Use showFinishOperation(page, entries, rowCount) instead");
	}

	@Override
	public void showFinishOperation(int page, List<E> serverEntries, int rowCount) {
		LiferayLogger.i("loaded page " + page + " of list with " + serverEntries);

		_progressBar.setVisibility(View.GONE);
		_recyclerView.setVisibility(View.VISIBLE);

		A adapter = getAdapter();

		addNewServerEntries(page, serverEntries, rowCount, adapter);

		adapter.setRowCount(rowCount);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		throw new AssertionError("Use showFinishOperation(page, entries, rowCount) instead");
	}

	@Override
	public void showFinishOperation(int page, Exception e) {
		_progressBar.setVisibility(View.GONE);
		_recyclerView.setVisibility(View.VISIBLE);
		LiferayLogger.e(getContext().getString(R.string.loading_list_error), e);
	}

	@Override
	public void onPageNotFound(int row) {
		BaseListScreenlet screenlet = (BaseListScreenlet) getParent();

		screenlet.loadPageForRow(row);
	}

	public A getAdapter() {
		return (A) _recyclerView.getAdapter();
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle) inState;
		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		List<E> entries = state.getParcelableArrayList(_STATE_ENTRIES);

		A adapter = getAdapter();
		adapter.setRowCount(state.getInt(_STATE_ROW_COUNT));
		adapter.setEntries(entries);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		A adapter = getAdapter();
		ArrayList<E> entries = (ArrayList<E>) adapter.getEntries();

		Bundle state = new Bundle();
		state.putParcelableArrayList(_STATE_ENTRIES, entries);
		state.putSerializable(_STATE_ROW_COUNT, adapter.getItemCount());
		state.putParcelable(_STATE_SUPER, superState);

		return state;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		DefaultTheme.initIfThemeNotPresent(getContext());

		int itemLayoutId = getItemLayoutId();
		int itemProgressLayoutId = getItemProgressLayoutId();

		_recyclerView = (RecyclerView) findViewById(R.id.liferay_recycler_list);
		_progressBar = (ProgressBar) findViewById(R.id.liferay_progress);

		A adapter = createListAdapter(itemLayoutId, itemProgressLayoutId);
		_recyclerView.setAdapter(adapter);
		_recyclerView.setHasFixedSize(true);
		_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		DividerItemDecoration dividerItemDecoration = getDividerDecoration();
		if (dividerItemDecoration != null) {
			_recyclerView.addItemDecoration(
				getDividerDecoration());
		}

	}

	protected List<E> createAllEntries(int page, List<E> serverEntries, int rowCount, A adapter) {
		List<E> entries = adapter.getEntries();
		List<E> allEntries = new ArrayList<>(
			Collections.<E>nCopies(rowCount, null));

		for (int i = 0; i < entries.size(); i++) {
			allEntries.set(i, entries.get(i));
		}

		BaseListScreenlet screenlet = ((BaseListScreenlet) getParent());

		int firstRowForPage = screenlet.getFirstRowForPage(page);

		for (int i = 0; i < serverEntries.size(); i++) {
			allEntries.set(i + firstRowForPage, serverEntries.get(i));
		}
		return allEntries;
	}

	protected DividerItemDecoration getDividerDecoration() {
		return new DividerItemDecoration(getResources().getDrawable(R.drawable.pixel_grey));
	}

	protected int getItemLayoutId() {
		return R.layout.list_item_default;
	}

	protected int getItemProgressLayoutId() {
		return R.layout.list_item_progress_default;
	}

	protected ProgressBar getProgressBar() {
		return _progressBar;
	}

	protected void setProgressBar(ProgressBar value) {
		_progressBar = value;
	}

	protected RecyclerView getRecyclerView() {
		return _recyclerView;
	}

	protected void setRecyclerView(RecyclerView value) {
		_recyclerView = value;
	}

	protected abstract A createListAdapter(int itemLayoutId, int itemProgressLayoutId);

	private void addNewServerEntries(int page, List<E> serverEntries, int rowCount, A adapter) {
		BaseListScreenlet screenlet = ((BaseListScreenlet) getParent());
		int firstRowForPage = screenlet.getFirstRowForPage(page);

		List<E> entries = adapter.getEntries();
		for (int i = entries.size(); i < rowCount; i++) {
			entries.add(null);
		}

		for (int i = 0; i < serverEntries.size(); i++) {
			entries.set(i + firstRowForPage, serverEntries.get(i));
		}
	}

	private static final String _STATE_ENTRIES = "entries";
	private static final String _STATE_ROW_COUNT = "rowCount";
	private static final String _STATE_SUPER = "super";

	protected ProgressBar _progressBar;
	protected RecyclerView _recyclerView;

	private BaseScreenlet _screenlet;

}