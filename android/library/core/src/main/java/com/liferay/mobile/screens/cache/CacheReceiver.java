package com.liferay.mobile.screens.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * @author Javier Gamarra
 */
public class CacheReceiver extends WakefulBroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {

		ComponentName component = new ComponentName(context.getPackageName(), CacheSyncService.class.getName());
		intent.setComponent(component);
		startWakefulService(context, intent);
	}
}
