/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.auth.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.interactor.BaseLoginInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginBasicInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginCookieInteractor;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.squareup.okhttp.Authenticator;

import static com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder.StorageType;

/**
 * @author Silvio Santos
 */
public class LoginScreenlet extends BaseScreenlet<LoginViewModel, BaseLoginInteractor> implements LoginListener {

	public static final String BASIC_AUTH = "BASIC_AUTH";
	public static final String LOGIN_SUCCESSFUL = "com.liferay.mobile.screens.auth.login.success";
	private LoginListener listener;
	private BasicAuthMethod basicAuthMethod;
	private AuthenticationType authenticationType;
	private StorageType credentialsStorage;
	private Authenticator authenticator;
	private boolean shouldHandleCookieExpiration;
	private int cookieExpirationTime;

	public LoginScreenlet(Context context) {
		super(context);
	}

	public LoginScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LoginScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public LoginScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	public void onLoginFailure(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (listener != null) {
			listener.onLoginFailure(e);
		}
	}

	@Override
	public void onLoginSuccess(User user) {
		getViewModel().showFinishOperation(user);

		if (listener != null) {
			listener.onLoginSuccess(user);
		}

		getContext().sendBroadcast(new Intent(LOGIN_SUCCESSFUL));

		SessionContext.removeStoredCredentials(credentialsStorage);
		SessionContext.storeCredentials(credentialsStorage);
	}

	public LoginListener getListener() {
		return listener;
	}

	public void setListener(LoginListener listener) {
		this.listener = listener;
	}

	public BasicAuthMethod getAuthMethod() {
		return basicAuthMethod;
	}

	public StorageType getCredentialsStorage() {
		return credentialsStorage;
	}

	public void setCredentialsStorage(StorageType value) {
		credentialsStorage = value;
	}

	public BasicAuthMethod getBasicAuthMethod() {
		return basicAuthMethod;
	}

	public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
		this.basicAuthMethod = basicAuthMethod;

		getViewModel().setBasicAuthMethod(this.basicAuthMethod);
	}

	public AuthenticationType getAuthenticationType() {
		return authenticationType;
	}

	public void setAuthenticationType(AuthenticationType authenticationType) {
		this.authenticationType = authenticationType;
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(Authenticator authenticator) {
		this.authenticator = authenticator;
	}

	public boolean isShouldHandleCookieRefresh() {
		return shouldHandleCookieExpiration;
	}

	public void setShouldHandleCookieExpiration(boolean shouldHandleCookieExpiration) {
		this.shouldHandleCookieExpiration = shouldHandleCookieExpiration;
	}

	public int getCookieExpirationTime() {
		return cookieExpirationTime;
	}

	public void setCookieExpirationTime(int cookieExpirationTime) {
		this.cookieExpirationTime = cookieExpirationTime;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.LoginScreenlet, 0, 0);

		int storeValue = typedArray.getInt(R.styleable.LoginScreenlet_credentialsStorage, StorageType.NONE.toInt());

		credentialsStorage = StorageType.valueOf(storeValue);

		shouldHandleCookieExpiration =
			typedArray.getBoolean(R.styleable.LoginScreenlet_shouldHandleCookieExpiration, true);
		cookieExpirationTime = typedArray.getInt(R.styleable.LoginScreenlet_cookieExpirationTime,
			CookieAuthentication.COOKIE_EXPIRATION_TIME);

		int layoutId = typedArray.getResourceId(R.styleable.LoginScreenlet_layoutId, getDefaultLayoutId());

		View view = LayoutInflater.from(context).inflate(layoutId, null);

		LoginViewModel loginViewModel = (LoginViewModel) view;

		int authMethodId = typedArray.getInt(R.styleable.LoginScreenlet_loginMode, 0);
		authenticationType = AuthenticationType.values()[authMethodId];

		loginViewModel.setAuthenticationType(authenticationType);

		if (AuthenticationType.BASIC.equals(authenticationType) || AuthenticationType.COOKIE.equals(
			authenticationType)) {
			int basicAuthMethodId = typedArray.getInt(R.styleable.LoginScreenlet_basicAuthMethod, 0);

			basicAuthMethod = BasicAuthMethod.getValue(basicAuthMethodId);
			loginViewModel.setBasicAuthMethod(basicAuthMethod);
		}

		typedArray.recycle();

		return view;
	}

	@Override
	protected BaseLoginInteractor createInteractor(String actionName) {
		if (AuthenticationType.COOKIE.equals(authenticationType)) {
			return new LoginCookieInteractor();
		} else {
			return new LoginBasicInteractor();
		}
	}

	@Override
	protected void onUserAction(String userActionName, BaseLoginInteractor interactor, Object... args) {
		if (AuthenticationType.COOKIE.equals(authenticationType)) {
			LoginViewModel viewModel = getViewModel();
			interactor.start(viewModel.getLogin(), viewModel.getPassword(), authenticator, shouldHandleCookieExpiration,
				cookieExpirationTime);
		} else if (AuthenticationType.BASIC.equals(authenticationType)) {
			LoginViewModel viewModel = getViewModel();
			interactor.start(viewModel.getLogin(), viewModel.getPassword(), viewModel.getBasicAuthMethod());
		}
	}
}