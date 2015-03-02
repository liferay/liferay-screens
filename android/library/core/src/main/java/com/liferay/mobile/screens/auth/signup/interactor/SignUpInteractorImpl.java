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

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.auth.signup.SignUpListener;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;

import org.json.JSONArray;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class SignUpInteractorImpl extends BaseRemoteInteractor<SignUpListener>
	implements SignUpInteractor {

	public SignUpInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void onEvent(JSONObjectEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onSignUpFailure(event.getException());
		}
		else {
			getListener().onSignUpSuccess(new User(event.getJSONObject()));
		}
	}

	public void signUp(
			long companyId, String firstName, String middleName,
			String lastName, String emailAddress, String screenName,
			String password, String jobTitle, Locale locale,
			String anonymousApiUserName, String anonymousApiPassword)
		throws Exception {

		validate(
			companyId, firstName, emailAddress, locale, anonymousApiUserName,
			anonymousApiPassword);

		UserService service = getUserService(
			anonymousApiUserName, anonymousApiPassword);

		sendSignUpRequest(
			service, companyId, firstName, middleName, lastName, emailAddress,
			screenName, password, jobTitle, locale);
	}

	protected UserService getUserService(
		String anonymousApiUserName, String anonymousApiPassword) {

		Authentication authentication = new BasicAuthentication(
			anonymousApiUserName, anonymousApiPassword);

		Session anonymousSession = new SessionImpl(
			LiferayServerContext.getServer(), authentication);

		anonymousSession.setCallback(
			new JSONObjectCallback(getTargetScreenletId()));

		return new UserService(anonymousSession);
	}

	protected void sendSignUpRequest(
			UserService service, long companyId, String firstName,
			String middleName, String lastName, String emailAddress,
			String screenName, String password, String jobTitle, Locale locale)
		throws Exception {

		middleName = (middleName != null) ? middleName : "";
		lastName = (lastName != null) ? lastName : "";
		screenName = (screenName != null) ? screenName : "";
		password = (password != null) ? password : "";
		jobTitle = (jobTitle != null) ? jobTitle : "";

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
		long companyId, String firstName, String emailAddress, Locale locale,
		String anonymousApiUserName, String anonymousApiPassword) {

		if (companyId <= 0) {
			throw new IllegalArgumentException(
				"CompanyId cannot be 0 or negative");
		}

		if ((firstName == null) || firstName.isEmpty()) {
			throw new IllegalArgumentException(
				"First name cannot be null or empty");
		}

		if ((emailAddress == null) || emailAddress.isEmpty()) {
			throw new IllegalArgumentException(
				"Email address cannot be null or empty");
		}

		if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be null");
		}

		if ((anonymousApiUserName == null) || anonymousApiUserName.isEmpty()) {
			throw new IllegalArgumentException(
				"Anonymous api user name cannot be null");
		}

		if ((anonymousApiPassword == null) || anonymousApiPassword.isEmpty()) {
			throw new IllegalArgumentException(
				"Anonymous api password cannot be null");
		}
	}

}