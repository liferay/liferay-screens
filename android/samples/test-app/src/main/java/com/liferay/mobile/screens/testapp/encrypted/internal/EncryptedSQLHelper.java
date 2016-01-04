package com.liferay.mobile.screens.testapp.encrypted.internal;


import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCache;
import com.liferay.mobile.screens.cache.ddl.form.DDLFormCache;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCache;
import com.liferay.mobile.screens.context.LiferayScreensContext;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import static com.liferay.mobile.screens.cache.sql.ScreensSQLiteOpenHelper.CREATE_TABLE_CACHED_RESULT;
import static com.liferay.mobile.screens.cache.sql.ScreensSQLiteOpenHelper.CREATE_TABLE_DDL_FORM;
import static com.liferay.mobile.screens.cache.sql.ScreensSQLiteOpenHelper.CREATE_TABLE_DDL_RECORD;
import static com.liferay.mobile.screens.cache.sql.ScreensSQLiteOpenHelper.CREATE_TABLE_DOCUMENT_UPLOAD;
import static com.liferay.mobile.screens.cache.sql.ScreensSQLiteOpenHelper.CREATE_TABLE_USER_PORTRAIT;

/**
 * @author Javier Gamarra
 */
public class EncryptedSQLHelper extends SQLiteOpenHelper {

	public static final int DB_VERSION = 1;
	public static final String SCREENS_CACHE_DB = "ScreensCacheDB";

	public EncryptedSQLHelper() {
		super(LiferayScreensContext.getContext(), SCREENS_CACHE_DB, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String dropTable = "DROP TABLE IF EXISTS ";
		db.execSQL(dropTable + TableCache.TABLE_NAME);
		db.execSQL(dropTable + UserPortraitCache.TABLE_NAME);
		db.execSQL(dropTable + DDLFormCache.TABLE_NAME);
		db.execSQL(dropTable + DDLRecordCache.TABLE_NAME);
		db.execSQL(dropTable + DocumentUploadCache.TABLE_NAME);
		createTable(db);
	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_CACHED_RESULT);
		db.execSQL(CREATE_TABLE_USER_PORTRAIT);
		db.execSQL(CREATE_TABLE_DDL_FORM);
		db.execSQL(CREATE_TABLE_DDL_RECORD);
		db.execSQL(CREATE_TABLE_DOCUMENT_UPLOAD);
	}

}