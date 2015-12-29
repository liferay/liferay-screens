package com.liferay.mobile.screens.cache;

import android.app.Application;

import com.liferay.mobile.screens.context.LiferayScreensContext;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * @author Javier Gamarra
 */
public class ApplicationCache extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		SQLiteDatabase.loadLibs(this);

		LiferayScreensContext.init(this);


		//class and type mapping and strategies
//		CacheSQL.getInstance(StorIOSQLite.getInstance(), new CacheStrategyFactory());
	}
}
