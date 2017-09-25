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

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class WebContentDisplayCustomCssActivity extends ThemeActivity implements WebContentDisplayListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_content_display_customcss);

		WebContentDisplayScreenlet screenlet =
			(WebContentDisplayScreenlet) findViewById(R.id.web_content_display_screenlet);
		screenlet.setListener(this);
	}

	@Override
	public WebContent onWebContentReceived(WebContent html) {
		info(getString(R.string.webcontent_received_info));
		return null;
	}

	@Override
	public boolean onUrlClicked(String url) {
		info(getString(R.string.webcontent_clicked_info) + " -> " + url);
		return false;
	}

	@Override
	public boolean onWebContentTouched(View view, MotionEvent event) {
		return false;
	}

	@Override
	public void error(Exception e, String userAction) {
		error(getString(R.string.webcontent_error), e);
	}
}
