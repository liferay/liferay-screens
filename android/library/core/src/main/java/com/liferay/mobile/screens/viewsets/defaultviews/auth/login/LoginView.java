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
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.base.ModalProgressBar;
import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

/**
 * @author Silvio Santos
 */
public class LoginView extends LinearLayout
	implements LoginViewModel, View.OnClickListener {

	public LoginView(Context context) {
		super(context);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public LoginView(Context context, AttributeSet attributes) {
		super(context, attributes);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public LoginView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		DefaultTheme.initIfThemeNotPresent(context);
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
	public void setAuthenticationType(AuthenticationType authenticationType) {
		_authenticationType = authenticationType;
	}

	@Override
	public void showStartOperation(String actionName) {
		if (_progressBar != null) {
			_progressBar.startProgress();
		}
	}

	@Override
	public void showFinishOperation(String actionName) {
		throw new AssertionError("Use showFinishOperation(user) instead");
	}

	@Override
	public void showFinishOperation(User user) {
		if (_progressBar != null) {
			_progressBar.finishProgress();
		}

		LiferayLogger.i("Login successful: " + user.getId());
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		if (_progressBar != null) {
			_progressBar.finishProgress();
		}

		LiferayLogger.e("Could not login", e);

		LiferayCrouton.error(getContext(), getContext().getString(R.string.login_error), e);
	}

	@Override
	public void onClick(View view) {
		LoginScreenlet loginScreenlet = (LoginScreenlet) getParent();
		if (view.getId() == R.id.liferay_login_button) {
			loginScreenlet.performUserAction(LoginScreenlet.BASIC_AUTH);
		} else {
			loginScreenlet.performUserAction(LoginScreenlet.OAUTH);
		}
	}

	public void setAuthMethod(AuthMethod authMethod) {
		_authMethod = authMethod;

		refreshLoginEditTextStyle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_loginEditText = (EditText) findViewById(R.id.liferay_login);
		_passwordEditText = (EditText) findViewById(R.id.liferay_password);
		_progressBar = (ModalProgressBar) findViewById(R.id.liferay_progress);

		_basicAuthenticationLayout = (LinearLayout) findViewById(R.id.basic_authentication_login);

		_oAuthButton = (Button) findViewById(R.id.oauth_authentication_login);
		_oAuthButton.setOnClickListener(this);

		_submitButton = (Button) findViewById(R.id.liferay_login_button);
		_submitButton.setOnClickListener(this);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		_basicAuthenticationLayout.setVisibility(AuthenticationType.BASIC.equals(_authenticationType) ? VISIBLE : GONE);
		_oAuthButton.setVisibility(AuthenticationType.OAUTH.equals(_authenticationType) ? VISIBLE : GONE);

		refreshLoginEditTextStyle();
	}

	protected void refreshLoginEditTextStyle() {
		if (_authMethod != null) {
			_loginEditText.setInputType(_authMethod.getInputType());
			_loginEditText.setCompoundDrawablesWithIntrinsicBounds(
				getResources().getDrawable(getLoginEditTextDrawableId()), null, null, null);
		}
	}

	protected int getLoginEditTextDrawableId() {
		if (AuthMethod.USER_ID.equals(_authMethod)) {
			return R.drawable.default_user_icon;
		}
		else if (AuthMethod.EMAIL.equals(_authMethod)) {
			return R.drawable.default_mail_icon;
		}
		return R.drawable.default_user_icon;
	}

	protected EditText getLoginEditText() {
		return _loginEditText;
	}

	protected EditText getPasswordEditText() {
		return _passwordEditText;
	}

	protected Button getSubmitButton() {
		return _submitButton;
	}

	private EditText _loginEditText;
	private EditText _passwordEditText;
	private Button _submitButton;
	private LinearLayout _basicAuthenticationLayout;
	private Button _oAuthButton;
	private AuthenticationType _authenticationType;
	private AuthMethod _authMethod;
	private ModalProgressBar _progressBar;

}