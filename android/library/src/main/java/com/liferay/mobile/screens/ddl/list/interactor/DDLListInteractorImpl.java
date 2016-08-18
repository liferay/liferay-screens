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

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListInteractorImpl extends BaseListInteractor<Record, DDLListInteractorListener> {

	protected void validate(long recordSetId, int startRow, int endRow, Locale locale) {
		super.validate(startRow, endRow, locale);

		if (recordSetId <= 0) {
			throw new IllegalArgumentException("ddlRecordSetId cannot be 0 or negative");
		}
	}

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
		long _recordSetId = (long) args[0];
		long _userId = (long) args[1];
		ScreensDDLRecordConnector ddlRecordService =
			ServiceProvider.getInstance().getScreensDDLRecordConnector(getSession());
		int startRow = query.getStartRow();
		int endRow = query.getEndRow();
		JSONObjectWrapper obc = query.getObjC();
		if (_userId != 0) {
			return ddlRecordService.getDdlRecords(_recordSetId, _userId, locale.toString(), startRow, endRow, obc);
		} else {
			return ddlRecordService.getDdlRecords(_recordSetId, locale.toString(), startRow, endRow, obc);
		}
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {

		long _recordSetId = (long) args[0];
		long _userId = (long) args[1];

		ScreensDDLRecordConnector ddlRecordService =
			ServiceProvider.getInstance().getScreensDDLRecordConnector(getSession());

		if (_userId != 0) {
			return ddlRecordService.getDdlRecordsCount(_recordSetId, _userId);
		} else {
			return ddlRecordService.getDdlRecordsCount(_recordSetId);
		}
	}

	@Override
	protected Record createEntity(Map<String, Object> stringObjectMap) {
		return new Record(stringObjectMap, locale);
	}

	@Override
	protected String getIdFromArgs(Object... args) throws Exception {
		return null;
	}
}