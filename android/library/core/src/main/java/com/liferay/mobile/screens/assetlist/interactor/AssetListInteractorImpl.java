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

import android.support.annotation.NonNull;
import android.util.Pair;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.assetlist.connector.AssetEntryConnector;
import com.liferay.mobile.screens.assetlist.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

import static com.liferay.mobile.screens.cache.DefaultCachedType.ASSET_LIST;
import static com.liferay.mobile.screens.cache.DefaultCachedType.ASSET_LIST_COUNT;

/**
 * @author Silvio Santos
 */
public class AssetListInteractorImpl
	extends BaseListInteractor<AssetEntry, AssetListInteractorListener>
	implements AssetListInteractor {

	public AssetListInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	public void loadRows(
		long groupId, long classNameId, String portletItemName,
		HashMap<String, Object> customEntryQuery, int startRow, int endRow, Locale locale)
		throws Exception {
		this._groupId = groupId;
		this._classNameId = classNameId;
		this._portletItemName = portletItemName;
		this._customEntryQuery = customEntryQuery;

		processWithCache(startRow, endRow, locale);
	}

	@Override
	protected boolean cached(Object[] args) throws Exception {

		final int startRow = (int) args[0];
		final int endRow = (int) args[1];
		final Locale locale = (Locale) args[2];

		String id = _portletItemName == null ? String.valueOf(_classNameId) : _portletItemName;

		return recoverRows(id, ASSET_LIST, ASSET_LIST_COUNT, _groupId, null, locale, startRow, endRow);
	}

	@NonNull
	@Override
	protected AssetEntry getElement(TableCache tableCache) throws JSONException {
		return new AssetEntry(JSONUtil.toMap(new JSONObject(tableCache.getContent())));
	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {

		String id = _portletItemName == null ? String.valueOf(_classNameId) : _portletItemName;

		storeRows(id, ASSET_LIST, ASSET_LIST_COUNT, _groupId, null, event);
	}

	@Override
	protected String getContent(AssetEntry assetEntry) {
		return new JSONObject(assetEntry.getValues()).toString();
	}

	@Override
	protected BaseListCallback<AssetEntry> getCallback(Pair<Integer, Integer> rowsRange, Locale locale) {
		return new AssetListCallback(getTargetScreenletId(), rowsRange, locale);
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {
		if (_portletItemName == null) {


			ScreensAssetEntryConnector service = ServiceProvider.getInstance().getScreensAssetEntryConnector(session);
			JSONObject entryQueryAttributes = configureEntryQuery(_groupId, _classNameId);
			entryQueryAttributes.put("start", startRow);
			entryQueryAttributes.put("end", endRow);

			JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryAttributes);

			service.getAssetEntries(entryQuery, locale.toString());
		}
		else {
			session.setCallback(new FilteredAssetListCallback(getTargetScreenletId()));
			ScreensAssetEntryConnector service = ServiceProvider.getInstance().getScreensAssetEntryConnector(session);
			service.getAssetEntries(LiferayServerContext.getCompanyId(), _groupId, _portletItemName, locale.toString(), endRow);
		}
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {
		JSONObject entryQueryParams = configureEntryQuery(_groupId, _classNameId);
		JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryParams);
		AssetEntryConnector service = ServiceProvider.getInstance().getAssetEntryConnector(session);
		service.getEntriesCount(entryQuery);
	}

	protected JSONObject configureEntryQuery(long groupId, long classNameId) throws JSONException {

		JSONObject entryQueryParams =
			_customEntryQuery == null ? new JSONObject() : new JSONObject(_customEntryQuery);

		if (!entryQueryParams.has("classNameIds")) {
			entryQueryParams.put("classNameIds", classNameId);
		}
		if (!entryQueryParams.has("groupIds")) {
			entryQueryParams.put("groupIds", groupId);
		}
		if (!entryQueryParams.has("visible")) {
			entryQueryParams.put("visible", "true");
		}
		return entryQueryParams;
	}

	@Override
	protected void validate(int startRow, int endRow, Locale locale) {

		if (_groupId <= 0) {
			throw new IllegalArgumentException(
				"GroupId cannot be 0 or negative");
		}

		if (_portletItemName == null && _classNameId <= 0) {
			throw new IllegalArgumentException(
				"ClassNameId cannot be 0 or negative");
		}

		super.validate(startRow, endRow, locale);
	}

	private String _portletItemName;
	private long _groupId;
	private long _classNameId;
	private HashMap<String, Object> _customEntryQuery;
}