package com.liferay.mobile.screens.cache.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCache;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCacheStorIOSQLiteDeleteResolver;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCacheStorIOSQLiteGetResolver;
import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCacheStorIOSQLitePutResolver;
import com.liferay.mobile.screens.cache.ddl.form.DDLFormCache;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.liferay.mobile.screens.cache.ddl.form.resolvers.DDLFormDeleteResolver;
import com.liferay.mobile.screens.cache.ddl.form.resolvers.DDLFormGetResolver;
import com.liferay.mobile.screens.cache.ddl.form.resolvers.DDLFormPutResolver;
import com.liferay.mobile.screens.cache.ddl.form.resolvers.DDLRecordDeleteResolver;
import com.liferay.mobile.screens.cache.ddl.form.resolvers.DDLRecordGetResolver;
import com.liferay.mobile.screens.cache.ddl.form.resolvers.DDLRecordPutResolver;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.cache.tablecache.TableCacheStorIOSQLiteDeleteResolver;
import com.liferay.mobile.screens.cache.tablecache.TableCacheStorIOSQLiteGetResolver;
import com.liferay.mobile.screens.cache.tablecache.TableCacheStorIOSQLitePutResolver;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCache;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCacheStorIOSQLiteDeleteResolver;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCacheStorIOSQLiteGetResolver;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCacheStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public class StorIOSQLite {

	@NonNull
	public static DatabaseResult querySet(Object object) {
		PutResult putResult = getInstance()
			.put()
			.object(object)
			.prepare()
			.executeAsBlocking();

		if (putResult.wasInserted() || putResult.wasUpdated()) {
			return new DatabaseResult(object, putResult.insertedId());
		}
		else {
			return new DatabaseResult(false);
		}
	}

	public static List queryGet(Class tableClass, String tableName, String orderBy, String where, Object... queryArgs) {
		return getInstance()
			.get()
			.listOfObjects(tableClass)
			.withQuery(
				Query.builder()
					.table(tableName)
					.where(where)
					.whereArgs(queryArgs)
					.build())
			.prepare()
			.executeAsBlocking();
	}

	public static int queryDelete(String tableName, String where, Object... queryArgs) {
		DeleteResult deleteResult = getInstance()
			.delete()
			.byQuery(
				DeleteQuery.builder()
					.table(tableName)
					.where(where)
					.whereArgs(queryArgs)
					.build())
			.prepare()
			.executeAsBlocking();
		return deleteResult.numberOfRowsDeleted();
	}

	public static synchronized void initWithCustomStorIOSQLite(DefaultStorIOSQLite defaultStorIOSQLite) {
		_storIOSQLite = defaultStorIOSQLite;
	}

	public static synchronized DefaultStorIOSQLite getInstance() {
		if (_storIOSQLite == null) {
			_storIOSQLite = StorIOSQLite.getStorIO();
		}
		return _storIOSQLite;
	}

	private StorIOSQLite() {
		super();
	}

	@NonNull
	private static DefaultStorIOSQLite getStorIO() {
		return DefaultStorIOSQLite.builder()
			.sqliteOpenHelper(new ScreensSQLiteOpenHelper())
			.addTypeMapping(TableCache.class, SQLiteTypeMapping.<TableCache>builder()
				.putResolver(new TableCacheStorIOSQLitePutResolver())
				.getResolver(new TableCacheStorIOSQLiteGetResolver())
				.deleteResolver(new TableCacheStorIOSQLiteDeleteResolver())
				.build())
			.addTypeMapping(DDLRecordCache.class, SQLiteTypeMapping.<DDLRecordCache>builder()
				.putResolver(new DDLRecordPutResolver())
				.getResolver(new DDLRecordGetResolver())
				.deleteResolver(new DDLRecordDeleteResolver())
				.build())
			.addTypeMapping(DDLFormCache.class, SQLiteTypeMapping.<DDLFormCache>builder()
				.putResolver(new DDLFormPutResolver())
				.getResolver(new DDLFormGetResolver())
				.deleteResolver(new DDLFormDeleteResolver())
				.build())
			.addTypeMapping(UserPortraitCache.class, SQLiteTypeMapping.<UserPortraitCache>builder()
				.putResolver(new UserPortraitCacheStorIOSQLitePutResolver())
				.getResolver(new UserPortraitCacheStorIOSQLiteGetResolver())
				.deleteResolver(new UserPortraitCacheStorIOSQLiteDeleteResolver())
				.build())
			.addTypeMapping(DocumentUploadCache.class, SQLiteTypeMapping.<DocumentUploadCache>builder()
				.putResolver(new DocumentUploadCacheStorIOSQLitePutResolver())
				.getResolver(new DocumentUploadCacheStorIOSQLiteGetResolver())
				.deleteResolver(new DocumentUploadCacheStorIOSQLiteDeleteResolver())
				.build())
			.build();
	}

	private static DefaultStorIOSQLite _storIOSQLite;
}
