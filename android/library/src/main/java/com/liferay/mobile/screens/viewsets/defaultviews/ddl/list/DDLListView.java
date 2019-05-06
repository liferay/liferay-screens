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
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.ddl.list.view.DDLListViewModel;
import com.liferay.mobile.screens.ddl.model.Record;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListView extends BaseListScreenletView<Record, BaseListAdapter.ViewHolder, DDLListAdapter>
    implements DDLListViewModel {

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
    protected DDLListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new DDLListAdapter(itemLayoutId, itemProgressLayoutId, this);
    }
}