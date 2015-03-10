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

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.screens.userportrait.UserPortraitListener;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Javier Gamarra
 */
public class UserPortraitActivity extends ThemeActivity implements UserPortraitListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.user_portrait);

		_screenlet = (UserPortraitScreenlet) getActiveScreenlet(
			R.id.user_portrait_default, R.id.user_portrait_material);

		_screenlet.setVisibility(View.VISIBLE);
		_screenlet.setListener(this);

		hideInactiveScreenlet(R.id.user_portrait_default, R.id.user_portrait_material);
	}

	@Override
	protected void onResume() {
		super.onResume();

		_screenlet.load();
	}

	@Override
	public Bitmap onUserPortraitReceived(UserPortraitScreenlet source, Bitmap bitmap) {
		info("User portrait received!");

		return null;
	}

	@Override
	public void onUserPortraitFailure(UserPortraitScreenlet source, Exception e) {
		error("Could not load user portrait", e);
	}

	private UserPortraitScreenlet _screenlet;
}
