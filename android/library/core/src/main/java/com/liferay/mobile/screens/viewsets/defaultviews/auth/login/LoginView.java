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
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
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
	public BasicAuthMethod getBasicAuthMethod() {
		return _basicAuthMethod;
	}

	public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
		_basicAuthMethod = basicAuthMethod;

		refreshLoginEditTextStyle();
	}

	@Override
	public String getLogin() {
		return _loginEditText.getText().toString();
	}

	public AuthenticationType getAuthenticationType() {
		return _authenticationType;
	}

	@Override
	public void setAuthenticationType(AuthenticationType authenticationType) {
		_authenticationType = authenticationType;
	}

	@Override
	public String getPassword() {
		return _passwordEditText.getText().toString();
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
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	@Override
	public void onClick(View view) {
		LoginScreenlet loginScreenlet = (LoginScreenlet) getParent();
		if (view.getId() == R.id.liferay_login_button) {
			loginScreenlet.performUserAction(LoginScreenlet.BASIC_AUTH);
		}
		else {
			loginScreenlet.performUserAction(LoginScreenlet.OAUTH);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_loginEditText = (EditText) findViewById(R.id.liferay_login);
		_passwordEditText = (EditText) findViewById(R.id.liferay_password);
		_progressBar = (ModalProgressBar) findViewById(R.id.liferay_progress);

		_basicAuthenticationLayout = (LinearLayout) findViewById(R.id.basic_authentication_login);

		_oAuthButton = (Button) findViewById(R.id.oauth_authentication_login);
		if (_oAuthButton != null) {
			_oAuthButton.setOnClickListener(this);
		}

		_submitButton = (Button) findViewById(R.id.liferay_login_button);
		_submitButton.setOnClickListener(this);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (_oAuthButton != null) {
			_oAuthButton.setVisibility(AuthenticationType.OAUTH.equals(_authenticationType) ? VISIBLE : GONE);
		}

		if (_basicAuthenticationLayout != null) {
			_basicAuthenticationLayout.setVisibility(AuthenticationType.BASIC.equals(_authenticationType) ? VISIBLE : GONE);
		}

		if (AuthenticationType.BASIC.equals(_authenticationType)) {
			_loginEditText.setHint(getResources().getString(getLabelResourceForAuthMode()));
		}

		refreshLoginEditTextStyle();
	}

	protected void refreshLoginEditTextStyle() {
		if (_basicAuthMethod != null) {
			_loginEditText.setInputType(_basicAuthMethod.getInputType());
			_loginEditText.setCompoundDrawablesWithIntrinsicBounds(
				getResources().getDrawable(getLoginEditTextDrawableId()), null, null, null);
		}
	}

	protected int getLoginEditTextDrawableId() {
		if (BasicAuthMethod.USER_ID.equals(_basicAuthMethod)) {
			return R.drawable.default_user_icon;
		}
		else if (BasicAuthMethod.EMAIL.equals(_basicAuthMethod)) {
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

	private int getLabelResourceForAuthMode() {
		switch (_basicAuthMethod) {
			case SCREEN_NAME:
				return R.string.screen_name;
			case USER_ID:
				return R.string.user_id;
			default:
				return R.string.email_address;
		}
	}

	protected EditText _loginEditText;
	protected EditText _passwordEditText;
	protected Button _submitButton;
	protected LinearLayout _basicAuthenticationLayout;
	protected Button _oAuthButton;
	protected ModalProgressBar _progressBar;

	private AuthenticationType _authenticationType;
	private BasicAuthMethod _basicAuthMethod;

	private BaseScreenlet _screenlet;
}