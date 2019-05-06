package com.liferay.mobile.screens.auth.login.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.UserService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserConnector62 implements UserConnector {

    private final UserService userService;

    public UserConnector62(Session session) {
        userService = new UserService(session);
    }

    @Override
    public JSONObject getUserByEmailAddress(long companyId, String login) throws Exception {
        return userService.getUserByEmailAddress(companyId, login);
    }

    @Override
    public JSONObject getUserById(long userId) throws Exception {
        return userService.getUserById(userId);
    }

    @Override
    public JSONObject getUserByScreenName(long companyId, String login) throws Exception {
        return userService.getUserByScreenName(companyId, login);
    }

    @Override
    public JSONObject addUser(long companyId, boolean autoPassword, String password, String password1,
        boolean autoScreenName, String screenName, String emailAddress, long facebookId, String openId, String s,
        String firstName, String middleName, String lastName, int prefixId, int suffixId, boolean male,
        int birthdayMonth, int birthdayDay, int birthdayYear, String jobTitle, JSONArray groupIds,
        JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupIds, boolean sendEmail,
        JSONObjectWrapper serviceContext) throws Exception {
        return userService.addUser(companyId, autoPassword, password, password1, autoScreenName, screenName,
            emailAddress, facebookId, openId, s, firstName, middleName, lastName, prefixId, suffixId, male,
            birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds, roleIds, userGroupIds,
            sendEmail, serviceContext);
    }

    public JSONObject updateUser(long userId, String oldPassword, String newPassword1, String newPassword2,
        boolean passwordReset, String reminderQueryQuestion, String reminderQueryAnswer, String screenName,
        String emailAddress, long facebookId, String openId, String languageId, String timeZoneId, String greeting,
        String comments, String firstName, String middleName, String lastName, int prefixId, int suffixId, boolean male,
        int birthdayMonth, int birthdayDay, int birthdayYear, String smsSn, String aimSn, String facebookSn,
        String icqSn, String jabberSn, String msnSn, String mySpaceSn, String skypeSn, String twitterSn, String ymSn,
        String jobTitle, JSONArray groupIds, JSONArray organizationIds, JSONArray roleIds, JSONArray userGroupRoles,
        JSONArray userGroupIds, JSONObjectWrapper serviceContext) throws Exception {
        return userService.updateUser(userId, oldPassword, newPassword1, newPassword2, passwordReset,
            reminderQueryQuestion, reminderQueryAnswer, screenName, emailAddress, facebookId, openId, languageId,
            timeZoneId, greeting, comments, firstName, middleName, lastName, prefixId, suffixId, male, birthdayMonth,
            birthdayDay, birthdayYear, smsSn, aimSn, facebookSn, icqSn, jabberSn, msnSn, mySpaceSn, skypeSn, twitterSn,
            ymSn, jobTitle, groupIds, organizationIds, roleIds, userGroupRoles, userGroupIds, serviceContext);
    }

    @Override
    public JSONObject updatePortrait(long userId, byte[] bitmap) throws Exception {
        return userService.updatePortrait(userId, bitmap);
    }
}
