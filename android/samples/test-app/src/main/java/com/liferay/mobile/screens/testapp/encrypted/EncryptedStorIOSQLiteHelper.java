package com.liferay.mobile.screens.testapp.encrypted;

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
import com.liferay.mobile.screens.testapp.encrypted.internal.EncryptedDefaultStorIOSQLite;
import com.liferay.mobile.screens.testapp.encrypted.internal.EncryptedSQLHelper;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class EncryptedStorIOSQLiteHelper {

	@NonNull
	public static EncryptedDefaultStorIOSQLite getEncryptedStorIOSQLite(String password) {
		Map<Class<?>, SQLiteTypeMapping<?>> typeMappingMap = new HashMap<>();
		typeMappingMap.put(TableCache.class, SQLiteTypeMapping.<TableCache>builder()
			.putResolver(new TableCacheStorIOSQLitePutResolver())
			.getResolver(new TableCacheStorIOSQLiteGetResolver())
			.deleteResolver(new TableCacheStorIOSQLiteDeleteResolver())
			.build());
		typeMappingMap.put(DDLRecordCache.class, SQLiteTypeMapping.<DDLRecordCache>builder()
			.putResolver(new DDLRecordPutResolver())
			.getResolver(new DDLRecordGetResolver())
			.deleteResolver(new DDLRecordDeleteResolver())
			.build());
		typeMappingMap.put(DDLFormCache.class, SQLiteTypeMapping.<DDLFormCache>builder()
			.putResolver(new DDLFormPutResolver())
			.getResolver(new DDLFormGetResolver())
			.deleteResolver(new DDLFormDeleteResolver())
			.build());
		typeMappingMap.put(UserPortraitCache.class, SQLiteTypeMapping.<UserPortraitCache>builder()
			.putResolver(new UserPortraitCacheStorIOSQLitePutResolver())
			.getResolver(new UserPortraitCacheStorIOSQLiteGetResolver())
			.deleteResolver(new UserPortraitCacheStorIOSQLiteDeleteResolver())
			.build());
		typeMappingMap.put(DocumentUploadCache.class, SQLiteTypeMapping.<DocumentUploadCache>builder()
			.putResolver(new DocumentUploadCacheStorIOSQLitePutResolver())
			.getResolver(new DocumentUploadCacheStorIOSQLiteGetResolver())
			.deleteResolver(new DocumentUploadCacheStorIOSQLiteDeleteResolver())
			.build());

		return new EncryptedDefaultStorIOSQLite(new EncryptedSQLHelper(), typeMappingMap, password);
	}

}
