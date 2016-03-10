package com.liferay.mobile.screens.testapp;

import android.os.Bundle;

import com.liferay.mobile.android.exception.AuthenticationException;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.interactor.LoginBasicInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractor;
import com.liferay.mobile.screens.base.interactor.CustomInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

import org.json.JSONObject;

public class CustomInteractorActivity extends ThemeActivity
	implements LoginListener, CustomInteractorListener<LoginInteractor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_custom_interactor);

		_loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet_custom_interactor);
		_loginScreenlet.setListener(this);
		_loginScreenlet.setCustomInteractorListener(this);
	}

	@Override
	public void onLoginSuccess(User user) {
		info("Login successful!");
	}

	@Override
	public void onLoginFailure(Exception e) {
		error("Error logging in, try with test/test", null);
	}

	@Override
	public LoginInteractor createInteractor(String actionName) {
		return new CustomLoginInteractor(_loginScreenlet.getScreenletId());
	}
	private LoginScreenlet _loginScreenlet;

	private class CustomLoginInteractor extends LoginBasicInteractor {

		public CustomLoginInteractor(int targetScreenletId) {
			super(targetScreenletId);
		}

		@Override
		public void login() throws Exception {
			String username = "test";

			if (username.equals(_login) && username.equals(_password)) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("emailAddress", "test@liferay.com");
				jsonObject.put("userId", "0");
				jsonObject.put("firstName", username);
				jsonObject.put("lastName", username);
				jsonObject.put("screenName", username);

				User fakeUser = new User(jsonObject);

				SessionContext.setCurrentUser(fakeUser);
				SessionContext.createBasicSession(username, username);

				getListener().onLoginSuccess(fakeUser);
			}
			else {
				getListener().onLoginFailure(new AuthenticationException("bad login"));
			}
		}
	}
}
