package com.liferay.mobile.screens.cache.sql;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liferay.mobile.screens.cache.ddl.documentupload.DocumentUploadCache;
import com.liferay.mobile.screens.cache.ddl.form.DDLFormCache;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.cache.userportrait.UserPortraitCache;
import com.liferay.mobile.screens.context.LiferayScreensContext;

/**
 * @author Javier Gamarra
 */
public class ScreensSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final int DB_VERSION = 1;
	public static final String SCREENS_CACHE_DB = "ScreensCacheDB";

	public ScreensSQLiteOpenHelper() {
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

	private static final String CREATE_TABLE_CACHED_RESULT = "CREATE TABLE "
		+ TableCache.TABLE_NAME + "("
		+ TableCache.ID + " TEXT NOT NULL, "
		+ TableCache.TYPE + " TEXT NOT NULL, "
		+ TableCache.CONTENT + " TEXT, "
		+ TableCache.DATE + " LONG NOT NULL, "
		+ TableCache.USER_ID + " LONG NOT NULL, "
		+ TableCache.GROUP_ID + " LONG NOT NULL, "
		+ TableCache.LOCALE + " TEXT NOT NULL, "
		+ TableCache.SENT + " INTEGER NULL );";

	private static final String CREATE_TABLE_USER_PORTRAIT = "CREATE TABLE "
		+ UserPortraitCache.TABLE_NAME + "("
		+ UserPortraitCache.USER_ID + " LONG NOT NULL, "
		+ UserPortraitCache.MALE + " INTEGER NOT NULL, "
		+ UserPortraitCache.PORTRAIT_ID + " LONG NOT NULL, "
		+ UserPortraitCache.UUID + " TEXT NOT NULL )";

	private static final String CREATE_TABLE_DDL_FORM = "CREATE TABLE "
		+ DDLFormCache.TABLE_NAME + "("
		+ DDLFormCache.RECORD_SET_ID + " LONG NOT NULL, "
		+ DDLFormCache.RECORD_ID + " LONG NOT NULL, "
		+ DDLFormCache.STRUCTURE_ID + " LONG NOT NULL, "
		+ DDLFormCache.LOCALE + " TEXT NULL, "
		+ DDLFormCache.CONTENT + " TEXT NOT NULL );";

	private static final String CREATE_TABLE_DDL_RECORD = "CREATE TABLE "
		+ DDLRecordCache.TABLE_NAME + "("
		+ DDLRecordCache.RECORD_ID + " LONG NOT NULL, "
		+ DDLRecordCache.RECORD_SET_ID + " LONG NOT NULL, "
		+ DDLRecordCache.STRUCTURE_ID + " LONG NOT NULL, "
		+ DDLRecordCache.LOCALE + " TEXT NULL, "
		+ DDLRecordCache.CONTENT + " TEXT NOT NULL, "
		+ DDLRecordCache.GROUP_ID + " LONG NULL, "
		+ DDLRecordCache.SENT + " INTEGER NOT NULL );";

	private static final String CREATE_TABLE_DOCUMENT_UPLOAD = "CREATE TABLE "
		+ DocumentUploadCache.TABLE_NAME + "("
		+ DocumentUploadCache.PATH + " TEXT NOT NULL, "
		+ DocumentUploadCache.GROUP_ID + " LONG NOT NULL, "
		+ DocumentUploadCache.REPOSITORY_ID + " LONG NOT NULL, "
		+ DocumentUploadCache.FOLDER_ID + " LONG NOT NULL, "
		+ DocumentUploadCache.FILE_PREFIX + " TEXT NOT NULL, "
		+ DocumentUploadCache.SENT + " INTEGER NOT NULL );";
}