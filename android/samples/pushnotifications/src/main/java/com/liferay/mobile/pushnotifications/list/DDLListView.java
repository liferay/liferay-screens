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

package com.liferay.mobile.pushnotifications.list;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.defaultviews.ddl.list.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class DDLListView extends BaseListScreenletView<Record, DDLListAdapter.ImageViewHolder, DDLListAdapter>
    implements DDLListViewModel, SwipeRefreshLayout.OnRefreshListener {

    private static final String STATE_LABEL_FIELDS = "ddllistview-label_fields";
    private static final String STATE_SUPER = "ddllistview-super";
    private SwipeRefreshLayout swipeRefreshLayout;

    public DDLListView(Context context) {
        super(context);
    }

    public DDLListView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public DDLListView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
        ((DDLListScreenlet) getParent()).loadPage(0);
    }

    @Override
    public void showFinishOperation(int startRow, int endRow, List<Record> entries, int rowCount) {
        super.showFinishOperation(startRow, endRow, entries, rowCount);

        DDLListScreenlet screenlet = (DDLListScreenlet) getParent();

        getAdapter().setLabelFields(screenlet.getLabelFields());
    }

    @Override
    protected DDLListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new DDLListAdapter(itemLayoutId, itemProgressLayoutId, this);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.ddl_list_item_push;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        swipeRefreshLayout = findViewById(R.id.liferay_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable inState) {
        Bundle state = (Bundle) inState;
        Parcelable superState = state.getParcelable(STATE_SUPER);
        super.onRestoreInstanceState(superState);

        List<String> labelFields = state.getStringArrayList(STATE_LABEL_FIELDS);

        getAdapter().setLabelFields(labelFields);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        Bundle state = new Bundle();
        DDLListScreenlet screenlet = (DDLListScreenlet) getParent();
        state.putStringArrayList(STATE_LABEL_FIELDS, (ArrayList<String>) screenlet.getLabelFields());
        state.putParcelable(STATE_SUPER, superState);

        return state;
    }

    @Override
    protected DividerItemDecoration getDividerDecoration() {
        return null;
    }
}