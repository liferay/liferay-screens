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
import android.widget.EditText;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 */
public class LoginActivity extends ThemeActivity implements LoginListener {

	private LoginScreenlet loginScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(this);

		setDefaultValues();
	}

	@Override
	public void onLoginSuccess(User user) {
		info(getString(R.string.login_success_info));
	}

	@Override
	public void onLoginFailure(Exception e) {
		error(getString(R.string.login_screenlet_error), e);
	}

	private void setDefaultValues() {
		EditText login = (EditText) loginScreenlet.findViewById(R.id.liferay_login);
		login.setText(getString(R.string.liferay_default_user_name));

		EditText password = (EditText) loginScreenlet.findViewById(R.id.liferay_password);
		password.setText(getString(R.string.liferay_default_password));
	}
}
