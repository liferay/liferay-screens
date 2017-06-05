package com.liferay.mobile.screens.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import java.util.Locale;

public class Cache {

	public static final String SEPARATOR = "-";

	private Cache() {
		super();
	}

	public static <E extends CacheEvent> E getObject(final Class<E> aClass, final Long groupId, final Long userId,
		final Locale locale, final String cacheKey) throws Exception {
		return (E) doDatabaseOperation(groupId, userId,
			db -> db.getObject(getFullId(aClass, locale, cacheKey, null), aClass));
	}

	public static <E extends CacheEvent> E getObject(final Class<E> aClass, Long groupId, Long userId, final String key)
		throws Exception {
		return (E) doDatabaseOperation(groupId, userId, db -> db.getObject(key, aClass));
	}

	public static <E extends CacheEvent> void storeObject(final E event, final Integer i) throws Exception {
		doDatabaseOperation(event.getGroupId(), event.getUserId(), db -> {
			db.put(getFullId(event.getClass(), event.getLocale(), event.getCacheKey(), i), event);
			return null;
		});
	}

	public static <E extends CacheEvent> void storeObject(E event) throws Exception {
		storeObject(event, null);
	}

	public static <E extends CacheEvent> void deleteObject(final E event) throws Exception {
		doDatabaseOperation(event.getGroupId(), event.getUserId(), db -> {
			db.del(getFullId(event.getClass(), event.getLocale(), event.getCacheKey(), null));
			return null;
		});
	}

	public static String[] findKeys(final Class childClass, Long groupId, Long userId, final Locale locale,
		final int startRow, final int limit) throws Exception {
		return (String[]) doDatabaseOperation(groupId, userId,
			db -> db.findKeys(getFullId(childClass, locale, null, null), startRow, limit));
	}

	public static boolean destroy(Long groupId, Long userId, final String className) throws Exception {
		return (boolean) doDatabaseOperation(groupId, userId, db -> {
			String[] keys = db.findKeys(className);
			for (String key : keys) {
				db.del(key);
			}
			return true;
		});
	}

	public static boolean destroy(Long groupId, Long userId) throws Exception {
		return (boolean) doDatabaseOperation(groupId, userId, db -> {
			db.destroy();
			return true;
		});
	}

	private static synchronized Object doDatabaseOperation(Long groupId, Long userId, Func1 func1) throws Exception {
		DB snappyDB = null;
		try {
			snappyDB = openDatabase(groupId, userId);
			return func1.call(snappyDB);
		} finally {
			if (snappyDB != null && snappyDB.isOpen()) {
				snappyDB.close();
			}
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
		Context context = LiferayScreensContext.getContext();
		DB db = groupId == null || userId == null ? DBFactory.open(context)
			: DBFactory.open(context, databaseName(groupId, userId));
		LiferayLogger.d("Opening db: " + db.toString());
		return db;
	}

	@NonNull
	private static String getFullId(Class aClass, Locale locale, String cacheKey, Integer row) {
		return aClass.getSimpleName() + SEPARATOR + locale + SEPARATOR + (row == null ? ""
			: String.format(Locale.US, "%05d", row) + SEPARATOR) + (cacheKey == null ? "" : cacheKey);
	}

	private static String databaseName(Long groupId, Long userId) {
		return "DB" + SEPARATOR + (groupId == null ? 0 : groupId) + SEPARATOR + (userId == null ? 0 : userId);
	}

	interface Func1<R> {
		R call(DB db) throws Exception;
	}
}