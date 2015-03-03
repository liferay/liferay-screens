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
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.signup.interactor.SignUpInteractor;
import com.liferay.mobile.screens.auth.signup.interactor.SignUpInteractorImpl;
import com.liferay.mobile.screens.auth.signup.view.SignUpViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStoreBuilder.*;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class SignUpScreenlet
	extends BaseScreenlet<SignUpViewModel, SignUpInteractor>
	implements SignUpListener {

	public SignUpScreenlet(Context context) {
		super(context, null);
	}

	public SignUpScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public SignUpScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void onSignUpFailure(Exception e) {
		getViewModel().showFailedOperation(null, e);

		if (_listener != null) {
			_listener.onSignUpFailure(e);
		}
	}

	@Override
	public void onSignUpSuccess(User user) {
		getViewModel().showFinishOperation(user);

		if (_listener != null) {
			_listener.onSignUpSuccess(user);
		}

		if (_autoLogin) {
			SignUpViewModel signUpViewModel = (SignUpViewModel)getScreenletView();
			String emailAddress = signUpViewModel.getEmailAddress();
			String password = signUpViewModel.getPassword();

			SessionContext.createSession(emailAddress, password);
			SessionContext.setLoggedUser(user);

			if (_autoLoginListener != null) {
				_autoLoginListener.onLoginSuccess(user);
			}

			SessionContext.storeSession(_credentialsStore);
		}
	}

	public void onUserAction(String userActionName) {
		SignUpViewModel signUpViewModel = (SignUpViewModel)getScreenletView();

		String firstName = signUpViewModel.getFirstName();
		String middleName = signUpViewModel.getMiddleName();
		String lastName = signUpViewModel.getLastName();
		String emailAddress = signUpViewModel.getEmailAddress();
		String password = signUpViewModel.getPassword();
		String screenName = signUpViewModel.getScreenName();
		String jobTitle = signUpViewModel.getJobTitle();
		Locale locale = getResources().getConfiguration().locale;

		try {
			getInteractor().signUp(
				_companyId, firstName, middleName, lastName, emailAddress,
				screenName, password, jobTitle, locale, _anonymousApiUserName,
				_anonymousApiPassword);
		}
		catch (Exception e) {
			onSignUpFailure(e);
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

	public StorageType getCredentialsStore() {
		return _credentialsStore;
	}

	public void setCredentialsStore(StorageType value) {
		_credentialsStore = value;
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.SignUpScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.SignUpScreenlet_layoutId, 0);

		_companyId = typedArray.getInt(
			R.styleable.SignUpScreenlet_companyId,
			(int)LiferayServerContext.getCompanyId());

		_anonymousApiUserName = typedArray.getString(
			R.styleable.SignUpScreenlet_anonymousApiUserName);

		_anonymousApiPassword = typedArray.getString(
			R.styleable.SignUpScreenlet_anonymousApiPassword);

		_autoLogin = typedArray.getBoolean(R.styleable.SignUpScreenlet_autoLogin, true);

		int storeValue = typedArray.getInt(R.styleable.SignUpScreenlet_credentialsStore,
			StorageType.NONE.toInt());

		_credentialsStore = StorageType.valueOf(storeValue);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected SignUpInteractor createInteractor(String actionName) {
		return new SignUpInteractorImpl(getScreenletId());
	}

	@Override
	protected void onUserAction(String userActionName, SignUpInteractor interactor, Object... args) {
		SignUpViewModel signUpViewModel = (SignUpViewModel)getScreenletView();

		String firstName = signUpViewModel.getFirstName();
		String middleName = signUpViewModel.getMiddleName();
		String lastName = signUpViewModel.getLastName();
		String emailAddress = signUpViewModel.getEmailAddress();
		String password = signUpViewModel.getPassword();
		String screenName = signUpViewModel.getScreenName();
		String jobTitle = signUpViewModel.getJobTitle();
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
	private StorageType _credentialsStore;

	private SignUpListener _listener;
	private LoginListener _autoLoginListener;

}