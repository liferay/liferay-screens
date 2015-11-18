package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liferay.mobile.android.exception.AuthenticationException;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.interactor.LoginBasicInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractor;
import com.liferay.mobile.screens.base.interactor.CustomInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import org.json.JSONObject;

public class CustomInteractorActivity extends AppCompatActivity
	implements LoginListener, CustomInteractorListener<LoginInteractor> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_custom_interactor);

		_loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet_custom_interactor);
		_loginScreenlet.setListener(this);
		_loginScreenlet.addCustomInteractor(this);
	}

	@Override
	public void onLoginSuccess(User user) {
		LiferayCrouton.info(this, "Login!");
	}

	@Override
	public void onLoginFailure(Exception e) {

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

				SessionContext.setLoggedUser(fakeUser);
				SessionContext.createBasicSession(username, username);

				getListener().onLoginSuccess(fakeUser);
			}
			else {
				getListener().onLoginFailure(new AuthenticationException("bad login"));
			}
		}
	}
}
