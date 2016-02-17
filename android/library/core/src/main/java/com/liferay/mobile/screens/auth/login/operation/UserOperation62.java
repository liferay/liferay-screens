package com.liferay.mobile.screens.auth.login.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.user.UserService;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserOperation62 implements UserOperation {

	public UserOperation62(Session session) {
		_userService = new UserService(session);
	}

	@Override
	public void getUserByEmailAddress(long companyId, String login) throws Exception {
		_userService.getUserByEmailAddress(companyId, login);
	}

	@Override
	public void getUserById(long userId) throws Exception {
		_userService.getUserById(userId);
	}

	@Override
	public void getUserByScreenName(long companyId, String login) throws Exception {
		_userService.getUserByScreenName(companyId, login);
	}

	@Override
	public void addUser(long companyId, boolean autoPassword, String password, String password1, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String s, String firstName, String middleName, String lastName, int prefixId, int suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, boolean sendEmail, JSONObjectWrapper serviceContext) throws Exception {
		_userService.addUser(companyId, autoPassword, password, password1, autoScreenName, screenName, emailAddress, facebookId, openId, s, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds, sendEmail, serviceContext);
	}

	@Override
	public JSONObject updatePortrait(long userId, byte[] bitmap) throws Exception {
		return _userService.updatePortrait(userId, bitmap);
	}

	private final UserService _userService;
}
