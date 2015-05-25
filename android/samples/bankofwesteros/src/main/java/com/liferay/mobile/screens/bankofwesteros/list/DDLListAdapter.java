package com.liferay.mobile.screens.bankofwesteros.list; /**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListAdapter
	extends com.liferay.mobile.screens.viewsets.westeros.ddl.list.DDLListAdapter {

	public DDLListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	protected int getDrawable(int position) {
		int drawable = R.drawable.issue_open;
		if (position < 1) {
			drawable = R.drawable.issue_reject;
		}
		else if (position < 2) {
			drawable = R.drawable.done;
		}
		else if (position < 3) {
			drawable = R.drawable.issue_waiting;
		}
		return drawable;
	}

}