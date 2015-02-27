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
import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractorImpl;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;

import org.json.JSONObject;

import static com.liferay.mobile.screens.context.storage.CredentialsStoreBuilder.*;

/**
 * @author Silvio Santos
 */
public class LoginScreenlet
	extends BaseScreenlet<LoginViewModel, LoginInteractor>
	implements LoginListener {

	public static final String LOGIN_ACTION = "login";

	public LoginScreenlet(Context context) {
		this(context, null);
	}

	public LoginScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public LoginScreenlet(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	public LoginInteractor getInteractor() {
		LoginInteractor interactor = super.getInteractor();

		if (interactor == null) {
			interactor = new LoginInteractorImpl(getScreenletId());

			setInteractor(interactor);
		}

		return interactor;
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
	public void onLoginSuccess(JSONObject userAttributes) {
		LoginListener listenerView = (LoginListener)getScreenletView();
		listenerView.onLoginSuccess(userAttributes);

		if (_listener != null) {
			_listener.onLoginSuccess(userAttributes);
		}

		SessionContext.storeSession(_credentialsStore);
	}

	@Override
	public void onUserAction(String userActionName) {
		LoginViewModel loginViewModel = (LoginViewModel)getScreenletView();
		String login = loginViewModel.getLogin();
		String password = loginViewModel.getPassword();
		AuthMethod method = loginViewModel.getAuthMethod();

		try {
			getInteractor().login(login, password, method);
		}
		catch (Exception e) {
			onLoginFailure(e);
		}
	}

	public void setListener(LoginListener listener) {
		_listener = listener;
	}

	public AuthMethod getAuthMethod() {
		return _authMethod;
	}

	public void setAuthMethod(AuthMethod authMethod) {
		_authMethod = authMethod;
	}

	public StorageType getCredentialsStore() {
		return _credentialsStore;
	}

	public void setCredentialsStore(StorageType value) {
		_credentialsStore = value;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.LoginScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(
			R.styleable.LoginScreenlet_layoutId, 0);

		int authMethodId = typedArray.getInt(R.styleable.LoginScreenlet_authMethod, 0);

		int storeValue = typedArray.getInt(R.styleable.LoginScreenlet_credentialsStore,
			StorageType.NONE.toInt());

		_credentialsStore = StorageType.valueOf(storeValue);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		LoginViewModel viewModel = (LoginViewModel) view;
		_authMethod = AuthMethod.getValue(authMethodId);
		viewModel.setAuthMethod(_authMethod);

		typedArray.recycle();

		return view;
	}

	private LoginListener _listener;
	private AuthMethod _authMethod;
	private StorageType _credentialsStore;

}