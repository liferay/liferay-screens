package com.liferay.mobile.screens.testapp;

import android.support.multidex.MultiDexApplication;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author Javier Gamarra
 */
public class TestApplication extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		LeakCanary.install(this);
	}
}
