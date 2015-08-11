package com.liferay.mobile.screens.cache;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liferay.mobile.screens.context.LiferayScreensContext;

/**
 * @author Javier Gamarra
 */
public class ScreensSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final int DB_VERSION = 13;
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
		db.execSQL("DROP TABLE IF EXISTS " + CachedResult.TABLE_NAME);
		createTable(db);
	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_CACHED_RESULT);
	}

	private static final java.lang.String CREATE_TABLE_CACHED_RESULT = "CREATE TABLE "
			+ CachedResult.TABLE_NAME + "("
			+ CachedResult.ID + " TEXT NOT NULL, "
			+ CachedResult.CACHED_TYPE + " TEXT NOT NULL, "
			+ CachedResult.CONTENT + " TEXT, "
			+ CachedResult.DATE + " LONG NOT NULL "
			+ ");";
}
