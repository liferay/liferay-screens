package com.liferay.mobile.screens.testapp;

import com.liferay.mobile.screens.cache.ApplicationCache;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author Javier Gamarra
 */
public class TestApplication extends ApplicationCache {

	@Override
	public void onCreate() {
		super.onCreate();

		LeakCanary.install(this);
	}
}
