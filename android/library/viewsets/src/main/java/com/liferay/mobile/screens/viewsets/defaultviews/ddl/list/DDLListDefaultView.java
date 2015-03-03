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

package com.liferay.mobile.screens.viewsets.defaultviews.ddl.list;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.viewsets.base.list.BaseListScreenletView;

import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListDefaultView extends BaseListScreenletView<DDLEntry, DDLListAdapter>
        implements DDLListViewModel {

    public DDLListDefaultView(Context context) {
        super(context);
    }

    public DDLListDefaultView(Context context, AttributeSet attributes) {
        super(context, attributes, 0);
    }

    public DDLListDefaultView(Context context, AttributeSet attributes, int defaultStyle) {
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

}