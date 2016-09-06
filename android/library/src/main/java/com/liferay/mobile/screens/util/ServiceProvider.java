package com.liferay.mobile.screens.util;

import android.util.Log;
import com.liferay.mobile.screens.context.LiferayServerContext;

/**
 * @author Javier Gamarra
 */
public class ServiceProvider {

	private static ServiceVersionFactory _versionFactory;

	public static ServiceVersionFactory getInstance() {
		synchronized (ServiceProvider.class) {
			if (_versionFactory == null) {
				_versionFactory = createFactory();
			}
		}
		return _versionFactory;
	}

	private static ServiceVersionFactory createFactory() {
		try {
			if (!LiferayServerContext.getVersionFactory().isEmpty()) {
				return (ServiceVersionFactory) Class.forName(
					LiferayServerContext.getVersionFactory()).newInstance();
			}
			if (LiferayServerContext.isLiferay7()) {
				return new ServiceVersionFactory70();
			} else {
				return new ServiceVersionFactory62();
			}
		} catch (Exception e) {
			Log.e("LiferayScreens", "Error creating the instance class");
			return new ServiceVersionFactory62();
		}
	}
}