package com.liferay.mobile.screens.cache;

import android.Manifest;
import android.app.Application;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

/**
 * @author Javier Gamarra
 */
public class ApplicationCache extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		LiferayScreensContext.init(this);

		RxPermissions.getInstance(this).request(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK)
			.subscribe(new Action1<Boolean>() {
				@Override
				public void call(Boolean result) {
					if (!result) {
						LiferayLogger.e("Can't use cache without network state or wake lock", null);
					}
				}
			});

		//class and type mapping and strategies
//		CacheSQL.getInstance(StorIOSQLite.getInstance(), new CacheStrategyFactory());
	}
}
