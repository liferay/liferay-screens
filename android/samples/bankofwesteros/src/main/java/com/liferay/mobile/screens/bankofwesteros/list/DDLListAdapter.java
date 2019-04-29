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

package com.liferay.mobile.screens.bankofwesteros.list;

import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.ddl.model.Record;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListAdapter extends com.liferay.mobile.screens.viewsets.westeros.ddl.list.DDLListAdapter {

    public DDLListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
        super(layoutId, progressLayoutId, listener);
    }

    @Override
    protected void fillHolder(Record entry, SwipeActionsViewHolder holder) {
        super.fillHolder(entry, holder);

        holder.imageView.setImageResource(getDrawable(holder.getLayoutPosition()));
    }

    protected int getDrawable(int position) {
        //FIXME add an easier way to do this
        int drawable = R.drawable.issue_open;
        if (position < 1) {
            drawable = R.drawable.issue_reject;
        } else if (position < 2) {
            drawable = R.drawable.done;
        } else if (position < 3) {
            drawable = R.drawable.issue_waiting;
        }
        return drawable;
    }
}