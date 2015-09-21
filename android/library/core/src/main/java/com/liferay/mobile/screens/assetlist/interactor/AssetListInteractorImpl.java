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
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v62.ScreensassetentryService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class AssetListInteractorImpl
	extends BaseListInteractor<AssetEntry, AssetListInteractorListener> implements AssetListInteractor {

	public AssetListInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void loadRows(
		long groupId, long classNameId, String portletItemName, int startRow, int endRow, Locale locale)
		throws Exception {
		this._groupId = groupId;
		this._classNameId = classNameId;

		if (portletItemName == null) {
			loadRows(startRow, endRow, locale);
		}
		else {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(new FilteredAssetListCallback(getTargetScreenletId()));
			ScreensassetentryService service = new ScreensassetentryService(session);
			service.getFilteredAssetEntries(LiferayServerContext.getCompanyId(), groupId, portletItemName, locale.toString());
		}
	}

	@Override
	protected BaseListCallback<AssetEntry> getCallback(Pair<Integer, Integer> rowsRange) {
		return new AssetListCallback(getTargetScreenletId(), rowsRange);
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {
		ScreensassetentryService service = new ScreensassetentryService(session);
		JSONObject entryQueryAttributes = addQueryParams(_groupId, _classNameId);
		entryQueryAttributes.put("start", startRow);
		entryQueryAttributes.put("end", endRow);

		JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryAttributes);

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

	private long _groupId;
	private long _classNameId;
}