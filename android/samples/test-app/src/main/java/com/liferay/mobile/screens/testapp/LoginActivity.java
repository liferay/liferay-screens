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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liferay.mobile.android.oauth.OAuthConfig;
import com.liferay.mobile.android.oauth.activity.OAuthActivity;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 */
public class LoginActivity extends ThemeActivity implements LoginListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		_loginScreenlet = (LoginScreenlet) getActiveScreenlet(R.id.login_default, R.id.login_material);

		_loginScreenlet.setVisibility(View.VISIBLE);
		_loginScreenlet.setListener(this);

		hideInactiveScreenlet(R.id.login_default, R.id.login_material);
	}

	@Override
	public void onActivityResult(int request, int result, Intent intent) {
		_loginScreenlet.sendOAuthResult(result, intent);
	}

	@Override
	public void onLoginSuccess(User user) {
		info("Login successful!");
	}

	@Override
	public void onLoginFailure(Exception e) {
		error("Login failed", e);
	}

	private LoginScreenlet _loginScreenlet;
}
