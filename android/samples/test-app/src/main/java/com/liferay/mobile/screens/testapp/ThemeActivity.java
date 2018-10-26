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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class ThemeActivity extends AppCompatActivity {

    private final int[] themes =
        { R.style.lexicon_theme, R.style.default_theme, R.style.material_theme, R.style.westeros_theme };
    private final int[] colors = {
        R.color.colorPrimary_lexicon, R.color.colorPrimary_default, R.color.colorPrimary_material,
        R.color.colorPrimary_westeros
    };
    private Integer currentThemePosition;
    private View content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentThemePosition = getIntent().getIntExtra("currentThemePosition", 0);
        setTheme(getCurrentTheme());
    }

    @Override
    protected void onResume() {
        super.onResume();

        content = findViewById(android.R.id.content);
    }

    protected void changeToNextTheme() {
        currentThemePosition = (currentThemePosition + 1) % themes.length;
    }

    protected void error(String message, Exception e) {
        showSnackbarWithColor(message, ContextCompat.getColor(this, R.color.red_default));
        LiferayLogger.e("Error ", e);
    }

    protected void info(String message) {
        int color = colors[currentThemePosition];
        showSnackbarWithColor(message, ContextCompat.getColor(this, color));
    }

    protected Intent getIntentWithTheme(Class destinationClass) {
        Intent intent = new Intent(this, destinationClass);
        intent.putExtra("currentThemePosition", currentThemePosition);
        return intent;
    }

    private int getCurrentTheme() {
        return themes[currentThemePosition];
    }

    private void showSnackbarWithColor(String message, int color) {
        Snackbar snackbar = Snackbar.make(content, message, Snackbar.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(color);
        snackbar.show();
    }
}
