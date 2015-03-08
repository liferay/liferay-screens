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

		_screenlet = (DDLFormScreenlet) getActiveScreenlet(R.id.ddl_form_default,
				R.id.ddl_form_material);

		_screenlet.setVisibility(View.VISIBLE);
		_screenlet.setListener(this);

		hideInactiveScreenlet(R.id.ddl_form_default, R.id.ddl_form_material);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		_screenlet.startUploadByPosition(requestCode);
	}

	@Override
	public void onDDLFormLoaded(Record record) {
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

	private DDLFormScreenlet _screenlet;
}
