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

package com.liferay.mobile.screens.base.thread.interactors;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.connector.ForgotPasswordConnector;
import com.liferay.mobile.screens.base.thread.BaseThreadInteractor;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.base.thread.event.ForgotPasswordThreadEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Jose Manuel Navarro
 */
public class ForgotPasswordThreadInteractorImpl
	extends BaseThreadInteractor<ForgotPasswordListener, ForgotPasswordThreadEvent> {

	public ForgotPasswordThreadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public ForgotPasswordThreadEvent execute(Object[] args) throws Exception {

		long companyId = (long) args[0];
		String login = (String) args[1];
		BasicAuthMethod basicAuthMethod = (BasicAuthMethod) args[2];
		String anonymousApiUserName = (String) args[3];
		String anonymousApiPassword = (String) args[4];

		validate(
			companyId, login, basicAuthMethod, anonymousApiUserName,
			anonymousApiPassword);

		ForgotPasswordConnector connector = getScreensUserService(
			anonymousApiUserName, anonymousApiPassword);

		boolean result = method(companyId, login, basicAuthMethod, connector);

		return new ForgotPasswordThreadEvent(result);
	}

	@Override
	public void onFailure(BasicThreadEvent event) {
		getListener().onForgotPasswordRequestFailure(event.getException());
	}

	@Override
	public void onSuccess(ForgotPasswordThreadEvent event) {
		getListener().onForgotPasswordRequestSuccess(event.isPasswordSent());
	}

	protected ForgotPasswordConnector getScreensUserService(
		String anonymousApiUserName, String anonymousApiPassword) {

		Authentication authentication = new BasicAuthentication(anonymousApiUserName, anonymousApiPassword);
		Session anonymousSession = new SessionImpl(LiferayServerContext.getServer(), authentication);
		return ServiceProvider.getInstance().getForgotPasswordConnector(anonymousSession);
	}

	protected void validate(
		long companyId, String login, BasicAuthMethod basicAuthMethod, String anonymousApiUserName,
		String anonymousApiPassword) {

		if ((companyId <= 0) && (basicAuthMethod != BasicAuthMethod.USER_ID)) {
			throw new IllegalArgumentException(
				"CompanyId cannot be 0 or negative");
		}

		if (login == null) {
			throw new IllegalArgumentException("Login cannot be empty");
		}

		if (basicAuthMethod == null) {
			throw new IllegalArgumentException("BasicAuthMethod cannot be empty");
		}

		if (anonymousApiUserName == null) {
			throw new IllegalArgumentException(
				"Anonymous api user name cannot be empty");
		}

		if (anonymousApiPassword == null) {
			throw new IllegalArgumentException(
				"Anonymous api password cannot be empty");
		}
	}

	private Boolean method(long companyId, String login, BasicAuthMethod basicAuthMethod, ForgotPasswordConnector connector) throws Exception {
		switch (basicAuthMethod) {
			case EMAIL:
				return connector.sendPasswordByEmailAddress(companyId, login);
			case USER_ID:
				return connector.sendPasswordByUserId(Long.parseLong(login));
			case SCREEN_NAME:
				return connector.sendPasswordByScreenName(companyId, login);
		}
		return false;
	}

}