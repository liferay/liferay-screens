package com.liferay.mobile.pushnotifications.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.push.bus.BusUtil;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;
import com.squareup.otto.Subscribe;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public abstract class PushActivity extends AppCompatActivity {

	public static final String REGISTRATION_ID = "REGISTRATION_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BusUtil.subscribe(this);

		registerWithPush();
	}

	@Override
	protected void onStop() {
		super.onStop();
		BusUtil.unsubscribe(this);
	}

	private void registerWithPush() {
		try {
			SharedPreferences preferences = getSharedPreferences();
			String senderId = getString(R.string.sender_id);
			if (!preferences.contains(REGISTRATION_ID)) {
				Push.with(SessionContext.createSessionFromCurrentSession()).register(this, senderId);
			}
		}
		catch (Exception e) {
			String message = "Error registering with Google Push";
			LiferayLogger.e(message, e);
			LiferayCrouton.error(this, message, e);
		}
	}

	@Subscribe
	public void recoverRegistrationId(String registrationId) {
		try {
			SharedPreferences preferences = getSharedPreferences();
			preferences.edit().putString(REGISTRATION_ID, registrationId).apply();

			Push.with(SessionContext.createSessionFromCurrentSession()).register(registrationId);
		}
		catch (Exception e) {
			String message = "Error registering with Liferay Push";
			LiferayLogger.e(message, e);
			LiferayCrouton.error(this, message, e);
		}
	}

	@Subscribe
	public void receivePushNotification(JSONObject jsonObject) {
		LiferayLogger.i("Push received! " + jsonObject.toString());
		processPushNotification(jsonObject);
	}

	protected abstract void processPushNotification(JSONObject jsonObject);

	private SharedPreferences getSharedPreferences() {
		return getSharedPreferences("PUSH", Context.MODE_PRIVATE);
	}

}
