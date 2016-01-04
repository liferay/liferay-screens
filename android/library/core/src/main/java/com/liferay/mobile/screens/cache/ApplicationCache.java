package com.liferay.mobile.screens.cache;

import android.app.Application;

import com.liferay.mobile.screens.context.LiferayScreensContext;

/**
 * @author Javier Gamarra
 */
public class ApplicationCache extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		LiferayScreensContext.init(this);

		// SQLiteDatabase.loadLibs(this);

		// class and type mapping and strategies
		// CacheSQL.getInstance(StorIOSQLite.getInstance(), new CacheStrategyFactory());
	}
}
