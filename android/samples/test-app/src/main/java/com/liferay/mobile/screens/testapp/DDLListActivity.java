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

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.task.callback.typed.JSONObjectAsyncTaskCallback;
import com.liferay.mobile.android.v62.ddlrecordset.DDLRecordSetService;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultCrouton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * @author Javier Gamarra
 */
public class DDLListActivity extends ThemeActivity implements BaseListListener<DDLEntry> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ddl_list);

		DDLListScreenlet screenlet = (DDLListScreenlet) getActiveScreenlet(R.id.ddl_list_default, R.id.ddl_list_material);

		screenlet.setVisibility(View.VISIBLE);
		screenlet.setListener(this);

		hideInactiveScreenlet(R.id.ddl_list_default, R.id.ddl_list_material);
	}

	@Override
	public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {
		error("Page request failed", e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<DDLEntry> entries, int rowCount) {
		info("Page " + page + " received!");
	}

	@Override
	public void onListItemSelected(DDLEntry element) {
		info("Item selected: " + element);
		loadDDLForm(element);
	}

	private void loadDDLForm(DDLEntry element) {
		final Integer recordId = (Integer) (element.getAttributes("recordId"));
		final Integer recordSetId = (Integer) (element.getAttributes("recordSetId"));

		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(getCallback(recordId, recordSetId));

			new DDLRecordSetService(session).getRecordSet(recordSetId);
		}
		catch (Exception e) {
			error("error loading structure id", e);
		}
	}

	private JSONObjectAsyncTaskCallback getCallback(final Integer recordId, final Integer recordSetId) {
		return new JSONObjectAsyncTaskCallback() {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					Intent intent = new Intent(DDLListActivity.this, DDLFormActivity.class);
					intent.putExtra("recordId", recordId);
					intent.putExtra("recordSetId", recordSetId);
					intent.putExtra("structureId", result.getInt("DDMStructureId"));

					Crouton.clearCroutonsForActivity(DDLListActivity.this);

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
}
