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
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.util.SessionContext;

/**
 * @author Silvio Santos
 */
public class LoginInteractorImpl implements LoginInteractor {

	public void login(String login, String password, AuthMethod authMethod) {
		validate(login, password, authMethod);

		switch (authMethod) {
			case EMAIL:
				sendGetUserByEmailRequest(login, password);

				break;

			case USER_ID:
				sendGetUserByIdRequest(Long.parseLong(login), password);

				break;

			case SCREEN_NAME:
				sendGetUserByScreenName(login, password);

				break;
		}
	}

	public void onEvent(LoginEvent event) {
		if (_listener == null) {
			return;
		}

		if (event.getType() == LoginEvent.REQUEST_SUCCESS) {
			_listener.onLoginSuccess();
		}
		else {
			_listener.onLoginFailure(event.getException());
		}
	}

	@Override
	public void onScreenletAttachted(LoginListener listener) {
		EventBusUtil.register(this);

		_listener = listener;
	}

	@Override
	public void onScreenletDetached(LoginListener listener) {
		EventBusUtil.unregister(this);

		_listener = null;
	}

	protected UserService getUserService(String login, String password) {
		Session session = SessionContext.createSession(login, password);
		session.setCallback(new LoginCallback());

		return new UserService(session);
	}

	protected void sendGetUserByEmailRequest(String email, String password) {
		UserService service = getUserService(email, password);

		try {
			service.getUserByEmailAddress(
				LiferayServerContext.getCompanyId(), email);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendGetUserByIdRequest(long userId, String password) {
		UserService service = getUserService(String.valueOf(userId), password);

		try {
			service.getUserById(userId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendGetUserByScreenName(String screenName, String password) {
		UserService service = getUserService(screenName, password);

		try {
			service.getUserByScreenName(
				LiferayServerContext.getCompanyId(), screenName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
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

	private LoginListener _listener;

}