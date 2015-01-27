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

package com.liferay.mobile.screens.auth.login;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.login.interactor.AuthMethod;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractorImpl;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;

/**
 * @author Silvio Santos
 */
public class LoginScreenlet
	extends BaseScreenlet<LoginViewModel, LoginInteractor>
	implements LoginListener {

	public LoginScreenlet(Context context) {
		this(context, null);
	}

	public LoginScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public LoginScreenlet(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);

		setInteractor(new LoginInteractorImpl());
	}

	@Override
	public void onLoginFailure(Exception e) {
		LoginListener listenerView = (LoginListener)getScreenletView();
		listenerView.onLoginFailure(e);

		if (_listener != null) {
			_listener.onLoginFailure(e);
		}
	}

	@Override
	public void onLoginSuccess() {
		LoginListener listenerView = (LoginListener)getScreenletView();
		listenerView.onLoginSuccess();

		if (_listener != null) {
			_listener.onLoginSuccess();
		}
	}

	@Override
	public void onUserAction(int id) {
		LoginViewModel loginViewModel = (LoginViewModel)getScreenletView();
		String login = loginViewModel.getLogin();
		String password = loginViewModel.getPassword();
		AuthMethod method = loginViewModel.getAuthMethod();

		getInteractor().login(login, password, method);
	}

	public void setListener(LoginListener listener) {
		_listener = listener;
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.LoginScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(
			R.styleable.LoginScreenlet_layoutId, 0);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		int authMethod = typedArray.getInt(
			R.styleable.LoginScreenlet_authMethod, 0);

		LoginViewModel viewModel = (LoginViewModel)view;
		viewModel.setAuthMethod(AuthMethod.getValue(authMethod));

		typedArray.recycle();

		return view;
	}

	private LoginListener _listener;

}