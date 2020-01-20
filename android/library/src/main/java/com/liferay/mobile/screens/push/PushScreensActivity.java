package com.liferay.mobile.screens.push;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.context.LiferayPortalVersion;
import com.liferay.mobile.screens.context.LiferayServerContext;
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
    public static final String USER = "USER";
    private Push push;

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
            editor.putString(USER, getCurrentUser());
            editor.putInt(VERSION_CODE, getVersionCode());
            editor.apply();
        } catch (Exception e) {
            String message = "Error registering with Liferay Push";
            LiferayLogger.e(message, e);
        }
        push.onPushNotification(this);
    }

    @Override
    public void onFailure(final Exception e) {
        LiferayLogger.e("Error registering push", e);
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

            Session session =
                SessionContext.isLoggedIn() ? SessionContext.createSessionFromCurrentSession() : getDefaultSession();
            push = Push.with(session);
            LiferayPortalVersion portalVersion = LiferayServerContext.getPortalVersion();
            push.withPortalVersion(portalVersion.getVersion());

            SharedPreferences preferences = getSharedPreferences();

            boolean userAlreadyRegistered = isUserAlreadyRegistered(preferences);

            boolean appUpdated = getVersionCode() != preferences.getInt(VERSION_CODE, 0);

            if (!userAlreadyRegistered || appUpdated || BuildConfig.DEBUG) {
                push.onSuccess(this).onFailure(this).register(this, getSenderId());
            } else {
                push.onPushNotification(this);
            }
        } catch (Exception e) {
            String message = "Error registering with Google Push";
            LiferayLogger.e(message, e);
            onErrorRegisteringPush(message, e);
        }
    }

    protected boolean isUserAlreadyRegistered(SharedPreferences preferences) {
        return preferences.contains(USER) && preferences.getString(USER, "").equals(getCurrentUser());
    }

    @Nullable
    protected String getCurrentUser() {
        if (SessionContext.isLoggedIn()) {

            Session session = SessionContext.createSessionFromCurrentSession();
            Authentication authentication = session.getAuthentication();

            if (authentication instanceof BasicAuthentication) {
                return ((BasicAuthentication) authentication).getUsername();
            } else {
                LiferayLogger.e("Can't obtain a valid user from this authentication: " + authentication);
                return "-";
            }
        } else {
            Session defaultSession = getDefaultSession();
            LiferayLogger.e("Using default session for push... is this the right behaviour?");
            return ((BasicAuthentication) defaultSession.getAuthentication()).getUsername();
        }
    }

    protected abstract Session getDefaultSession();

    protected void unsubscribeFromBuses() {
        //		if (push != null) {
        //			FIXME !
        //			push.unsubscribe();
        //		}
    }

    protected abstract void onPushNotificationReceived(JSONObject jsonObject);

    protected abstract void onErrorRegisteringPush(String message, Exception e);

    protected abstract String getSenderId();

    private int getVersionCode() throws PackageManager.NameNotFoundException {
        return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
    }

    private SharedPreferences getSharedPreferences() {
        return getSharedPreferences(PUSH_PREFERENCES, Context.MODE_PRIVATE);
    }
}