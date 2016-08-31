package com.liferay.mobile.screens.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import java.util.Locale;

public class Cache {

	public final static String SEPARATOR = "-";

	public static <E extends OfflineEventNew> E getObject(Class aClass, long groupId, long userId, Locale locale,
		String cacheKey) throws Exception {
		DB snappyDB = openDatabase(groupId, userId);
		String id = getFullId(aClass, locale, cacheKey, null);
		E object = (E) snappyDB.getObject(id, aClass);
		snappyDB.close();
		return object;
	}

	public static <E extends OfflineEventNew> E getObject(Class aClass, long groupId, long userId, String key)
		throws Exception {
		DB snappyDB = openDatabase(groupId, userId);
		E object = (E) snappyDB.getObject(key, aClass);
		snappyDB.close();
		return object;
	}

	public static <E extends OfflineEventNew> void storeObject(E event, Integer i) throws Exception {
		DB snappyDB = openDatabase(event.getGroupId(), event.getUserId());
		String id = getFullId(event.getClass(), event.getLocale(), event.getCacheKey(), i);
		snappyDB.put(id, event);
		snappyDB.close();
	}

	public static <E extends OfflineEventNew> void storeObject(E event) throws Exception {
		storeObject(event, null);
	}

	public static String[] findKeys(Class childClass, long groupId, long userId, Locale locale, int startRow, int limit)
		throws Exception {
		DB snappyDB = openDatabase(groupId, userId);
		String elementKey = getFullId(childClass, locale, null, null);
		String[] keys = snappyDB.findKeys(elementKey, startRow, limit);
		snappyDB.close();
		return keys;
	}

	public static boolean destroy(String className) {
		try {
			DB snappyDB = openDatabase(null, null);

			String[] keys = snappyDB.findKeys(className);
			for (String key : keys) {
				snappyDB.del(key);
			}
			snappyDB.close();
		} catch (Exception e) {
			LiferayLogger.e("Error deleting className");
			return false;
		}
		return true;
	}

	public static boolean destroy() {
		try {
			DB snappyDB = openDatabase(null, null);
			snappyDB.destroy();
			return true;
		} catch (Exception e) {
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

	@NonNull
	private static DB openDatabase(Long groupId, Long userId) throws Exception {
		try {
			Context context = LiferayScreensContext.getContext();
			return groupId == null || userId == null ? DBFactory.open(context)
				: DBFactory.open(context, databaseName(groupId, userId));
		} catch (SnappydbException e) {
			throw new Exception("Database exception", e);
		}
	}

	@NonNull
	private static String getFullId(Class aClass, Locale locale, String cacheKey, Integer row) {
		return aClass.getSimpleName() + SEPARATOR +
			locale + SEPARATOR +
			(row == null ? "" : String.format(Locale.US, "%05d", row) + SEPARATOR) +
			(cacheKey == null ? "" : cacheKey);
	}

	private static String databaseName(long groupId, Long userId) {
		return "DB" + SEPARATOR + groupId + SEPARATOR + userId;
	}
}