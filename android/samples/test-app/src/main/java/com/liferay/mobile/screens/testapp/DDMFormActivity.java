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
import android.support.annotation.Nullable;
import android.view.View;
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;

/**
 * @author Paulo Cruz
 */
public class DDMFormActivity extends ThemeActivity implements DDMFormListener {

	public static final String FORM_INSTANCE_ID_KEY = "FORM_INSTANCE_ID";
	private DDMFormScreenlet screenlet;
	private ModalProgressBarWithLabel modalProgress;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ddm_form);

		screenlet = findViewById(R.id.ddm_form_screenlet);
		modalProgress = findViewById(R.id.liferay_modal_progress);

		screenlet.setListener(this);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void initScreenletFromIntent(Intent intent) {
		if (intent.hasExtra(FORM_INSTANCE_ID_KEY)) {
			screenlet.setFormInstanceId(intent.getLongExtra(FORM_INSTANCE_ID_KEY, 0));
		}
	}

	private void loadResource() {
		screenlet.setVisibility(View.GONE);
		modalProgress.show("Loading Form");
		screenlet.load();
	}

	@Override
	public void onFormLoaded(FormInstance formInstance) {
		modalProgress.hide();
		screenlet.setVisibility(View.VISIBLE);
		info(getString(R.string.form_loaded_info));
	}

	@Override
	public void onError(Exception exception) {
		info(getString(R.string.loading_form_error));
	}
}