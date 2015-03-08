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
import android.view.View;

import com.liferay.mobile.screens.auth.signup.SignUpListener;
import com.liferay.mobile.screens.auth.signup.SignUpScreenlet;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 */
public class SignUpActivity extends ThemeActivity implements SignUpListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.signup);

		SignUpScreenlet screenlet = (SignUpScreenlet) getActiveScreenlet(R.id.signup_default, R.id.signup_material);

		screenlet.setVisibility(View.VISIBLE);
		screenlet.setListener(this);

		hideInactiveScreenlet(R.id.signup_default, R.id.signup_material);
	}

	@Override
	public void onSignUpFailure(Exception e) {

	}

	@Override
	public void onSignUpSuccess(User user) {

	}
}
