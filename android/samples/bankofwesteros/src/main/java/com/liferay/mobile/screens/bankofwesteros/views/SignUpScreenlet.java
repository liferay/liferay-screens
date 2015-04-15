/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.bankofwesteros.views;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.auth.signup.interactor.SignUpInteractor;
import com.liferay.mobile.screens.auth.signup.view.SignUpViewModel;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

/**
 * @author Silvio Santos
 */
public class SignUpScreenlet
	extends com.liferay.mobile.screens.auth.signup.SignUpScreenlet
	implements com.liferay.mobile.screens.bankofwesteros.views.SignUpListener {

	public static final String TERMS_AND_CONDITIONS = "TERMS_AND_CONDITIONS";

	public SignUpScreenlet(Context context) {
		super(context);
	}

	public SignUpScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public SignUpScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public SignUpListener getListener() {
		return _listener;
	}

	public void setListener(SignUpListener value) {
		_listener = value;
	}

	@Override
	public void onClickOnTermsAndConditions() {
		if (_listener != null) {
			_listener.onClickOnTermsAndConditions();
		}
	}

	@Override
	public void onSignUpFailure(Exception e) {
		super.onSignUpFailure(e);
	}

	@Override
	public void onSignUpSuccess(User user) {
		super.onSignUpSuccess(user);
	}

	@Override
	protected void onUserAction(String userActionName, SignUpInteractor interactor, Object... args) {
		if (TERMS_AND_CONDITIONS.equals(userActionName)) {
			onClickOnTermsAndConditions();
		}
		else {
			super.onUserAction(userActionName, interactor, args);
		}
	}

	private SignUpListener _listener;
}