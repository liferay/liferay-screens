package com.liferay.mobile.screens.cache;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.List;

public class LiferayCacheSQL<E extends CachedContent> implements LiferayCache<E> {

	public LiferayCacheSQL() {
		_storIOSQLite = DefaultStorIOSQLite.builder()
				.sqliteOpenHelper(new ScreensSQLiteOpenHelper())
				.addTypeMapping(CachedResult.class, SQLiteTypeMapping.<CachedResult>builder()
						.putResolver(new CachedResultStorIOSQLitePutResolver())
						.getResolver(new CachedResultStorIOSQLiteGetResolver())
						.deleteResolver(new CachedResultStorIOSQLiteDeleteResolver())
						.build())
				.build();
	}

	@Override
	public E recover(CachedType cachedType, String id) {
		List<E> results = queryByTypeAndId(cachedType, id);
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public void store(List<E> objects) {
		for (E object : objects) {
			store(object);
		}
	}

	@Override
	public void store(final E object) {
		new Thread(new Runnable() {
			@Override
			public void run() {

				if (object.getCachedType() == CachedType.DDL_LIST) {


				} else {
					PutResult result = _storIOSQLite.put().object(object).prepare().executeAsBlocking();
				}



//				object.setCachedResultId(cachedResult.getId());
//				PutResult result2 = _storIOSQLite.put().object(object).prepare().executeAsBlocking();
//
//
//				result2.insertedId();
				//FIXME notify
			}
		}).start();
	}

	@Override
	public boolean hasCachedContents(CachedType cachedType, String id) {
		return !queryByTypeAndId(cachedType, id).isEmpty();
	}

	@NonNull
	private List queryByTypeAndId(CachedType cachedType, String id) {
		return _storIOSQLite.get()
				.listOfObjects(CachedResult.class)
				.withQuery(Query.builder()
						.table(CachedResult.TABLE_NAME)
						.where(CachedResult.ID + " = ? AND " + CachedResult.CACHED_TYPE + " = ?")
						.whereArgs(id, cachedType)
						.build())
				.prepare().executeAsBlocking();
	}

	private final DefaultStorIOSQLite _storIOSQLite;
}
