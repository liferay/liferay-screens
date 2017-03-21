package com.liferay.mobile.screens.demoform.app;

import android.app.Application;
import com.liferay.mobile.screens.context.SessionContext;

public class MyApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		SessionContext.createBasicSession("javier.gamarra@liferay.com", "Im0kfnG3");
	}
}
