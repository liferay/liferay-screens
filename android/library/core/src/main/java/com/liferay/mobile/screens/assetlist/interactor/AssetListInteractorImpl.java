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

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.assetentry.AssetEntryService;
import com.liferay.mobile.screens.assetlist.AssetListListener;
import com.liferay.mobile.screens.assetlist.AssetListScreenletEntry;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.service.MobilewidgetsassetentryService;
import com.liferay.mobile.screens.util.SessionContext;

import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class AssetListInteractorImpl
	extends BaseInteractor<AssetListListener> implements AssetListInteractor {

	public AssetListInteractorImpl(
		int targetScreenletId, int firstPageSize, int pageSize) {

		super(targetScreenletId);

		_firstPageSize = (firstPageSize != 0) ? firstPageSize : pageSize;
		_pageSize = pageSize;
	}

	public void loadPage(
			long groupId, long classNameId, int page, Locale locale)
		throws Exception {

		RequestState requestState = RequestState.getInstance();

		if (requestState.contains(getTargetScreenletId(), page)) {
			return;
		}

		Session session = SessionContext.createSessionFromCurrentSession();
		BatchSessionImpl batchSession = new BatchSessionImpl(session);
		batchSession.setCallback(
			new AssetListCallback(getTargetScreenletId(), page));

		sendGetPageRowsRequest(
			batchSession, groupId, classNameId, page, locale);

		sendGetEntriesCountRequest(batchSession, groupId, classNameId);

		batchSession.invoke();

		requestState.put(getTargetScreenletId(), page);
	}

	@Override
	public void loadPageForRow(
			long groupId, long classNameId, int row, Locale locale)
		throws Exception {

		int page = getPageFromRow(row);

		loadPage(groupId, classNameId, page, locale);
	}

	public void onEvent(AssetListEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onAssetListLoadFailure(event.getException());
		}
		else {
			int firstRowForPage = getFirstRowForPage(event.getPage());
			List<AssetListScreenletEntry> entries = event.getEntries();
			int rowCount = event.getRowCount();

			getListener().onAssetListPageReceived(
				firstRowForPage, entries, rowCount);
		}
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

	protected int getFirstRowForPage(int page) {
		if (page == 0) {
			return 0;
		}

		return (_firstPageSize + (page - 1) * _pageSize);
	}

	protected MobilewidgetsassetentryService getMWAssetEntryService(
		Session session) {

		return new MobilewidgetsassetentryService(session);
	}

	protected int getPageFromRow(int row) {
		if (row < _firstPageSize) {
			return 0;
		}

		return ((row - _firstPageSize) / _pageSize) + 1;
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
			Session session, long groupId, long classNameId, int page,
			Locale locale)
		throws Exception {

		JSONObject entryQueryAttributes = configureEntryQueryAttributes(
			groupId, classNameId);

		entryQueryAttributes.put("start", getFirstRowForPage(page));
		entryQueryAttributes.put("end", getFirstRowForPage(page + 1));

		JSONObjectWrapper entryQuery = new JSONObjectWrapper(
			entryQueryAttributes);

		MobilewidgetsassetentryService service = getMWAssetEntryService(
			session);

		service.getAssetEntries(entryQuery, locale.toString());
	}

	private int _firstPageSize = 50;
	private int _pageSize = 25;

}