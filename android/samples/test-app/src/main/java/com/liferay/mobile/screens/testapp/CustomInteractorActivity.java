package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.android.exception.AuthenticationException;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.interactor.BaseLoginInteractor;
import com.liferay.mobile.screens.base.interactor.CustomInteractorListener;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import org.json.JSONObject;

public class CustomInteractorActivity extends ThemeActivity
	implements LoginListener, CustomInteractorListener<BaseLoginInteractor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_custom_interactor);

		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet_custom_interactor);
		loginScreenlet.setListener(this);
		loginScreenlet.setCustomInteractorListener(this);
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
	public BaseLoginInteractor createInteractor(String actionName) {
		return new CustomLoginInteractor();
	}

	private class CustomLoginInteractor extends BaseLoginInteractor {

		@Override
		public BasicThreadEvent execute(Object[] args) throws Exception {

			String login = (String) args[0];
			String password = (String) args[1];

			String username = "test";

			if (username.equals(login) && username.equals(password)) {

				SessionContext.createBasicSession(login, password);

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("emailAddress", "test@liferay.com");
				jsonObject.put("userId", "0");
				jsonObject.put("firstName", username);
				jsonObject.put("lastName", username);
				jsonObject.put("screenName", username);
				return new BasicThreadEvent(jsonObject);
			}
			throw new AuthenticationException("bad login");
		}
	}
}
