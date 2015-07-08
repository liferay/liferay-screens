package com.liferay.mobile.pushnotifications.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.liferay.mobile.push.Push;

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
