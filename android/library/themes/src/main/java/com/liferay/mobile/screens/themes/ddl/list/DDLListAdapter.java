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

import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.themes.list.ListAdapter;
import com.liferay.mobile.screens.themes.list.ListAdapterListener;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListAdapter
        extends ListAdapter<DDLEntry> {


    public DDLListAdapter(int layoutId, int progressLayoutId, ListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @Override
    protected void fillHolder(DDLEntry entry, ListAdapter.ViewHolder holder) {
        holder.textView.setText(entry.getUser());
    }

}