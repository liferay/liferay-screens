package com.liferay.mobile.screens.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;

/**
 * @author Silvio Santos
 */
public class MainActivity extends Activity
	implements LoginListener {

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);

		setContentView(R.layout.activity_main);

		LoginScreenlet loginScreenlet = (LoginScreenlet)findViewById(
			R.id.login_screenlet);

		loginScreenlet.setListener(this);
	}

	@Override
	public void onLoginFailure(Exception e) {
		String message = "Failed to login: " + e.getMessage();

		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onLoginSuccess() {
		Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
	}

}
