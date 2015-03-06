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
import android.os.Bundle;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultCrouton;
import com.liferay.mobile.screens.viewsets.material.MaterialCrouton;

/**
 * @author Javier Gamarra
 */
public class LoginActivity extends Activity implements LoginListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		((LoginScreenlet) findViewById(R.id.login_screenlet)).setListener(this);
	}

	@Override
	public void onLoginSuccess(User user) {
		MaterialCrouton.info(this, "Login!");
	}

	@Override
	public void onLoginFailure(Exception e) {
		MaterialCrouton.error(this, "Error!", e);
	}
}
