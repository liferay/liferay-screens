package com.liferay.mobile.screens.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.legacy.content.WakefulBroadcastReceiver;

/**
 * @author Javier Gamarra
 */
public abstract class AbstractPushReceiver<P extends AbstractPushService> extends WakefulBroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        ComponentName component = new ComponentName(context.getPackageName(), getPushServiceClass().getName());
        intent.setComponent(component);
        startWakefulService(context, intent);
    }

    @NonNull
    protected abstract Class<P> getPushServiceClass();
}
