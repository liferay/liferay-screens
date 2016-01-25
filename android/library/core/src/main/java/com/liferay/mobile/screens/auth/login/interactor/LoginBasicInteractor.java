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
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v7.UserService;


public class LoginBasicInteractor extends BaseLoginInteractor {

	public LoginBasicInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void login() throws Exception {
		validate(_login, _password, _basicAuthMethod);

		UserService service = getUserService(_login, _password);

		switch (_basicAuthMethod) {
			case EMAIL:
				sendGetUserByEmailRequest(service, _login);
				break;

			case USER_ID:
				sendGetUserByIdRequest(service, Long.parseLong(_login));
				break;

			case SCREEN_NAME:
				sendGetUserByScreenNameRequest(service, _login);
				break;
		}
	}

	public void setLogin(String value) {
		_login = value;
	}

	public void setPassword(String value) {
		_password = value;
	}

	public void setBasicAuthMethod(BasicAuthMethod value) {
		_basicAuthMethod = value;
	}

	protected UserService getUserService(String login, String password) {
		Session session = SessionContext.createBasicSession(login, password);
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));

		return new UserService(session);
	}

	protected void sendGetUserByEmailRequest(UserService service, String email) throws Exception {
		service.getUserByEmailAddress(LiferayServerContext.getCompanyId(), email);
	}

	protected void sendGetUserByScreenNameRequest(UserService service, String screenName)
		throws Exception {
		service.getUserByScreenName(LiferayServerContext.getCompanyId(), screenName);
	}

	protected void sendGetUserByIdRequest(UserService service, long userId) throws Exception {
		service.getUserById(userId);
	}

	protected void validate(String login, String password, BasicAuthMethod basicAuthMethod) {
		if (login == null) {
			throw new IllegalArgumentException("Login cannot be empty");
		}

		if (password == null) {
			throw new IllegalArgumentException("Password cannot be empty");
		}

		if (basicAuthMethod == null) {
			throw new IllegalArgumentException("BasicAuthMethod cannot be empty");
		}

		if (basicAuthMethod == BasicAuthMethod.USER_ID && !TextUtils.isDigitsOnly(login)) {
			throw new IllegalArgumentException("UserId has to be a number");
		}
	}

	// NOTE: this interactor can store state because these attributes
	// aren't used after the request is fired.
	protected String _login;
	protected String _password;
	protected BasicAuthMethod _basicAuthMethod;

}