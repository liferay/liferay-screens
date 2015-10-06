package com.liferay.mobile.screens.bankofwesteros.views;

import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class UpdateUserInteractorImpl {

	//TODO to move to a screenlet

	public void saveUser(String firstName, String lastName, final String emailAddress, final String newPassword, JSONObjectCallback callback) {
		Session sessionFromCurrentSession = SessionContext.createSessionFromCurrentSession();
		sessionFromCurrentSession.setCallback(callback);

		User user = SessionContext.getLoggedUser();

		UserService userService = new UserService(sessionFromCurrentSession);
		try {
			JSONArray array = new JSONArray();

			//FIXME example implementation!
			userService.updateUser(user.getInt("userId"), "test2", newPassword, newPassword, false,
				user.getString("reminderQueryQuestion"), user.getString("reminderQueryAnswer"),
				user.getString("screenName"), emailAddress, user.getInt("facebookId"),
				user.getString("openId"), user.getString("languageId"), "", user.getString("greeting"),
				user.getString("comments"), firstName, user.getString("middleName"),
				lastName, 0, 0, true, 1, 1, 1900, user.getString("emailAddress"),
				"", "", "", "", "", "", "", "", "", user.getString("jobTitle"),
				array, array, array, array, array, null);
		}
		catch (Exception e) {
			LiferayLogger.e("Error parsing JSON", e);
		}
	}
}
