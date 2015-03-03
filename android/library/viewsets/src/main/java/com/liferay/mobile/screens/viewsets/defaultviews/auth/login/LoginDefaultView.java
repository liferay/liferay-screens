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

package com.liferay.mobile.screens.viewsets.defaultviews.auth.login;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.viewsets.R;

/**
 * @author Silvio Santos
 */
public class LoginDefaultView extends LinearLayout
	implements LoginViewModel, View.OnClickListener {

	public LoginDefaultView(Context context) {
		super(context, null);
	}

	public LoginDefaultView(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public LoginDefaultView(Context context, AttributeSet attributes, int defaultStyle) {
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
	public void showStartOperation(String actionName) {
		//TODO show progress dialog
	}

	@Override
	public void showFinishOperation(String actionName) {
		assert false : "Use showFinishOperation(user) instead";
	}

	@Override
	public void showFinishOperation(User user) {
		// TODO show success
	}

	@Override
	public void showFailedOperation(Exception e, Object... args) {
		//TODO show login error to user
	}

	@Override
	public void onClick(View view) {
		LoginScreenlet loginScreenlet = (LoginScreenlet) getParent();

		loginScreenlet.performUserAction(LoginScreenlet.LOGIN_ACTION);
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

	@Override
	protected void onAttachedToWindow() {
		int drawableId;
		if (AuthMethod.USER_ID.equals(_authMethod)) {
			_loginEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
			drawableId = R.drawable.default_user_icon;
		} else if (AuthMethod.EMAIL.equals(_authMethod)) {
			_loginEditText.setInputType(InputType.TYPE_CLASS_TEXT);
			drawableId = R.drawable.default_mail_icon;
		} else {
			_loginEditText.setInputType(InputType.TYPE_CLASS_TEXT);
			drawableId = R.drawable.default_user_icon;
		}

		_loginEditText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(drawableId), null, null, null);
	}

	private AuthMethod _authMethod;
	private EditText _loginEditText;
	private EditText _passwordEditText;

}