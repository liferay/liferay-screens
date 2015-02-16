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

package com.liferay.mobile.screens.ddl.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractor;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractorImpl;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListRowsListener;

import java.util.Locale;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListScreenlet
	extends BaseListScreenlet<DDLEntry, DDLListInteractor>
	implements DDLListRowsListener {

	public DDLListScreenlet(Context context) {
		this(context, null);
	}

	public DDLListScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public DDLListScreenlet(
            Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	public DDLListInteractor getInteractor() {
		DDLListInteractor interactor = super.getInteractor();

		if (interactor == null) {
			interactor = new DDLListInteractorImpl(getScreenletId());

			setInteractor(interactor);
		}

		return interactor;
	}

	public void loadPage(int page) {
		Locale locale = getResources().getConfiguration().locale;

		int startRow = getFirstRowForPage(page);
		int endRow = getFirstRowForPage(page + 1);

		try {
			getInteractor().loadRows(_recordSetId, startRow, endRow, locale);
		}
		catch (Exception e) {
			onListRowsFailure(startRow, endRow, e);
		}
	}

    public int getRecordSetId() {
        return _recordSetId;
    }

    public void setRecordSetId(int recordSetId) {
        _recordSetId = recordSetId;
    }


	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.DDLListScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(
			R.styleable.DDLListScreenlet_layoutId, 0);

		_firstPageSize = typedArray.getInteger(
			R.styleable.DDLListScreenlet_firstPageSize, _FIRST_PAGE_SIZE);

		_pageSize = typedArray.getInteger(
			R.styleable.DDLListScreenlet_pageSize, _PAGE_SIZE);

		_autoLoad = typedArray.getBoolean(
			R.styleable.DDLListScreenlet_autoLoad, true);

        _recordSetId = typedArray.getInteger(
                R.styleable.DDLListScreenlet_recordSetId, 0);

		typedArray.recycle();

		return LayoutInflater.from(getContext()).inflate(layoutId, null);
	}

	private int _recordSetId;

}