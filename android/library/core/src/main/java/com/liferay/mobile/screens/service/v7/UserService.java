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

package com.liferay.mobile.screens.service.v7;

import com.liferay.mobile.android.http.file.UploadData;
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

	public JSONObject getUserById(long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);

			_command.put("/user/get-user-by-id", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONArray getGroupUsers(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/user/get-group-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONObject updatePortrait(long userId, byte[] bytes) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("bytes", toString(bytes));

			_command.put("/user/update-portrait", _params);
		}
		catch (JSONException _je) {
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
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public Boolean hasGroupUser(long groupId, long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);

			_command.put("/user/has-group-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public JSONArray getOrganizationUsers(long organizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);

			_command.put("/user/get-organization-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONArray getUserGroupUsers(long userGroupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupId", userGroupId);

			_command.put("/user/get-user-group-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public void addOrganizationUsers(long organizationId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/add-organization-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addPasswordPolicyUsers(long passwordPolicyId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("passwordPolicyId", passwordPolicyId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/add-password-policy-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addRoleUsers(long roleId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/add-role-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addTeamUsers(long teamId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("teamId", teamId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/add-team-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addUserGroupUsers(long userGroupId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupId", userGroupId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/add-user-group-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject addUserWithWorkflow(long companyId, boolean autoPassword, String password1, String password2, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String locale, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, boolean sendEmail, JSONObjectWrapper serviceContext) throws Exception {
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

			_command.put("/user/add-user-with-workflow", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject addUserWithWorkflow(long companyId, boolean autoPassword, String password1, String password2, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String locale, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, JSONArray addresses, JSONArray emailAddresses, JSONArray phones, JSONArray websites, JSONArray announcementsDelivers, boolean sendEmail, JSONObjectWrapper serviceContext) throws Exception {
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
			_params.put("addresses", checkNull(addresses));
			_params.put("emailAddresses", checkNull(emailAddresses));
			_params.put("phones", checkNull(phones));
			_params.put("websites", checkNull(websites));
			_params.put("announcementsDelivers", checkNull(announcementsDelivers));
			_params.put("sendEmail", sendEmail);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/add-user-with-workflow", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public void deletePortrait(long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);

			_command.put("/user/delete-portrait", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteRoleUser(long roleId, long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("userId", userId);

			_command.put("/user/delete-role-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getCompanyUsers(long companyId, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/user/get-company-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public Integer getCompanyUsersCount(long companyId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);

			_command.put("/user/get-company-users-count", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getInt(0);
	}

	public JSONArray getGroupUserIds(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/user/get-group-user-ids", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONArray getOrganizationUserIds(long organizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);

			_command.put("/user/get-organization-user-ids", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONObject getUserByEmailAddress(long companyId, String emailAddress) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("emailAddress", checkNull(emailAddress));

			_command.put("/user/get-user-by-email-address", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public Long getUserIdByEmailAddress(long companyId, String emailAddress) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("emailAddress", checkNull(emailAddress));

			_command.put("/user/get-user-id-by-email-address", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public Long getUserIdByScreenName(long companyId, String screenName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("screenName", checkNull(screenName));

			_command.put("/user/get-user-id-by-screen-name", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public Boolean hasRoleUser(long companyId, String name, long userId, boolean inherited) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("userId", userId);
			_params.put("inherited", inherited);

			_command.put("/user/has-role-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public Boolean hasRoleUser(long roleId, long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("userId", userId);

			_command.put("/user/has-role-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public Boolean sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("emailAddress", checkNull(emailAddress));

			_command.put("/user/send-password-by-email-address", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public Boolean sendPasswordByScreenName(long companyId, String screenName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("screenName", checkNull(screenName));

			_command.put("/user/send-password-by-screen-name", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public Boolean sendPasswordByUserId(long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);

			_command.put("/user/send-password-by-user-id", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public void setRoleUsers(long roleId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/set-role-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void setUserGroupUsers(long userGroupId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupId", userGroupId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/set-user-group-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetGroupTeamsUsers(long groupId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/unset-group-teams-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetOrganizationUsers(long organizationId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/unset-organization-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetPasswordPolicyUsers(long passwordPolicyId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("passwordPolicyId", passwordPolicyId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/unset-password-policy-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetRoleUsers(long roleId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/unset-role-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetTeamUsers(long teamId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("teamId", teamId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/unset-team-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetUserGroupUsers(long userGroupId, JSONArray userIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupId", userGroupId);
			_params.put("userIds", checkNull(userIds));

			_command.put("/user/unset-user-group-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateAgreedToTermsOfUse(long userId, boolean agreedToTermsOfUse) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("agreedToTermsOfUse", agreedToTermsOfUse);

			_command.put("/user/update-agreed-to-terms-of-use", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateIncompleteUser(long companyId, boolean autoPassword, String password1, String password2, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String locale, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, boolean updateUserInformation, boolean sendEmail, JSONObjectWrapper serviceContext) throws Exception {
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
			_params.put("updateUserInformation", updateUserInformation);
			_params.put("sendEmail", sendEmail);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/update-incomplete-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateOpenId(long userId, String openId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("openId", checkNull(openId));

			_command.put("/user/update-open-id", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public void updateOrganizations(long userId, JSONArray organizationIds, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("organizationIds", checkNull(organizationIds));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/update-organizations", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateReminderQuery(long userId, String question, String answer) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("question", checkNull(question));
			_params.put("answer", checkNull(answer));

			_command.put("/user/update-reminder-query", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateScreenName(long userId, String screenName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("screenName", checkNull(screenName));

			_command.put("/user/update-screen-name", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateUser(long userId, String oldPassword, String newPassword1, String newPassword2, boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName, String emailAddress, long facebookId, String openId, String languageId, String timeZoneId, String greeting, String comments, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn, String facebookSn, String jabberSn, String skypeSn, String twitterSn, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupRoles, JSONArray userGroupIds, JSONArray addresses, JSONArray emailAddresses, JSONArray phones, JSONArray websites, JSONArray announcementsDelivers, JSONObjectWrapper serviceContext) throws Exception {
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
			_params.put("facebookSn", checkNull(facebookSn));
			_params.put("jabberSn", checkNull(jabberSn));
			_params.put("skypeSn", checkNull(skypeSn));
			_params.put("twitterSn", checkNull(twitterSn));
			_params.put("jobTitle", checkNull(jobTitle));
			_params.put("groupIds", checkNull(groupIds));
			_params.put("organizationIds", checkNull(organizationIds));
			_params.put("roleIds", checkNull(roleIds));
			_params.put("userGroupRoles", checkNull(userGroupRoles));
			_params.put("userGroupIds", checkNull(userGroupIds));
			_params.put("addresses", checkNull(addresses));
			_params.put("emailAddresses", checkNull(emailAddresses));
			_params.put("phones", checkNull(phones));
			_params.put("websites", checkNull(websites));
			_params.put("announcementsDelivers", checkNull(announcementsDelivers));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/update-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateUser(long userId, String oldPassword, String newPassword1, String newPassword2, boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName, String emailAddress, long facebookId, String openId, boolean portrait, byte[] portraitBytes, String languageId, String timeZoneId, String greeting, String comments, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn, String facebookSn, String jabberSn, String skypeSn, String twitterSn, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupRoles, JSONArray userGroupIds, JSONArray addresses, JSONArray emailAddresses, JSONArray phones, JSONArray websites, JSONArray announcementsDelivers, JSONObjectWrapper serviceContext) throws Exception {
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
			_params.put("portrait", portrait);
			_params.put("portraitBytes", toString(portraitBytes));
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
			_params.put("facebookSn", checkNull(facebookSn));
			_params.put("jabberSn", checkNull(jabberSn));
			_params.put("skypeSn", checkNull(skypeSn));
			_params.put("twitterSn", checkNull(twitterSn));
			_params.put("jobTitle", checkNull(jobTitle));
			_params.put("groupIds", checkNull(groupIds));
			_params.put("organizationIds", checkNull(organizationIds));
			_params.put("roleIds", checkNull(roleIds));
			_params.put("userGroupRoles", checkNull(userGroupRoles));
			_params.put("userGroupIds", checkNull(userGroupIds));
			_params.put("addresses", checkNull(addresses));
			_params.put("emailAddresses", checkNull(emailAddresses));
			_params.put("phones", checkNull(phones));
			_params.put("websites", checkNull(websites));
			_params.put("announcementsDelivers", checkNull(announcementsDelivers));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/update-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateUser(long userId, String oldPassword, String newPassword1, String newPassword2, boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName, String emailAddress, long facebookId, String openId, String languageId, String timeZoneId, String greeting, String comments, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn, String facebookSn, String jabberSn, String skypeSn, String twitterSn, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupRoles, JSONArray userGroupIds, JSONObjectWrapper serviceContext) throws Exception {
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
			_params.put("facebookSn", checkNull(facebookSn));
			_params.put("jabberSn", checkNull(jabberSn));
			_params.put("skypeSn", checkNull(skypeSn));
			_params.put("twitterSn", checkNull(twitterSn));
			_params.put("jobTitle", checkNull(jobTitle));
			_params.put("groupIds", checkNull(groupIds));
			_params.put("organizationIds", checkNull(organizationIds));
			_params.put("roleIds", checkNull(roleIds));
			_params.put("userGroupRoles", checkNull(userGroupRoles));
			_params.put("userGroupIds", checkNull(userGroupIds));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/update-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject addUser(long companyId, boolean autoPassword, String password1, String password2, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String locale, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, JSONArray addresses, JSONArray emailAddresses, JSONArray phones, JSONArray websites, JSONArray announcementsDelivers, boolean sendEmail, JSONObjectWrapper serviceContext) throws Exception {
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
			_params.put("addresses", checkNull(addresses));
			_params.put("emailAddresses", checkNull(emailAddresses));
			_params.put("phones", checkNull(phones));
			_params.put("websites", checkNull(websites));
			_params.put("announcementsDelivers", checkNull(announcementsDelivers));
			_params.put("sendEmail", sendEmail);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/add-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject addUser(long companyId, boolean autoPassword, String password1, String password2, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String locale, String firstName, String middleName, String lastName, long prefixId, long suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, boolean sendEmail, JSONObjectWrapper serviceContext) throws Exception {
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
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateEmailAddress(long userId, String password, String emailAddress1, String emailAddress2, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("password", checkNull(password));
			_params.put("emailAddress1", checkNull(emailAddress1));
			_params.put("emailAddress2", checkNull(emailAddress2));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/update-email-address", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateStatus(long userId, int status, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("status", status);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/update-status", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateStatus(long userId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("status", status);

			_command.put("/user/update-status", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public void deleteUser(long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);

			_command.put("/user/delete-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updatePassword(long userId, String password1, String password2, boolean passwordReset) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("password1", checkNull(password1));
			_params.put("password2", checkNull(password2));
			_params.put("passwordReset", passwordReset);

			_command.put("/user/update-password", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject getCurrentUser() throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_command.put("/user/get-current-user", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateLockoutById(long userId, boolean lockout) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("lockout", lockout);

			_command.put("/user/update-lockout-by-id", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public void addGroupUsers(long groupId, JSONArray userIds, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userIds", checkNull(userIds));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/add-group-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getRoleUserIds(long roleId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);

			_command.put("/user/get-role-user-ids", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public void unsetGroupUsers(long groupId, JSONArray userIds, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userIds", checkNull(userIds));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/user/unset-group-users", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}