package com.liferay.mobile.screens.bankofwesteros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author Javier Gamarra
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		//todo change to back and screen orientation aware
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
			}
		}, DELAY_MILLIS);
	}

	private static final int DELAY_MILLIS = 2000;
}
