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

package com.liferay.mobile.screens.service.v62;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class UserService extends BaseService {

    public UserService(Session session) {
        super(session);
    }

    public JSONObject addUser(long companyId, boolean autoPassword, String password1, String password2,
        boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String locale,
        String firstName, String middleName, String lastName, int prefixId, int suffixId, boolean male,
        int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds,
        JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, boolean sendEmail,
        JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("autoPassword", autoPassword);
            _params.put("password1", checkNull(password1));
            _params.put("password2", checkNull(password2));
            _params.put("autoScreenName", autoScreenName);
            _params.put("screenName", checkNull(screenName));
            _params.put("emailAddress", checkNull(emailAddress));
            _params.put("facebookId", facebookId);
            _params.put("openId", checkNull(openId));
            _params.put("locale", checkNull(locale));
            _params.put("firstName", checkNull(firstName));
            _params.put("middleName", checkNull(middleName));
            _params.put("lastName", checkNull(lastName));
            _params.put("prefixId", prefixId);
            _params.put("suffixId", suffixId);
            _params.put("male", male);
            _params.put("birthdayMonth", birthdayMonth);
            _params.put("birthdayDay", birthdayDay);
            _params.put("birthdayYear", birthdayYear);
            _params.put("jobTitle", checkNull(jobTitle));
            _params.put("groupIds", checkNull(groupIds));
            _params.put("organizationIds", checkNull(organizationIds));
            _params.put("roleIds", checkNull(roleIds));
            _params.put("userGroupIds", checkNull(userGroupIds));
            _params.put("sendEmail", sendEmail);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

            _command.put("/user/add-user", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject getUserByEmailAddress(long companyId, String emailAddress) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("emailAddress", checkNull(emailAddress));

            _command.put("/user/get-user-by-email-address", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject getUserById(long userId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("userId", userId);

            _command.put("/user/get-user-by-id", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject getUserByScreenName(long companyId, String screenName) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("screenName", checkNull(screenName));

            _command.put("/user/get-user-by-screen-name", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject updatePortrait(long userId, byte[] bytes) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("userId", userId);
            _params.put("bytes", toString(bytes));

            _command.put("/user/update-portrait", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject updateUser(long userId, String oldPassword, String newPassword1, String newPassword2,
        boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName,
        String emailAddress, long facebookId, String openId, String languageId, String timeZoneId, String greeting,
        String comments, String firstName, String middleName, String lastName, int prefixId, int suffixId, boolean male,
        int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn, String aimSn, String facebookSn,
        String icqSn, String jabberSn, String msnSn, String mySpaceSn, String skypeSn, String twitterSn, String ymSn,
        String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupRoles,
        JSONArray userGroupIds, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("userId", userId);
            _params.put("oldPassword", checkNull(oldPassword));
            _params.put("newPassword1", checkNull(newPassword1));
            _params.put("newPassword2", checkNull(newPassword2));
            _params.put("passwordReset", passwordReset);
            _params.put("reminderQueryQuestion", checkNull(reminderQueryQuestion));
            _params.put("reminderQueryAnswer", checkNull(reminderQueryAnswer));
            _params.put("screenName", checkNull(screenName));
            _params.put("emailAddress", checkNull(emailAddress));
            _params.put("facebookId", facebookId);
            _params.put("openId", checkNull(openId));
            _params.put("languageId", checkNull(languageId));
            _params.put("timeZoneId", checkNull(timeZoneId));
            _params.put("greeting", checkNull(greeting));
            _params.put("comments", checkNull(comments));
            _params.put("firstName", checkNull(firstName));
            _params.put("middleName", checkNull(middleName));
            _params.put("lastName", checkNull(lastName));
            _params.put("prefixId", prefixId);
            _params.put("suffixId", suffixId);
            _params.put("male", male);
            _params.put("birthdayMonth", birthdayMonth);
            _params.put("birthdayDay", birthdayDay);
            _params.put("birthdayYear", birthdayYear);
            _params.put("smsSn", checkNull(smsSn));
            _params.put("aimSn", checkNull(aimSn));
            _params.put("facebookSn", checkNull(facebookSn));
            _params.put("icqSn", checkNull(icqSn));
            _params.put("jabberSn", checkNull(jabberSn));
            _params.put("msnSn", checkNull(msnSn));
            _params.put("mySpaceSn", checkNull(mySpaceSn));
            _params.put("skypeSn", checkNull(skypeSn));
            _params.put("twitterSn", checkNull(twitterSn));
            _params.put("ymSn", checkNull(ymSn));
            _params.put("jobTitle", checkNull(jobTitle));
            _params.put("groupIds", checkNull(groupIds));
            _params.put("organizationIds", checkNull(organizationIds));
            _params.put("roleIds", checkNull(roleIds));
            _params.put("userGroupRoles", checkNull(userGroupRoles));
            _params.put("userGroupIds", checkNull(userGroupIds));
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

            _command.put("/user/update-user", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }
}