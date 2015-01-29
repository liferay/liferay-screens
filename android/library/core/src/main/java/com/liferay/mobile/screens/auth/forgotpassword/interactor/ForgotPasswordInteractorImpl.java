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

package com.liferay.mobile.screens.auth.forgotpassword.interactor;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.service.v62.mobilewidgetsuser.MobilewidgetsuserService;
import com.liferay.mobile.screens.util.LiferayServerContext;

/**
 * @author Jose Manuel Navarro
 */
public class ForgotPasswordInteractorImpl
	extends BaseInteractor<ForgotPasswordListener>
	implements ForgotPasswordInteractor {

	public ForgotPasswordInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void requestPassword(
		long companyId, String login, AuthMethod authMethod,
		String anonymousApiUserName, String anonymousApiPassword) {

		validate(
			companyId, login, authMethod, anonymousApiUserName,
			anonymousApiPassword);

		MobilewidgetsuserService service = getScreensUserService(
				anonymousApiUserName, anonymousApiPassword);

		switch (authMethod) {
			case EMAIL:
				sendForgotPasswordByEmailRequest(service, companyId, login);
				break;

			case USER_ID:
				sendForgotPasswordByIdRequest(service, Long.parseLong(login));
				break;

			case SCREEN_NAME:
				sendForgotPasswordByScreenNameRequest(service, companyId, login);
				break;
		}
	}

	public void onEvent(ForgotPasswordEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onForgotPasswordFailure(event.getException());
		}
		else {
			getListener().onForgotPasswordSuccess(event.isPasswordSent());
		}
	}

	protected MobilewidgetsuserService getScreensUserService(
		String anonymousApiUserName, String anonymousApiPassword) {

		Authentication authentication = new BasicAuthentication(
				anonymousApiUserName, anonymousApiPassword);

		Session anonymousSession = new SessionImpl(
				LiferayServerContext.getServer(), authentication);

		anonymousSession.setCallback(
				new ForgotPasswordCallback(getTargetScreenletId()));

		return new MobilewidgetsuserService(anonymousSession);
	}


	protected void sendForgotPasswordByEmailRequest(
		MobilewidgetsuserService service, long companyId, String emailAddress) {

		try {
			service.sendPasswordByEmailAddress(companyId, emailAddress);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendForgotPasswordByScreenNameRequest(
		MobilewidgetsuserService service, long companyId, String screenName) {

		try {
			service.sendPasswordByScreenName(companyId, screenName);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void sendForgotPasswordByIdRequest(
			MobilewidgetsuserService service, long userId) {

		try {
			service.sendPasswordByUserId(userId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void validate(
			long companyId, String login, AuthMethod authMethod,
			String anonymousApiUserName, String anonymousApiPassword) {

		if (companyId <= 0 && authMethod != AuthMethod.USER_ID) {
			throw new IllegalArgumentException(
				"Company cannot be 0 or negative");
		}

		if (login == null) {
			throw new IllegalArgumentException("Login cannot be null");
		}

		if (authMethod == null) {
			throw new IllegalArgumentException("AuthMethod cannot be null");
		}

		if (anonymousApiUserName == null) {
			throw new IllegalArgumentException(
				"Anonymous api user name cannot be null");
		}

		if (anonymousApiPassword == null) {
			throw new IllegalArgumentException(
				"Anonymous api password cannot be null");
		}
	}

}