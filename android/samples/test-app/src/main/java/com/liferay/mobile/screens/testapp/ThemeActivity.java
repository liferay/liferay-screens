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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;

/**
 * @author Javier Gamarra
 */
public abstract class ThemeActivity extends Activity {

	protected Integer currentTheme;

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		currentTheme = getIntent().getIntExtra("theme", DefaultTheme.DEFAULT_THEME);
		setTheme(currentTheme);
	}

	protected Intent getIntentWithTheme(Class destinationClass) {
		Intent intent = new Intent(this, destinationClass);
		intent.putExtra("theme", currentTheme);
		return intent;
	}

	protected View getActiveScreenlet(int defaultId, int materialId) {
		return isDefaultTheme() ? findViewById(defaultId) : findViewById(materialId);
	}

	protected void hideInactiveScreenlet(int defaultId, int materialId) {
		View view = isDefaultTheme() ? findViewById(materialId) : findViewById(defaultId);
		view.setVisibility(View.GONE);
	}

	protected boolean isDefaultTheme() {
		return currentTheme == R.style.default_theme;
	}
}
