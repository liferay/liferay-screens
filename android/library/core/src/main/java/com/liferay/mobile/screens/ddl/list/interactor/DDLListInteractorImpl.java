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

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.base.list.ListEvent;
import com.liferay.mobile.screens.base.list.ListRowsListener;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.service.MobilewidgetsddlrecordService;
import com.liferay.mobile.screens.util.SessionContext;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListInteractorImpl
	extends BaseInteractor<ListRowsListener<DDLEntry>> implements DDLListInteractor {

	public DDLListInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void loadRows(
			long recordSetId, int startRow, int endRow, Locale locale)
		throws Exception {

		validate(recordSetId, startRow, endRow, locale);

		Pair<Integer, Integer> rowsRange = new Pair<>(startRow, endRow);

		RequestState requestState = RequestState.getInstance();

		// check if this page is already being loaded
		if (requestState.contains(getTargetScreenletId(), rowsRange)) {
			return;
		}

		Session session = SessionContext.createSessionFromCurrentSession();
		BatchSessionImpl batchSession = new BatchSessionImpl(session);
		batchSession.setCallback(
			new DDLListCallback(getTargetScreenletId(), rowsRange));

		sendGetPageRowsRequest(
			batchSession, recordSetId, startRow, endRow, locale);

		sendGetEntriesCountRequest(batchSession, recordSetId);

		batchSession.invoke();

		requestState.put(getTargetScreenletId(), rowsRange);
	}

	public void onEvent(ListEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onListRowsFailure(
                    event.getStartRow(), event.getEndRow(), event.getException());
		}
		else {
			List<DDLEntry> entries = event.getEntries();
			int rowCount = event.getRowCount();

			getListener().onListRowsReceived(
                    event.getStartRow(), event.getEndRow(), entries, rowCount);
		}
	}

	protected JSONObject configureEntryQueryAttributes(
			long recordSetId)
		throws JSONException {

		JSONObject entryQueryAttributes = new JSONObject();

		entryQueryAttributes.put("ddlRecordSetId", recordSetId);

		return entryQueryAttributes;
	}

	protected void sendGetEntriesCountRequest(
			Session session, long recordSetId)
		throws Exception {

		JSONObject entryQueryAttributes = configureEntryQueryAttributes(
			recordSetId);

        MobilewidgetsddlrecordService service =
                new MobilewidgetsddlrecordService(session);
		service.getEntriesCount(entryQueryAttributes);
	}

	protected void sendGetPageRowsRequest(
			Session session, long recordSetId, int startRow, int endRow,
			Locale locale)
		throws Exception {

		JSONObject entryQueryAttributes = configureEntryQueryAttributes(recordSetId
			);

		entryQueryAttributes.put("start", startRow);
		entryQueryAttributes.put("end", endRow);

		MobilewidgetsddlrecordService service =
			new MobilewidgetsddlrecordService(session);

		service.getDDLEntries(entryQueryAttributes, locale.toString());
	}

	protected void validate(
		long recordSetId, int startRow, int endRow, Locale locale) {

		if (recordSetId <= 0) {
			throw new IllegalArgumentException(
				"ddlRecordSetId cannot be 0 or negative");
		}


		if (startRow < 0) {
			throw new IllegalArgumentException("Start row cannot be negative");
		}

		if (endRow < 0) {
			throw new IllegalArgumentException("End row cannot be negative");
		}

		if (startRow >= endRow) {
			throw new IllegalArgumentException("Start row cannot be greater or equals than end row");
		}

		if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be null");
		}
	}

	private int _firstPageSize = 50;
	private int _pageSize = 25;

}