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

import android.support.annotation.NonNull;
import android.util.Pair;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.service.v62.ScreensddlrecordService;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static com.liferay.mobile.screens.cache.DefaultCachedType.DDL_LIST;
import static com.liferay.mobile.screens.cache.DefaultCachedType.DDL_LIST_COUNT;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListInteractorImpl
	extends BaseListInteractor<Record, DDLListInteractorListener> implements DDLListInteractor {

	public DDLListInteractorImpl(int targetScreenletId, CachePolicy cachePolicy) {
		super(targetScreenletId, cachePolicy);
	}

	public void loadRows(
		long recordSetId, long userId, int startRow, int endRow, Locale locale)
		throws Exception {

		_recordSetId = recordSetId;
		_userId = userId;

		processWithCache(startRow, endRow, locale);
	}

	@Override
	protected boolean cached(Object[] args) throws Exception {

		int startRow = (int) args[0];
		int endRow = (int) args[1];
		Locale locale = (Locale) args[2];

		String id = String.valueOf(_recordSetId);

		return recoverRows(id, DDL_LIST, DDL_LIST_COUNT, null, _userId, locale, startRow, endRow);
	}

	@NonNull
	@Override
	protected Record getElement(TableCache tableCache) throws JSONException {
		return new Record(JSONUtil.toMap(new JSONObject(tableCache.getContent())));
	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {

		String id = String.valueOf(_recordSetId);

		storeRows(id, DDL_LIST, DDL_LIST_COUNT, null, _userId, event);
	}

	@Override
	protected String getContent(Record record) {
		return new JSONObject(record.getModelValues()).toString();
	}

	@Override
	protected BaseListCallback<Record> getCallback(Pair<Integer, Integer> rowsRange, Locale locale) {
		return new DDLListCallback(getTargetScreenletId(), rowsRange, locale);
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {
		ScreensddlrecordService ddlRecordService = new ScreensddlrecordService(session);
		if (_userId != 0) {
			ddlRecordService.getDdlRecords(_recordSetId, _userId, locale.toString(), startRow, endRow);
		}
		else {
			ddlRecordService.getDdlRecords(_recordSetId, locale.toString(), startRow, endRow);
		}
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {
		ScreensddlrecordService ddlRecordService = new ScreensddlrecordService(session);
		if (_userId != 0) {
			ddlRecordService.getDdlRecordsCount(_recordSetId, _userId);
		}
		else {
			ddlRecordService.getDdlRecordsCount(_recordSetId);
		}
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