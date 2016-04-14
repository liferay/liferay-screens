package com.liferay.mobile.screens.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public abstract class PushScreensActivity extends AppCompatActivity
	implements Push.OnSuccess, Push.OnPushNotification, Push.OnFailure {

	public static final String PUSH_PREFERENCES = "PUSH_PREFERENCES";
	public static final String VERSION_CODE = "VERSION_CODE";
	public static final String TOKEN = "TOKEN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		registerWithPush();
	}

	@Override
	public void onSuccess(final JSONObject jsonObject) {
		try {
			String token = jsonObject.getString("token");

			SharedPreferences preferences = getSharedPreferences();
			SharedPreferences.Editor editor = preferences.edit();
			editor.putString(TOKEN, token);
			editor.putInt(VERSION_CODE, getVersionCode());
			editor.apply();
		}
		catch (Exception e) {
			String message = "Error registering with Liferay Push";
			LiferayLogger.e(message, e);
		}
		_push.onPushNotification(this);
	}

	@Override
	public void onFailure(final Exception e) {
		onErrorRegisteringPush("", e);
	}

	@Override
	public void onPushNotification(final JSONObject pushNotification) {
		onPushNotificationReceived(pushNotification);
	}

	@Override
	protected void onStop() {
		super.onStop();

		unsubscribeFromBuses();
	}

	protected void registerWithPush() {
		try {

			Session session = SessionContext.isLoggedIn() ?
				SessionContext.createSessionFromCurrentSession() : getDefaultSession();
			_push = Push.with(session);

			SharedPreferences preferences = getSharedPreferences();

			boolean alreadyRegistered = preferences.contains(TOKEN);
			boolean appUpdated = getVersionCode() != preferences.getInt(VERSION_CODE, 0);

			if (!alreadyRegistered || appUpdated || BuildConfig.DEBUG) {
				_push.onSuccess(this).onFailure(this).register(this, getSenderId());
			}
			else {
				_push.onPushNotification(this);
			}
		}
		catch (Exception e) {
			String message = "Error registering with Google Push";
			LiferayLogger.e(message, e);
			onErrorRegisteringPush(message, e);
		}
	}

	protected abstract Session getDefaultSession();

	protected void unsubscribeFromBuses() {
//		if (_push != null) {
//			FIXME !
//			_push.unsubscribe();
//		}
	}

	protected abstract void onPushNotificationReceived(JSONObject jsonObject);

	protected abstract void onErrorRegisteringPush(final String message, final Exception e);

	protected abstract String getSenderId();

	private int getVersionCode() throws PackageManager.NameNotFoundException {
		PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		return pInfo.versionCode;
	}

	private SharedPreferences getSharedPreferences() {
		return getSharedPreferences(PUSH_PREFERENCES, Context.MODE_PRIVATE);
	}

	private Push _push;

}