/**
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

package com.liferay.mobile.screens.themes.auth.login;

import android.content.Context;

import android.util.AttributeSet;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.themes.R;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class LoginScreenletView extends LinearLayout
	implements LoginViewModel, View.OnClickListener, LoginListener {

	public LoginScreenletView(Context context) {
		super(context, null);
	}

	public LoginScreenletView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public LoginScreenletView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public AuthMethod getAuthMethod() {
		return _authMethod;
	}

	@Override
	public String getLogin() {
		return _loginEditText.getText().toString();
	}

	@Override
	public String getPassword() {
		return _passwordEditText.getText().toString();
	}

	@Override
	public void onClick(View view) {
		LoginScreenlet loginScreenlet = (LoginScreenlet)getParent();

		loginScreenlet.performUserAction(LoginScreenlet.LOGIN_ACTION);
	}

	@Override
	public void onLoginFailure(Exception e) {
		//TODO show login error to user??
	}

	@Override
	public void onLoginSuccess(JSONObject userAttributes) {
	}

	public void setAuthMethod(AuthMethod authMethod) {
		_authMethod = authMethod;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_loginEditText = (EditText)findViewById(R.id.login);
		_passwordEditText = (EditText)findViewById(R.id.password);

		Button loginButton = (Button)findViewById(R.id.login_button);
		loginButton.setOnClickListener(this);
	}

	private AuthMethod _authMethod;
	private EditText _loginEditText;
	private EditText _passwordEditText;

}