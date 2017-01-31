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
import android.webkit.WebView;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;

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

		if (getIntent().hasExtra("articleId")) {
			screenlet.setArticleId(getIntent().getStringExtra("articleId"));
		}
	}

	@Override
	public WebContent onWebContentReceived(WebContent html) {
		info(getString(R.string.webcontent_received_info));
		return null;
	}

	@Override
	public boolean onWebContentClicked(WebView.HitTestResult result, MotionEvent event) {
		info(getString(R.string.webcontent_clicked_info) + " -> " + result.getExtra());
		return true;
	}

	@Override
	public void error(Exception e, String userAction) {
		error(getString(R.string.webcontent_error), e);
	}
}
