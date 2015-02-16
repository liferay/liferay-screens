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

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.assetentry.AssetEntryService;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.list.BaseListInteractor;
import com.liferay.mobile.screens.service.MobilewidgetsassetentryService;
import com.liferay.mobile.screens.util.SessionContext;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class AssetListInteractorImpl
	extends BaseListInteractor<AssetListRowsListener> implements AssetListInteractor {

	public AssetListInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void loadRows(
			long groupId, long classNameId, int startRow, int endRow, Locale locale)
		throws Exception {

		validate(groupId, classNameId, startRow, endRow, locale);

		Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);

		RequestState requestState = RequestState.getInstance();

		// check if this page is already being loaded
		if (requestState.contains(getTargetScreenletId(), rowsRange)) {
			return;
		}

		Session session = SessionContext.createSessionFromCurrentSession();
		BatchSessionImpl batchSession = new BatchSessionImpl(session);
		batchSession.setCallback(
			new AssetListCallback(getTargetScreenletId(), rowsRange));

		sendGetPageRowsRequest(
			batchSession, groupId, classNameId, startRow, endRow, locale);

		sendGetEntriesCountRequest(batchSession, groupId, classNameId);

		batchSession.invoke();

		requestState.put(getTargetScreenletId(), rowsRange);
	}

	protected JSONObject configureEntryQueryAttributes(
			long groupId, long classNameId)
		throws JSONException {

		JSONObject entryQueryAttributes = new JSONObject();

		entryQueryAttributes.put("classNameIds", classNameId);
		entryQueryAttributes.put("groupIds", groupId);
		entryQueryAttributes.put("visible", "true");

		return entryQueryAttributes;
	}

	protected AssetEntryService getAssetEntryService(Session session) {
		return new AssetEntryService(session);
	}

	protected void sendGetEntriesCountRequest(
			Session session, long groupId, long classNameId)
		throws Exception {

		JSONObject entryQueryAttributes = configureEntryQueryAttributes(
			groupId, classNameId);

		JSONObjectWrapper entryQuery = new JSONObjectWrapper(
			entryQueryAttributes);

		AssetEntryService service = getAssetEntryService(session);
		service.getEntriesCount(entryQuery);
	}

	protected void sendGetPageRowsRequest(
			Session session, long groupId, long classNameId, int startRow, int endRow,
			Locale locale)
		throws Exception {

		JSONObject entryQueryAttributes = configureEntryQueryAttributes(
			groupId, classNameId);

		entryQueryAttributes.put("start", startRow);
		entryQueryAttributes.put("end", endRow);

		JSONObjectWrapper entryQuery = new JSONObjectWrapper(
			entryQueryAttributes);

		MobilewidgetsassetentryService service =
			new MobilewidgetsassetentryService(session);

		service.getAssetEntries(entryQuery, locale.toString());
	}

	protected void validate(
		long groupId, long classNameId, int startRow, int endRow, Locale locale) {

		if (groupId <= 0) {
			throw new IllegalArgumentException(
				"GroupId cannot be 0 or negative");
		}

		if (classNameId <= 0) {
			throw new IllegalArgumentException(
				"ClassNameId cannot be 0 or negative");
		}

		super.validate(startRow,endRow,locale);
	}

}