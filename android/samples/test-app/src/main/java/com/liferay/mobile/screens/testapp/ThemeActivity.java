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
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Javier Gamarra
 */
public abstract class ThemeActivity extends AppCompatActivity {

	@Override
	protected void onResume() {
		super.onResume();

		_currentThemePosition = getIntent().getIntExtra("currentThemePosition", 0);
		setTheme(getCurrentTheme());
		_content = findViewById(android.R.id.content);
	}

	protected void changeToNextTheme() {
		_currentThemePosition = (_currentThemePosition + 1) % themes.length;
	}

	protected void error(String message, Exception e) {
		showSnackbarWithColor(message, ContextCompat.getColor(this, R.color.default_pure_red));
	}

	protected void info(String message) {
		int color = colors[_currentThemePosition];
		showSnackbarWithColor(message, ContextCompat.getColor(this, color));
	}

	protected Intent getIntentWithTheme(Class destinationClass) {
		Intent intent = new Intent(this, destinationClass);
		intent.putExtra("currentThemePosition", _currentThemePosition);
		return intent;
	}

	private int getCurrentTheme() {
		return themes[_currentThemePosition];
	}

	private void showSnackbarWithColor(String message, int color) {
		Snackbar snackbar = Snackbar.make(_content, message, Snackbar.LENGTH_SHORT);
		ViewGroup group = (ViewGroup) snackbar.getView();
		group.setBackgroundColor(color);
		snackbar.show();
	}

	int[] themes = {R.style.default_theme, R.style.material_theme, R.style.westeros_theme};
	int[] colors = {R.color.colorPrimary_default, R.color.colorPrimary_material, R.color.colorPrimary_westeros};
	private Integer _currentThemePosition;
	private View _content;

}
