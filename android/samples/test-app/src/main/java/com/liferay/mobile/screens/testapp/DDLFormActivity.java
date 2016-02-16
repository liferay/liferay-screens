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

import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormActivity extends ThemeActivity implements DDLFormListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ddl_form);

		_screenlet = (DDLFormScreenlet) findViewById(R.id.ddl_form_screenlet);
		_screenlet.setListener(this);

		initScreenletFromIntent(getIntent());
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!_loaded) {
			_screenlet.load();
		}
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		_loaded = true;
		info("Form loaded!");
	}

	@Override
	public void onDDLFormRecordLoaded(Record record) {
		info("Record loaded!");
	}

	@Override
	public void onDDLFormRecordAdded(Record record) {
		info("Record added!");
	}

	@Override
	public void onDDLFormRecordUpdated(Record record) {
		info("Record updated!");
	}

	@Override
	public void onDDLFormLoadFailed(Exception e) {
		error("Form load failed", e);
	}

	@Override
	public void onDDLFormRecordLoadFailed(Exception e) {
		error("Record load failed", e);
	}

	@Override
	public void onDDLFormRecordAddFailed(Exception e) {
		error("Add failed", e);
	}

	@Override
	public void onDDLFormUpdateRecordFailed(Exception e) {
		error("Update failed", e);
	}

	@Override
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
		info("Document uploaded!");
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
		error("Document could not be uploaded", e);
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			_screenlet.startUploadByPosition(requestCode);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(STATE_LOADED, _loaded);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		_loaded = savedInstanceState.getBoolean(STATE_LOADED);
	}

	private void initScreenletFromIntent(Intent intent) {
		if (intent.hasExtra("recordId")) {
			_screenlet.setRecordId(intent.getLongExtra("recordId", 0));
			_screenlet.setRecordSetId(intent.getLongExtra("recordSetId", 0));
			_screenlet.setStructureId(intent.getLongExtra("structureId", 0));
		}
	}

	private DDLFormScreenlet _screenlet;
	private boolean _loaded;

	private static final String STATE_LOADED = "STATE_LOADED";
}
