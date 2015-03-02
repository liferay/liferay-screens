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

package com.liferay.mobile.screens.themes.auth.forgotpassword;

import android.content.Context;

import android.util.AttributeSet;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.forgotpassword.view.ForgotPasswordViewModel;
import com.liferay.mobile.screens.themes.R;

/**
 * @author Jose Manuel Navarro
 */
public class ForgotPasswordScreenletView extends LinearLayout
	implements ForgotPasswordViewModel, View.OnClickListener,
		ForgotPasswordListener {

	public ForgotPasswordScreenletView(Context context) {
		super(context, null);
	}

	public ForgotPasswordScreenletView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public ForgotPasswordScreenletView(Context context, AttributeSet attributes, int defaultStyle) {
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
	public void onClick(View view) {
		ForgotPasswordScreenlet screenlet =
			(ForgotPasswordScreenlet)getParent();

		screenlet.performUserAction(ForgotPasswordScreenlet.REQUEST_PASSWORD_ACTION);
	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {
		//TODO show user error?
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {
		//TODO show user success?
	}

	public void setAuthMethod(AuthMethod authMethod) {
		_authMethod = authMethod;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_loginEditText = (EditText)findViewById(R.id.login);

		Button requestButton = (Button)findViewById(R.id.request_button);
		requestButton.setOnClickListener(this);
	}

	private AuthMethod _authMethod;
	private EditText _loginEditText;

}