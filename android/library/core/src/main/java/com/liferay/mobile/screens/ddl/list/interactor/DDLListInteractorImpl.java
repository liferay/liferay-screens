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

package com.liferay.mobile.screens.ddl.list.interactor;

import android.util.Pair;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.service.MobilewidgetsddlrecordService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListInteractorImpl
	extends BaseListInteractor<DDLEntry,DDLListRowsListener> implements DDLListInteractor {

    public DDLListInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

    public void loadRows(
			long recordSetId, long userId, int startRow, int endRow, Locale locale)
		throws Exception {

        _recordSetId = recordSetId;
        _userId = userId;

        loadRows(startRow, endRow, locale);
	}

    @Override
    protected BaseListCallback<DDLEntry> getCallback(Pair<Integer, Integer> rowsRange) {
        return new DDLListCallback(getTargetScreenletId(), rowsRange);
    }

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {
		JSONObject entryQueryParams = addQueryParams(_recordSetId, _userId);

		entryQueryParams.put("start", startRow);
		entryQueryParams.put("end", endRow);

		new MobilewidgetsddlrecordService(session).getDDLEntries(entryQueryParams, locale.toString());
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {
		JSONObject entryQueryParams = addQueryParams(_recordSetId, _userId);

		new MobilewidgetsddlrecordService(session).getEntriesCount(entryQueryParams);
	}

    protected JSONObject addQueryParams(long recordSetId, long userId) throws JSONException {
        JSONObject entryQueryParams = new JSONObject();

        entryQueryParams.put("ddlRecordSetId", recordSetId);

        if (userId != 0) {
            entryQueryParams.put("userId", _userId);
        }

        return entryQueryParams;
    }

	protected void validate(
		long recordSetId, int startRow, int endRow, Locale locale) {
        super.validate(startRow, endRow, locale);

		if (recordSetId <= 0) {
			throw new IllegalArgumentException(
				"ddlRecordSetId cannot be 0 or negative");
		}
	}

    private long _recordSetId;
    private long _userId;


}