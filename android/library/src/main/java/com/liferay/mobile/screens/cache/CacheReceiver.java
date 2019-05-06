package com.liferay.mobile.screens.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.legacy.content.WakefulBroadcastReceiver;

import static com.liferay.mobile.screens.auth.login.LoginScreenlet.LOGIN_SUCCESSFUL;

/**
 * @author Javier Gamarra
 */
public class CacheReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (LOGIN_SUCCESSFUL.equals(action)) {
            ComponentName component = new ComponentName(context.getPackageName(), CacheSyncService.class.getName());
            intent.setComponent(component);
            startWakefulService(context, intent);
        }
    }
}
