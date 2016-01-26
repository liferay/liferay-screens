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
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;

import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;

/**
 * @author Javier Gamarra
 */
public abstract class ThemeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		currentTheme = getIntent().getIntExtra("theme", DefaultTheme.getDefaultTheme());

		setTheme(currentTheme);
	}

	@Override
	protected void onResume() {
		super.onResume();

		_content = findViewById(android.R.id.content);
	}

	protected void error(String message, Exception e) {
		showSnackbarWithColor(message, ContextCompat.getColor(this, R.color.default_pure_red));
	}

	protected void info(String message) {
		int color = isDefaultTheme() ? R.color.default_primary_blue : R.color.material_primary;

		showSnackbarWithColor(message, ContextCompat.getColor(this, color));
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

	private void showSnackbarWithColor(String message, int color) {
		Snackbar snackbar = Snackbar.make(_content, message, Snackbar.LENGTH_SHORT);
		ViewGroup group = (ViewGroup) snackbar.getView();
		group.setBackgroundColor(color);
		snackbar.show();
	}

	protected Integer currentTheme;
	private View _content;
}
