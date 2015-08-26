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
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.service.v62.ScreensddlrecordService;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListInteractorImpl
	extends BaseListInteractor<DDLEntry, DDLListInteractorListener> implements DDLListInteractor {

	public DDLListInteractorImpl(int targetScreenletId, CachePolicy cachePolicy) {
		super(targetScreenletId, cachePolicy);
	}

	public void loadRows(
		final long recordSetId, long userId, final int startRow, final int endRow, final Locale locale)
		throws Exception {

		_recordSetId = recordSetId;
		_userId = userId;

		final RequestState requestState = RequestState.getInstance();
		final Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);
		// check if this page is already being loaded
		if (requestState.contains(getTargetScreenletId(), rowsRange)) {
			return;
		}

		loadWithCache(recordSetId, userId, startRow, endRow, locale, requestState, rowsRange);
	}

	@Override
	public void onEventMainThread(BaseListEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event);

		List entries = event.getEntries();
		int rowCount = event.getRowCount();

		getListener().onListRowsReceived(
			event.getStartRow(), event.getEndRow(), entries, rowCount);
	}

	@Override
	protected void loadOnline(Object[] args) throws Exception {

		int startRow = (int) args[2];
		int endRow = (int) args[3];
		Locale locale = (Locale) args[4];

		loadRows(startRow, endRow, locale);
	}

	@Override
	protected void notifyError(BaseListEvent event) {
		getListener().onListRowsFailure(event.getStartRow(), event.getEndRow(), event.getException());
	}

	@Override
	protected boolean getFromCache(Object[] args) throws Exception {

		long recordSetId = (long) args[0];
		long userId = (long) args[1];
		int startRow = (int) args[2];
		int endRow = (int) args[3];
		Locale locale = (Locale) args[4];
		RequestState requestState = (RequestState) args[5];
		Pair rowsRange = (Pair) args[6];

		Cache cache = CacheSQL.getInstance();
		String query = " AND " + TableCache.ID + " >= ? AND " + TableCache.ID + " < ? AND " + TableCache.USER_ID + " = ? AND " + TableCache.LOCALE + " = ? ";
		List<TableCache> ddlEntryCache = (List<TableCache>) cache.get(DefaultCachedType.DDL_LIST,
			query, createId(String.valueOf(recordSetId), startRow), createId(String.valueOf(recordSetId), endRow), userId, locale);

		if (ddlEntryCache != null && !ddlEntryCache.isEmpty()) {


			requestState.put(getTargetScreenletId(), rowsRange);

			List<DDLEntry> entries = new ArrayList<>();

			for (TableCache tableCache : ddlEntryCache) {
				entries.add(new DDLEntry(JSONUtil.toMap(new JSONObject(tableCache.getContent()))));
			}

			TableCache tableCache = (TableCache) cache.getById(DefaultCachedType.DDL_LIST_COUNT, String.valueOf(recordSetId), LiferayServerContext.getGroupId(), _userId, locale);

			RequestState.getInstance().remove(getTargetScreenletId(), rowsRange);

			EventBusUtil.post(new BaseListEvent(
				getTargetScreenletId(), startRow, endRow, entries, Integer.valueOf(tableCache.getContent()), locale));

			return true;
		}
		return false;


	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {
		Cache cache = CacheSQL.getInstance();
		cache.set(new TableCache(String.valueOf(_recordSetId), DefaultCachedType.DDL_LIST_COUNT,
			String.valueOf(event.getRowCount()), LiferayServerContext.getGroupId(), _userId, event.getLocale()));

		for (int i = 0; i < event.getEntries().size(); i++) {
			DDLEntry ddlEntry = (DDLEntry) event.getEntries().get(i);

			int range = i + event.getStartRow();

			Map<String, Object> values = ddlEntry.getValues();
			Object recordSetId = ddlEntry.getAttributes("recordSetId");
			cache.set(new TableCache(createId(recordSetId.toString(), range), DefaultCachedType.DDL_LIST,
				new JSONObject(values).toString(), LiferayServerContext.getGroupId(), _userId, event.getLocale()));
		}
	}

	@Override
	protected BaseListCallback<DDLEntry> getCallback(Pair<Integer, Integer> rowsRange, Locale locale) {
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