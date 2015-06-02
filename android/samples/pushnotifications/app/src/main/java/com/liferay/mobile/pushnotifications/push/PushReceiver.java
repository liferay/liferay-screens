package com.liferay.mobile.pushnotifications.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * @author Javier Gamarra
 */
public class PushReceiver extends WakefulBroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		ComponentName component = new ComponentName(context.getPackageName(), PushService.class.getName());
		intent.setComponent(component);
		startWakefulService(context, intent);
	}
}
