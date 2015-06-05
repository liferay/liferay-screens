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

package com.liferay.mobile.screens.auth.login.interactor;

import android.text.TextUtils;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;


public class LoginBasicInteractor extends BaseLoginInteractor {

	public LoginBasicInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void login() throws Exception {
		validate();

		UserService service = getUserService();

		switch (_authMethod) {
			case EMAIL:
				service.getUserByEmailAddress(LiferayServerContext.getCompanyId(), _login);
				break;

			case USER_ID:
				service.getUserById(Long.parseLong(_login));
				break;

			case SCREEN_NAME:
				service.getUserByScreenName(LiferayServerContext.getCompanyId(), _login);
				break;
		}
	}

	public void setLogin(String value) {
		_login = value;
	}

	public void setPassword(String value) {
		_password = value;
	}

	public void setAuthMethod(AuthMethod value) {
		_authMethod = value;
	}

	protected UserService getUserService() {
		Session session = SessionContext.createSession(_login, _password);
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));

		return new UserService(session);
	}

	protected void validate() {
		if (_login == null) {
			throw new IllegalArgumentException("Login cannot be empty");
		}

		if (_password == null) {
			throw new IllegalArgumentException("Password cannot be empty");
		}

		if (_authMethod == null) {
			throw new IllegalArgumentException("AuthMethod cannot be empty");
		}

		if (_authMethod == AuthMethod.USER_ID && !TextUtils.isDigitsOnly(_login)) {
			throw new IllegalArgumentException("UserId has to be numeric");
		}
	}

	// NOTE: this interactor can store state because these attributes
	// aren't used after the request is fired.
	private String _login;
	private String _password;
	private AuthMethod _authMethod;

}