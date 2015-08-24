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

package com.liferay.mobile.screens.assetlist.interactor;

import android.util.Pair;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.assetentry.AssetEntryService;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.CacheCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.service.v62.ScreensassetentryService;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class AssetListInteractorImpl
	extends BaseListInteractor<AssetEntry, AssetListInteractorListener> implements AssetListInteractor {


	public AssetListInteractorImpl(int targetScreenletId, CachePolicy cachePolicy) {
		super(targetScreenletId, cachePolicy);
	}

	public void loadRows(
		long groupId, final long classNameId, final int startRow, final int endRow, final Locale locale)
		throws Exception {
		this._groupId = groupId;
		this._classNameId = classNameId;


		final RequestState requestState = RequestState.getInstance();
		final Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);
		// check if this page is already being loaded
		if (requestState.contains(getTargetScreenletId(), rowsRange)) {
			return;
		}

		loadWithCache(new CacheCallback() {
			@Override
			public void loadOnline() throws Exception {
				loadRows(startRow, endRow, locale);
			}

			@Override
			public boolean retrieveFromCache() throws JSONException {
				return loadFromCache(classNameId, startRow, endRow, requestState, rowsRange);
			}
		});
	}

	@Override
	public void onEventMainThread(BaseListEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onListRowsFailure(
				event.getStartRow(), event.getEndRow(), event.getException());
		}
		else {

			storeToCache(event);

			List entries = event.getEntries();
			int rowCount = event.getRowCount();

			getListener().onListRowsReceived(
				event.getStartRow(), event.getEndRow(), entries, rowCount);
		}
	}

	@Override
	protected BaseListCallback<AssetEntry> getCallback(Pair<Integer, Integer> rowsRange) {
		return new AssetListCallback(getTargetScreenletId(), rowsRange);
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {
		JSONObject entryQueryAttributes = addQueryParams(_groupId, _classNameId);
		entryQueryAttributes.put("start", startRow);
		entryQueryAttributes.put("end", endRow);

		JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryAttributes);

		ScreensassetentryService service = new ScreensassetentryService(session);
		service.getAssetEntries(entryQuery, locale.toString());
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {
		JSONObject entryQueryParams = addQueryParams(_groupId, _classNameId);

		JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryParams);

		new AssetEntryService(session).getEntriesCount(entryQuery);
	}

	protected JSONObject addQueryParams(long groupId, long classNameId) throws JSONException {
		JSONObject entryQueryParams = new JSONObject();

		entryQueryParams.put("classNameIds", classNameId);
		entryQueryParams.put("groupIds", groupId);
		entryQueryParams.put("visible", "true");

		return entryQueryParams;
	}

	@Override
	protected void validate(int startRow, int endRow, Locale locale) {

		if (_groupId <= 0) {
			throw new IllegalArgumentException(
				"GroupId cannot be 0 or negative");
		}

		if (_classNameId <= 0) {
			throw new IllegalArgumentException(
				"ClassNameId cannot be 0 or negative");
		}

		super.validate(startRow, endRow, locale);
	}

	private boolean loadFromCache(long classNameId, int startRow, int endRow, RequestState requestState, Pair<Integer, Integer> rowsRange) throws JSONException {
		Cache cache = CacheSQL.getInstance();
		String query = " AND " + TableCache.ID + " >= ? AND " + TableCache.ID + " < ?";
		List<TableCache> ddlEntryCache = (List<TableCache>) cache.get(DefaultCachedType.DDL_LIST,
			query, createId(String.valueOf(classNameId), startRow), createId(String.valueOf(classNameId), endRow));

		if (ddlEntryCache != null && !ddlEntryCache.isEmpty()) {


			requestState.put(getTargetScreenletId(), rowsRange);

			List<AssetEntry> entries = new ArrayList<>();

			for (TableCache tableCache : ddlEntryCache) {
				entries.add(new AssetEntry(JSONUtil.toMap(new JSONObject(tableCache.getContent()))));
			}

			TableCache tableCache = (TableCache) cache.getById(DefaultCachedType.DDL_LIST_COUNT, String.valueOf(classNameId));

			RequestState.getInstance().remove(getTargetScreenletId(), rowsRange);

			EventBusUtil.post(new BaseListEvent(
				getTargetScreenletId(), startRow, endRow, entries, Integer.valueOf(tableCache.getContent())));

			return true;
		}
		return false;
	}

	private void storeToCache(BaseListEvent event) {
		Cache cache = CacheSQL.getInstance();
		cache.set(new TableCache(String.valueOf(_classNameId), DefaultCachedType.DDL_LIST_COUNT, String.valueOf(event.getRowCount())));

		for (int i = 0; i < event.getEntries().size(); i++) {
			AssetEntry ddlEntry = (AssetEntry) event.getEntries().get(i);

			int range = i + event.getStartRow();

			Map<String, Object> values = ddlEntry.getValues();
			cache.set(new TableCache(createId(String.valueOf(_classNameId), range), DefaultCachedType.DDL_LIST, new JSONObject(values).toString()));
		}
	}

	private long _groupId;
	private long _classNameId;

}