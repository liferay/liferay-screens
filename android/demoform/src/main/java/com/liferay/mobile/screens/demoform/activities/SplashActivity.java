package com.liferay.mobile.screens.demoform.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.liferay.mobile.screens.demoform.R;

/**
 * @author Javier Gamarra
 */
public class SplashActivity extends Activity {

	private static final int DELAY_MILLIS = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		//TODO change to back and screen orientation aware
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}, DELAY_MILLIS);
	}
}
