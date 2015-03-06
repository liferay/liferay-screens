/*
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

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DDLListAdapter;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;

import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListMaterialView extends BaseListScreenletView<DDLEntry, DDLListAdapter>
        implements DDLListViewModel {

    public DDLListMaterialView(Context context) {
        super(context);
    }

    public DDLListMaterialView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDLListMaterialView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

	@Override
	public void showFinishOperation(int page, List<DDLEntry> entries, int rowCount) {
		DDLListScreenlet screenlet = (DDLListScreenlet) getParent();
		DDLListAdapter adapter = (DDLListAdapter) getAdapter();

		adapter.setLabelFields(screenlet.getLabelFields());

		super.showFinishOperation(page, entries, rowCount);
	}

    @Override
    protected DDLListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new DDLListAdapter(itemLayoutId, itemProgressLayoutId, this);
    }

	protected void init() {
		DefaultTheme.initIfThemeNotPresent(getContext());

		int itemLayoutId = R.layout.list_item_material;
		int itemProgressLayoutId = R.layout.list_item_progress_material;

		_recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		_progressBar = (ProgressBar) findViewById(R.id.progress_bar);

		DDLListAdapter adapter = createListAdapter(itemLayoutId, itemProgressLayoutId);
		_recyclerView.setAdapter(adapter);
		_recyclerView.setHasFixedSize(true);
		_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

	}

}