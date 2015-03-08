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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * @author Javier Gamarra
 */
public class SignUpActivity extends ThemeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signup);

		findViewById(R.id.signup_default).setVisibility(isDefaultTheme() ? View.VISIBLE : View.GONE);
		findViewById(R.id.signup_material).setVisibility(isDefaultTheme() ? View.GONE : View
				.VISIBLE);
	}
}
