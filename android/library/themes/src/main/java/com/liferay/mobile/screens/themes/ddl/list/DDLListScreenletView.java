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
import android.util.AttributeSet;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListListener;
import com.liferay.mobile.screens.themes.list.ListAdapterListener;
import com.liferay.mobile.screens.themes.list.ListScreenletView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListScreenletView extends ListScreenletView<DDLEntry, DDLListAdapter>
        implements DDLListListener, ListAdapterListener {

    public DDLListScreenletView(Context context) {
        super(context);
    }

    public DDLListScreenletView(Context context, AttributeSet attributes) {
        super(context, attributes, 0);
    }

    public DDLListScreenletView(
            Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public void onListPageReceived(int page, List<DDLEntry> entries, List<String> labelFields, int rowCount) {
        DDLListAdapter adapter = (DDLListAdapter) getAdapter();
        List<DDLEntry> allEntries = createAllEntries(page, entries, rowCount, adapter);

        adapter.setRowCount(rowCount);
        adapter.setEntries(allEntries);
        adapter.setLabelFields(labelFields);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected DDLListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new DDLListAdapter(
                itemLayoutId, itemProgressLayoutId, this);
    }

}