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

package com.liferay.mobile.screens.auth.signup.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.util.SessionContext;

import java.util.Locale;

import org.json.JSONArray;

/**
 * @author Silvio Santos
 */
public class SignUpInteractorImpl extends BaseInteractor<SignUpListener>
	implements SignUpInteractor {

	public SignUpInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void onEvent(SignUpEvent event) {
		if (_listener == null) {
			return;
		}

		if (event.isFailed()) {
			_listener.onSignUpFailure(event.getException());
		}
		else {
			_listener.onSignUpSuccess();
		}
	}

	@Override
	public void onScreenletAttachted(SignUpListener listener) {
		_listener = listener;

		EventBusUtil.register(this);
	}

	@Override
	public void onScreenletDetached(SignUpListener listener) {
		EventBusUtil.unregister(this);

		_listener = null;
	}

	public void signUp(
			long companyId, String firstName, String middleName,
			String lastName, String emailAddress, String screenName,
			String password, String jobTitle, Locale locale,
			String anonymousApiUserName, String anonymousApiPassword)
		throws Exception {

		validate(anonymousApiUserName, anonymousApiPassword);

		sendSignUpRequest(
			companyId, firstName, middleName, lastName, emailAddress,
			screenName, password, jobTitle, locale, anonymousApiUserName,
			anonymousApiPassword);
	}

	protected UserService getUserService(String login, String password) {
		Session session = SessionContext.createSession(login, password);
		session.setCallback(new SignUpCallback(getTargetScreenletId()));
		UserService service = new UserService(session);

		return service;
	}

	protected void sendSignUpRequest(
			long companyId, String firstName, String middleName,
			String lastName, String emailAddress, String screenName,
			String password, String jobTitle, Locale locale,
			String anonymousApiUserName, String anonymousApiPassword)
		throws Exception {

		UserService service = getUserService(
			anonymousApiUserName, anonymousApiPassword);

		boolean autoPassword = password.isEmpty();
		boolean autoScreenName = screenName.isEmpty();
		long facebookId = 0;
		String openId = "";
		int preffixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 1;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		JSONArray groupIds = new JSONArray();
		groupIds.put(LiferayServerContext.getGroupId());

		JSONArray organizationIds = new JSONArray();
		JSONArray roleIds = new JSONArray();
		JSONArray userGroupIds = new JSONArray();
		boolean sendEmail = true;

		service.addUser(
			companyId, autoPassword, password, password, autoScreenName,
			screenName, emailAddress, facebookId, openId, locale.toString(),
			firstName, middleName, lastName, preffixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendEmail, null);
	}

	protected void validate(
		String anonymousApiUserName, String anonymousApiPassword) {

		if (anonymousApiUserName == null) {
			throw new IllegalArgumentException(
				"Anonymous api user name cannot be null");
		}

		if (anonymousApiPassword == null) {
			throw new IllegalArgumentException(
				"Anonymous api password cannot be null");
		}
	}

	private SignUpListener _listener;

}