package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 */
public class ReloginActivity extends ThemeActivity implements LoginListener {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relogin);

		if (SessionContext.isLoggedIn()) {
			_userName = (TextView) findViewById(R.id.user_name);
			_userName.setText(SessionContext.getCurrentUser().getLastName());
		}
	}

	public void relogin(View view) {
		SessionContext.relogin(this);
	}

	@Override
	public void onLoginSuccess(final User user) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_userName.setText(user.getLastName());
				info("Relogin successful!");
			}
		});
	}

	@Override
	public void onLoginFailure(Exception e) {
		error("Error relogin", e);
	}

	public void change(View view) {
		if (SessionContext.isLoggedIn()) {
			final User user = SessionContext.getCurrentUser();
			user.getAttributes().put("lastName", "EXAMPLE_LASTNAME");
			_userName.setText(user.getLastName());
		}
	}

	private TextView _userName;
}
