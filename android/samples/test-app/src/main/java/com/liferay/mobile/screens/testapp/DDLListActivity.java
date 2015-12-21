/*
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

package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class DDLListActivity extends ThemeActivity implements BaseListListener<Record> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ddl_list);

		_screenlet = (DDLListScreenlet) findViewById(R.id.ddl_list_screenlet);
		_screenlet.setListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		_screenlet.loadPage(0);
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {
		error("Page request failed", e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<Record> entries, int rowCount) {
//		info("Page " + page + " received!");
	}

	@Override
	public void onListItemSelected(Record element, View view) {
//		info("Item selected: " + element);
		loadDDLForm(element);
	}

	@Override
	public void loadingFromCache(boolean success) {
		info("Loading from cache: " + success);
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
//		info("Retrieving online... and tried in cache: " + triedInCache);
	}

	@Override
	public void storingToCache(Object object) {
		info("Storing to cache...");
	}

	private void loadDDLForm(Record element) {
		final long recordId = element.getRecordId();
		final long recordSetId = element.getRecordSetId();

		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(getCallback(recordId, recordSetId));

			new DDLRecordSetService(session).getRecordSet(recordSetId);
		}
		catch (Exception e) {
			error("error loading structure id", e);
		}
	}

	private JSONObjectCallback getCallback(final long recordId, final long recordSetId) {
		return new JSONObjectCallback() {

			@Override
			public void onSuccess(JSONObject result) {
				try {
					Intent intent = getIntentWithTheme(DDLFormActivity.class);
					intent.putExtra("recordId", recordId);
					intent.putExtra("recordSetId", recordSetId);
					intent.putExtra("structureId", result.getLong("DDMStructureId"));

					DefaultAnimation.startActivityWithAnimation(DDLListActivity.this, intent);
				}
				catch (JSONException e) {
					error("error parsing JSON", e);
				}
			}

			@Override
			public void onFailure(Exception e) {
				error("error loading structure id", e);
			}
		};
	}

	private DDLListScreenlet _screenlet;
}
