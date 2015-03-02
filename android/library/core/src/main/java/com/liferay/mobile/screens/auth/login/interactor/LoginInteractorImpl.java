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

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class LoginInteractorImpl
	extends BaseRemoteInteractor<LoginListener>
	implements LoginInteractor {

	public LoginInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void login(String login, String password, AuthMethod authMethod)
		throws Exception {

		validate(login, password, authMethod);

		UserService service = getUserService(login, password);

		switch (authMethod) {
			case EMAIL:
				sendGetUserByEmailRequest(service, login);
				break;

			case USER_ID:
				sendGetUserByIdRequest(service, Long.parseLong(login));
				break;

			case SCREEN_NAME:
				sendGetUserByScreenNameRequest(service, login);
				break;
		}
	}

	public void onEvent(JSONObjectEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			SessionContext.clearSession();
			getListener().onLoginFailure(event.getException());
		}
		else {
			User user = new User(event.getJSONObject());
			SessionContext.setLoggedUser(user);
			getListener().onLoginSuccess(user);
		}
	}

	protected UserService getUserService(String login, String password) {
		Session session = SessionContext.createSession(login, password);
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));

		return new UserService(session);
	}

	protected void sendGetUserByEmailRequest(UserService service, String email)
		throws Exception {

		service.getUserByEmailAddress(
			LiferayServerContext.getCompanyId(), email);
	}

	protected void sendGetUserByIdRequest(UserService service, long userId)
		throws Exception {

		service.getUserById(userId);
	}

	protected void sendGetUserByScreenNameRequest(
			UserService service, String screenName)
		throws Exception {

		service.getUserByScreenName(
			LiferayServerContext.getCompanyId(), screenName);
	}

	protected void validate(
		String login, String password, AuthMethod authMethod) {

		if (login == null) {
			throw new IllegalArgumentException("Login cannot be null");
		}

		if (password == null) {
			throw new IllegalArgumentException("Password cannot be null");
		}

		if (authMethod == null) {
			throw new IllegalArgumentException("AuthMethod cannot be null");
		}
	}

}