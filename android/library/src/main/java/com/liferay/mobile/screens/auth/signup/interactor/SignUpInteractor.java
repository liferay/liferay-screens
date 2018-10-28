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
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.auth.signup.SignUpListener;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class SignUpInteractor extends BaseRemoteInteractor<SignUpListener, BasicEvent> {

    @Override
    public BasicEvent execute(Object[] args) throws Exception {

        long companyId = (long) args[0];
        String firstName = (String) args[1];
        String middleName = (String) args[2];
        String lastName = (String) args[3];
        String emailAddress = (String) args[4];
        String screenName = (String) args[5];
        String password = (String) args[6];
        String jobTitle = (String) args[7];
        Locale locale = (Locale) args[8];
        String anonymousApiUserName = (String) args[9];
        String anonymousApiPassword = (String) args[10];

        validate(companyId, firstName, emailAddress, locale, anonymousApiUserName, anonymousApiPassword);

        Authentication authentication = new BasicAuthentication(anonymousApiUserName, anonymousApiPassword);

        Session anonymousSession = new SessionImpl(LiferayServerContext.getServer(), authentication);
        UserConnector userConnector = ServiceProvider.getInstance().getUserConnector(anonymousSession);

        JSONObject jsonObject =
            sendSignUpRequest(userConnector, companyId, firstName, middleName, lastName, emailAddress, screenName,
                password, jobTitle, locale);
        return new BasicEvent(jsonObject);
    }

    @Override
    public void onSuccess(BasicEvent event) {
        getListener().onSignUpSuccess(new User(event.getJSONObject()));
    }

    @Override
    public void onFailure(BasicEvent event) {
        getListener().onSignUpFailure(event.getException());
    }

    protected JSONObject sendSignUpRequest(UserConnector userConnector, long companyId, String firstName,
        String middleName, String lastName, String emailAddress, String screenName, String password, String jobTitle,
        Locale locale) throws Exception {

        middleName = (middleName != null) ? middleName : "";
        lastName = (lastName != null) ? lastName : "";
        screenName = (screenName != null) ? screenName : "";
        password = (password != null) ? password : "";
        jobTitle = (jobTitle != null) ? jobTitle : "";

        boolean autoPassword = password.isEmpty();
        boolean autoScreenName = screenName.isEmpty();
        long facebookId = 0;
        String openId = "";
        int prefixId = 0;
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

        return userConnector.addUser(companyId, autoPassword, password, password, autoScreenName, screenName,
            emailAddress, facebookId, openId, locale.toString(), firstName, middleName, lastName, prefixId, suffixId,
            male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
            sendEmail, null);
    }

    protected void validate(long companyId, String firstName, String emailAddress, Locale locale,
        String anonymousApiUserName, String anonymousApiPassword) {

        if (companyId <= 0) {
            throw new IllegalArgumentException("CompanyId cannot be 0 or negative");
        } else if ((firstName == null) || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        } else if ((emailAddress == null) || emailAddress.isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty");
        } else if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be empty");
        } else if ((anonymousApiUserName == null) || anonymousApiUserName.isEmpty()) {
            throw new IllegalArgumentException("Anonymous api user name cannot be empty");
        } else if ((anonymousApiPassword == null) || anonymousApiPassword.isEmpty()) {
            throw new IllegalArgumentException("Anonymous api password cannot be empty");
        }
    }
}