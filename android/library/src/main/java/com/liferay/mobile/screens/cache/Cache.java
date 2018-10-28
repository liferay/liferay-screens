package com.liferay.mobile.screens.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadEvent;
import com.liferay.mobile.screens.rating.interactor.RatingEvent;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadEvent;
import com.liferay.mobile.screens.util.LiferayLocale;
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
        return (E) doDatabaseOperation(groupId, userId, new Func1<E>() {
            @Override
            public E call(DB db) throws Exception {
                return db.getObject(getFullId(aClass, locale, cacheKey, null), aClass);
            }
        });
    }

    public static <E extends CacheEvent> E getObject(final Class<E> aClass, Long groupId, Long userId, final String key)
        throws Exception {
        return (E) doDatabaseOperation(groupId, userId, new Func1<E>() {
            @Override
            public E call(DB db) throws Exception {
                return db.getObject(key, aClass);
            }
        });
    }

    public static <E extends CacheEvent> void storeObject(final E event, final Integer i) throws Exception {
        doDatabaseOperation(event.getGroupId(), event.getUserId(), new Func1<Void>() {
            @Override
            public Void call(DB db) throws Exception {
                db.put(getFullId(event.getClass(), event.getLocale(), event.getCacheKey(), i), event);
                return null;
            }
        });
    }

    public static <E extends CacheEvent> void storeObject(E event) throws Exception {
        storeObject(event, null);
    }

    public static <E extends CacheEvent> void deleteObject(final E event) throws Exception {
        doDatabaseOperation(event.getGroupId(), event.getUserId(), new Func1<Void>() {
            @Override
            public Void call(DB db) throws Exception {
                db.del(getFullId(event.getClass(), event.getLocale(), event.getCacheKey(), null));
                return null;
            }
        });
    }

    public static String[] findKeys(final Class childClass, Long groupId, Long userId, final Locale locale,
        final int startRow, final int limit) throws Exception {
        return (String[]) doDatabaseOperation(groupId, userId, new Func1<String[]>() {
            @Override
            public String[] call(DB db) throws Exception {
                return db.findKeys(getFullId(childClass, locale, null, null), startRow, limit);
            }
        });
    }

    public static boolean destroy(Long groupId, Long userId, final String className) throws Exception {
        return (boolean) doDatabaseOperation(groupId, userId, new Func1<Boolean>() {
            @Override
            public Boolean call(DB db) throws Exception {
                String[] keys = db.findKeys(className);
                for (String key : keys) {
                    db.del(key);
                }
                return true;
            }
        });
    }

    public static boolean destroy(Long groupId, Long userId) throws Exception {
        return (boolean) doDatabaseOperation(groupId, userId, new Func1<Boolean>() {
            @Override
            public Boolean call(DB db) throws Exception {
                db.destroy();
                return true;
            }
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

    public static void pendingItemsToSync(final PendingItemsToSyncListener pendingItemsToSyncListener) {
        Executor.execute(new Runnable() {
            @Override
            public void run() {
                int ddlItems = pendingItemsByClass(DDLFormEvent.class);
                int commentItems = pendingItemsByClass(CommentEvent.class);
                int ratingItems = pendingItemsByClass(RatingEvent.class);
                int userPortraitItems = pendingItemsByClass(UserPortraitUploadEvent.class);
                int ddlDocumentItems = pendingItemsByClass(DDLFormDocumentUploadEvent.class);

                int totalCount = ddlItems + commentItems + ratingItems + userPortraitItems + ddlDocumentItems;

                pendingItemsToSyncListener.getItemsCount(totalCount);
            }
        });
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

    private static int pendingItemsByClass(Class aClass) {
        Long groupId = LiferayServerContext.getGroupId();
        Long userId = SessionContext.getUserId();
        Locale locale = LiferayLocale.getDefaultLocale();

        int totalCount = 0;
        try {
            String[] keys = Cache.findKeys(aClass, groupId, userId, locale, 0, Integer.MAX_VALUE);
            for (String key : keys) {
                CacheEvent event = Cache.getObject(aClass, groupId, userId, key);
                if (event.isDirty()) {
                    totalCount++;
                }
            }
        } catch (Exception e) {
            LiferayLogger.e("Error in pendingItemsByClass method with class " + aClass.getName(), e);
        }

        return totalCount;
    }

    interface Func1<R> {
        R call(DB db) throws Exception;
    }
}