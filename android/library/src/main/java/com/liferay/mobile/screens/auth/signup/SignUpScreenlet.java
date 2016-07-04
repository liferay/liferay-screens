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

package com.liferay.mobile.screens.auth.signup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.signup.interactor.SignUpInteractor;
import com.liferay.mobile.screens.auth.signup.interactor.SignUpInteractorImpl;
import com.liferay.mobile.screens.auth.signup.view.SignUpViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder.StorageType;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class SignUpScreenlet
	extends BaseScreenlet<SignUpViewModel, SignUpInteractor>
	implements SignUpListener {

	public SignUpScreenlet(Context context) {
		super(context);
	}

	public SignUpScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public SignUpScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onSignUpFailure(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (getListener() != null) {
			getListener().onSignUpFailure(e);
		}
	}

	@Override
	public void onSignUpSuccess(User user) {
		getViewModel().showFinishOperation(user);

		if (getListener() != null) {
			getListener().onSignUpSuccess(user);
		}

		if (_autoLogin) {
			SignUpViewModel viewModel = getViewModel();

			String authUsername = getAuthUsernameFromUser(user);
			String password = viewModel.getPassword();

			SessionContext.createBasicSession(authUsername, password);
			SessionContext.setCurrentUser(user);

			if (_autoLoginListener != null) {
				_autoLoginListener.onLoginSuccess(user);
			}

			SessionContext.storeCredentials(_credentialsStorage);
		}
	}

	public String getAuthUsernameFromUser(User user) {
		switch (_basicAuthMethod) {
			case SCREEN_NAME:
				return user.getScreenName();
			case USER_ID:
				return String.valueOf(user.getId());
			case EMAIL:
			default:
				return user.getEmail();
		}
	}

	public String getAnonymousApiPassword() {
		return _anonymousApiPassword;
	}

	public void setAnonymousApiPassword(String value) {
		_anonymousApiPassword = value;
	}

	public String getAnonymousApiUserName() {
		return _anonymousApiUserName;
	}

	public void setAnonymousApiUserName(String value) {
		_anonymousApiUserName = value;
	}

	public boolean isAutoLogin() {
		return _autoLogin;
	}

	public void setAutoLogin(boolean value) {
		_autoLogin = value;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long value) {
		_companyId = value;
	}

	public SignUpListener getListener() {
		return _listener;
	}

	public void setListener(SignUpListener value) {
		_listener = value;
	}

	public LoginListener getAutoLoginListener() {
		return _autoLoginListener;
	}

	public void setAutoLoginListener(LoginListener value) {
		_autoLoginListener = value;
	}

	public StorageType getCredentialsStorage() {
		return _credentialsStorage;
	}

	public void setCredentialsStorage(StorageType value) {
		_credentialsStorage = value;
	}

	public BasicAuthMethod getBasicAuthMethod() {
		return _basicAuthMethod;
	}

	public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
		_basicAuthMethod = basicAuthMethod;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.SignUpScreenlet, 0, 0);

		_companyId = castToLongOrUseDefault(typedArray.getString(
			R.styleable.SignUpScreenlet_companyId),
			LiferayServerContext.getCompanyId());

		_anonymousApiUserName = typedArray.getString(
			R.styleable.SignUpScreenlet_anonymousApiUserName);

		_anonymousApiPassword = typedArray.getString(
			R.styleable.SignUpScreenlet_anonymousApiPassword);

		_autoLogin = typedArray.getBoolean(R.styleable.SignUpScreenlet_autoLogin, true);

		int storageValue = typedArray.getInt(R.styleable.SignUpScreenlet_credentialsStorage,
			StorageType.NONE.toInt());

		_credentialsStorage = StorageType.valueOf(storageValue);

		_autoLogin = typedArray.getBoolean(R.styleable.SignUpScreenlet_autoLogin, true);

		int authMethodId = typedArray.getInt(R.styleable.SignUpScreenlet_basicAuthMethod, 0);
		_basicAuthMethod = BasicAuthMethod.getValue(authMethodId);

		int layoutId = typedArray.getResourceId(
			R.styleable.SignUpScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected SignUpInteractor createInteractor(String actionName) {
		return new SignUpInteractorImpl(getScreenletId());
	}

	@Override
	protected void onUserAction(String userActionName, SignUpInteractor interactor, Object... args) {
		SignUpViewModel viewModel = getViewModel();

		String firstName = viewModel.getFirstName();
		String middleName = viewModel.getMiddleName();
		String lastName = viewModel.getLastName();
		String emailAddress = viewModel.getEmailAddress();
		String password = viewModel.getPassword();
		String screenName = viewModel.getScreenName();
		String jobTitle = viewModel.getJobTitle();
		Locale locale = getResources().getConfiguration().locale;

		try {
			interactor.signUp(
				_companyId, firstName, middleName, lastName, emailAddress,
				screenName, password, jobTitle, locale, _anonymousApiUserName,
				_anonymousApiPassword);
		}
		catch (Exception e) {
			onSignUpFailure(e);
		}
	}

	private String _anonymousApiPassword;
	private String _anonymousApiUserName;
	private boolean _autoLogin;
	private long _companyId;
	private StorageType _credentialsStorage;
	private BasicAuthMethod _basicAuthMethod;

	private SignUpListener _listener;
	private LoginListener _autoLoginListener;

}