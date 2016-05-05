package com.liferay.mobile.screens.auth.login.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface UserConnector {

	JSONObject getUserByEmailAddress(long companyId, String login) throws Exception;

	void getUserById(long userId) throws Exception;

	JSONObject getUserByScreenName(long companyId, String login) throws Exception;

	JSONObject addUser(long companyId, boolean autoPassword, String password, String password1, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String s, String firstName, String middleName, String lastName, int prefixId, int suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, boolean sendEmail, JSONObjectWrapper jsonObjectWrapper) throws Exception;

	JSONObject updatePortrait(long userId, byte[] bitmap) throws Exception;
}
