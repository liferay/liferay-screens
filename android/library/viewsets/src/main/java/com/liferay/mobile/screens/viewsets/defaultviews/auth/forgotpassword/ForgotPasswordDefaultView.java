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

package com.liferay.mobile.screens.viewsets.defaultviews.auth.forgotpassword;

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
import com.liferay.mobile.screens.viewsets.R;

/**
 * @author Jose Manuel Navarro
 */
public class ForgotPasswordDefaultView extends LinearLayout
	implements ForgotPasswordViewModel, View.OnClickListener {

	public ForgotPasswordDefaultView(Context context) {
		super(context, null);
	}

	public ForgotPasswordDefaultView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public ForgotPasswordDefaultView(Context context, AttributeSet attributes, int defaultStyle) {
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
	public void showStartOperation(String actionName) {
		//TODO show progress dialog
	}

	@Override
	public void showFinishOperation(String actionName) {
		assert false : "Use showFinishOperation(passwordSent) instead";
	}

	@Override
	public void showFinishOperation(boolean passwordSent) {
		int operationMsg = (passwordSent) ? R.string.password_sent : R.string.password_sent;

		//TODO show success with message
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		//TODO show user error?
	}

	@Override
	public void onClick(View view) {
		ForgotPasswordScreenlet screenlet = (ForgotPasswordScreenlet)getParent();

		screenlet.performUserAction();
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