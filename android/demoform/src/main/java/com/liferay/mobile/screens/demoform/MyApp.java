package com.liferay.mobile.screens.demoform;

import android.app.Application;
import com.liferay.mobile.screens.context.SessionContext;

public class MyApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		SessionContext.createBasicSession("test@liferay.com", "test");
	}
}
