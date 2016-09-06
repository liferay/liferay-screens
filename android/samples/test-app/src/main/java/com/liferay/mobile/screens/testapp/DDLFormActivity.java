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
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLFormActivity extends ThemeActivity implements DDLFormListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ddl_form);

		screenlet = (DDLFormScreenlet) findViewById(R.id.ddl_form_screenlet);
		screenlet.setListener(this);

		initScreenletFromIntent(getIntent());
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!loaded) {
			screenlet.load();
		}
	}

	@Override
	public void onDDLFormLoaded(Record record) {
		loaded = true;
		info("Form loaded!");
	}

	@Override
	public void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes) {
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
	public void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject) {
		info("Document uploaded!");
	}

	@Override
	public void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e) {
		error("Document could not be uploaded", e);
	}

	@Override
	public void error(Exception e, String userAction) {
		error(userAction + " failed", e);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			screenlet.startUploadByPosition(requestCode);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(STATE_LOADED, loaded);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		loaded = savedInstanceState.getBoolean(STATE_LOADED);
	}

	private void initScreenletFromIntent(Intent intent) {
		if (intent.hasExtra("recordId")) {
			screenlet.setRecordId(intent.getLongExtra("recordId", 0));
			screenlet.setRecordSetId(intent.getLongExtra("recordSetId", 0));
			screenlet.setStructureId(intent.getLongExtra("structureId", 0));
		}
	}

	private DDLFormScreenlet screenlet;
	private boolean loaded;

	private static final String STATE_LOADED = "STATE_LOADED";
}
