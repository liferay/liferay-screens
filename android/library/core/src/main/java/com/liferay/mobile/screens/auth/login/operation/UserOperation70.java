package com.liferay.mobile.screens.auth.login.operation;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v70.user.UserService;
import com.liferay.mobile.screens.auth.forgotpassword.operation.ForgotPasswordOperation;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserOperation70 implements UserOperation, ForgotPasswordOperation, CurrentUserOperation {

	public UserOperation70(Session session) {
		_userService = new UserService(session);
	}

	@Override
	public void getUserByEmailAddress(long companyId, String emailAddress) throws Exception {
		_userService.getUserByEmailAddress(companyId, emailAddress);
	}

	@Override
	public void getUserById(long userId) throws Exception {
		_userService.getUserById(userId);
	}

	@Override
	public void getUserByScreenName(long companyId, String screenName) throws Exception {
		_userService.getUserByScreenName(companyId, screenName);
	}

	@Override
	public void addUser(long companyId, boolean autoPassword, String password, String password1, boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String s, String firstName, String middleName, String lastName, int prefixId, int suffixId, boolean male, int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, boolean sendEmail, JSONObjectWrapper jsonObjectWrapper) throws Exception {
		_userService.addUser(companyId, autoPassword, password, password1, autoScreenName, screenName, emailAddress, facebookId, openId, s, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds, sendEmail, jsonObjectWrapper);
	}

	@Override
	public JSONObject updatePortrait(long userId, byte[] bitmap) throws Exception {
		return _userService.updatePortrait(userId, bitmap);
	}

	@Override
	public void sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception {
		_userService.sendPasswordByEmailAddress(companyId, emailAddress);
	}

	public void sendPasswordByScreenName(long companyId, String screenName) throws Exception {
		_userService.sendPasswordByScreenName(companyId, screenName);
	}

	@Override
	public void sendPasswordByUserId(long userId) throws Exception {
		_userService.sendPasswordByUserId(userId);
	}

	@Override
	public void getCurrentUser() throws Exception {
		_userService.getCurrentUser();
	}

	private final UserService _userService;
}
