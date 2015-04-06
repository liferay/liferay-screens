package com.liferay.mobile.screens.bankofwesteros;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import static com.liferay.mobile.screens.bankofwesteros.TourActivity.TOUR_VISITED;
import static com.liferay.mobile.screens.bankofwesteros.TourActivity.WESTEROS_PREFERENCES;

/**
 * @author Javier Gamarra
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		final Class destination = getDestinationActivity();

		//TODO change to back and screen orientation aware
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, destination));
			}
		}, DELAY_MILLIS);

	}

	private Class getDestinationActivity() {
		SharedPreferences preferences = getSharedPreferences(WESTEROS_PREFERENCES, MODE_PRIVATE);
		boolean tourVisited = preferences.contains(TOUR_VISITED) &&
			preferences.getBoolean(TOUR_VISITED, false);
		return tourVisited ? MainActivity.class : TourActivity.class;
	}

	private static final int DELAY_MILLIS = 2000;
}
