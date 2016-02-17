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

import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayScreenlet;

/**
 * @author Javier Gamarra
 */
public class WebContentDisplayActivity extends ThemeActivity implements WebContentDisplayListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.web_content_display);

		WebContentDisplayScreenlet screenlet =
			(WebContentDisplayScreenlet) findViewById(R.id.web_content_display_screenlet);
		screenlet.setListener(this);

		screenlet.load();
	}

	@Override
	public String onWebContentReceived(WebContentDisplayScreenlet source, String html) {
		info("Web Content received!");
		return null;
	}

	@Override
	public void onWebContentFailure(WebContentDisplayScreenlet source, Exception e) {
		error("Could not receive web content information", e);
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
}
