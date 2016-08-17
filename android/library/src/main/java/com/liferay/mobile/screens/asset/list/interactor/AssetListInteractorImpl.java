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

package com.liferay.mobile.screens.asset.list.interactor;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.asset.list.connector.AssetEntryConnector;
import com.liferay.mobile.screens.asset.list.connector.ScreensAssetEntryConnector;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class AssetListInteractorImpl extends BaseListInteractor<AssetEntry, AssetListInteractorListener> {

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {

		long _classNameId = (long) args[0];
		String _portletItemName = (String) args[1];
		String _customEntryQuery = (String) args[2];

		Session session = SessionContext.createSessionFromCurrentSession();

		if (_portletItemName == null) {

			ScreensAssetEntryConnector connector = ServiceProvider.getInstance().getScreensAssetEntryConnector(session);
			JSONObject entryQueryAttributes = configureEntryQuery(groupId, _classNameId, _customEntryQuery);
			entryQueryAttributes.put("start", query.getStartRow());
			entryQueryAttributes.put("end", query.getEndRow());

			JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryAttributes);

			return connector.getAssetEntries(entryQuery, locale.toString());
		} else {
			ScreensAssetEntryConnector connector = ServiceProvider.getInstance().getScreensAssetEntryConnector(session);
			return connector.getAssetEntries(LiferayServerContext.getCompanyId(), groupId, _portletItemName,
				locale.toString(), query.getEndRow());
		}
	}

	@Override
	protected Integer getPageRowCountRequest(Query query, Object... args) throws Exception {

		long _classNameId = (long) args[0];
		String _portletItemName = (String) args[1];
		String _customEntryQuery = (String) args[2];

		Session session = SessionContext.createSessionFromCurrentSession();

		JSONObject entryQueryParams = configureEntryQuery(groupId, _classNameId, _customEntryQuery);
		JSONObjectWrapper entryQuery = new JSONObjectWrapper(entryQueryParams);
		AssetEntryConnector connector = ServiceProvider.getInstance().getAssetEntryConnector(session);
		return connector.getEntriesCount(entryQuery);
	}

	@Override
	protected AssetEntry createEntity(Map<String, Object> stringObjectMap) {
		return new AssetEntry(stringObjectMap);
	}

	@Override
	protected BaseListEvent<AssetEntry> createEventFromArgs(Object... args) throws Exception {
		return null;
	}

	protected JSONObject configureEntryQuery(long groupId, long classNameId, String _customEntryQuery)
		throws JSONException {

		JSONObject entryQueryParams = _customEntryQuery == null ? new JSONObject() : new JSONObject(_customEntryQuery);

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

	//@Override
	//protected void validate(int startRow, int endRow, Locale locale) {
	//
	//	if (_groupId <= 0) {
	//		throw new IllegalArgumentException("GroupId cannot be 0 or negative");
	//	} else if (_portletItemName == null && _classNameId <= 0) {
	//		throw new IllegalArgumentException("ClassNameId cannot be 0 or negative");
	//	}
	//
	//	super.validate(startRow, endRow, locale);
	//}
}