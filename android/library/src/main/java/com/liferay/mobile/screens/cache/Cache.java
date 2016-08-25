package com.liferay.mobile.screens.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

public class Cache {

	public static boolean destroy(String className) {
		try {
			DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());

			String[] keys = snappyDB.findKeys(className);
			for (String key : keys) {
				snappyDB.del(key);
			}
			snappyDB.close();
		} catch (SnappydbException e) {
			LiferayLogger.e("Error deleting className");
			return false;
		}
		return true;
	}

	public static boolean destroy() {
		try {
			DB snappyDB = DBFactory.open(LiferayScreensContext.getContext());
			snappyDB.destroy();
			snappyDB.close();
			return true;
		} catch (SnappydbException e) {
			LiferayLogger.e("Error destroying DB");
			return false;
		}
	}

	public static void resync() {
		Context context = LiferayScreensContext.getContext();
		ComponentName component = new ComponentName(context.getPackageName(), CacheSyncService.class.getName());
		Intent intent = new Intent();
		intent.setComponent(component);
		context.startService(intent);
	}
}
