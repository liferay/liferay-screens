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
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListListener;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListRowsListener;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractor;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractorImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

	public DDLListScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public DDLListInteractor getInteractor() {
		DDLListInteractor interactor = super.getInteractor();

		if (interactor == null) {
            //TODO review interactor creation when DDL form is merged
			interactor = new DDLListInteractorImpl(getScreenletId());

			setInteractor(interactor);
		}

		return interactor;
	}

    @Override
    protected void loadRows(int startRow, int endRow, Locale locale) throws Exception {
        getInteractor().loadRows(_recordSetId, _userId, startRow, endRow, locale);
    }

    @Override
    public void onListRowsReceived(int startRow, int endRow, List<DDLEntry> entries, int rowCount) {
        int page = getPageFromRow(startRow);

        DDLListListener listenerView = (DDLListListener) getScreenletView();
        listenerView.onListPageReceived(page, entries, _labelFields, rowCount);

        if (_listener != null) {
            _listener.onListPageReceived(page, entries, rowCount);
        }
    }

    public int getRecordSetId() {
        return _recordSetId;
    }

    public void setRecordSetId(int recordSetId) {
        _recordSetId = recordSetId;
    }

    public int getUserId() {
        return _userId;
    }

    public void setUserId(int userId) {
        _userId = userId;
    }

    public List<String> getLabelFields() {
        return _labelFields;
    }

    public void setLabelFields(List<String> labelFields) {
        _labelFields = labelFields;
    }

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attributes, R.styleable.DDLListScreenlet, 0, 0);
        _recordSetId = typedArray.getInteger(
                R.styleable.DDLListScreenlet_recordSetId, 0);
        _userId = typedArray.getInteger(
                R.styleable.DDLListScreenlet_userId, 0);
        _labelFields = parse(typedArray.getString(
                R.styleable.DDLListScreenlet_labelFields));
        typedArray.recycle();

        return super.createScreenletView(context, attributes);
	}

    private List<String> parse(String labelFields) {
		if (labelFields == null) {
			throw new IllegalArgumentException("DDLListScreenlet must define 'labelFields' parameter");
		}

        List<String> parsedFields = new ArrayList<String>();
        for (String text : labelFields.split(",")) {
            parsedFields.add(text.trim());
        }

        return parsedFields;
    }

    private int _recordSetId;
    private int _userId;
    private List<String> _labelFields;

}