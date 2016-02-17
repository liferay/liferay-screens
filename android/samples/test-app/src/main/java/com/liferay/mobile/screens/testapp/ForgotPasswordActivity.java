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

import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;

/**
 * @author Javier Gamarra
 */
public class ForgotPasswordActivity extends ThemeActivity implements ForgotPasswordListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.forgot_password);

		ForgotPasswordScreenlet screenlet = (ForgotPasswordScreenlet) findViewById(R.id.forgot_password_screenlet);
		screenlet.setListener(this);
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {
		info("Request completed!");
	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {
		error("Request failed", e);
	}
}
