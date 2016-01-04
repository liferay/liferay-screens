package com.liferay.mobile.screens.testapp.encrypted;

import com.liferay.mobile.screens.cache.ApplicationCache;
import com.liferay.mobile.screens.cache.sql.StorIOSQLite;
import com.liferay.mobile.screens.testapp.encrypted.internal.EncryptedDefaultStorIOSQLite;

import net.sqlcipher.database.SQLiteDatabase;

/**
 * @author Javier Gamarra
 */
public class EncryptedApplication extends ApplicationCache {

	@Override
	public void onCreate() {
		super.onCreate();

		SQLiteDatabase.loadLibs(this);

		String password = "password";

		EncryptedDefaultStorIOSQLite encryptedStorIOSQLite =
			EncryptedStorIOSQLiteHelper.getEncryptedStorIOSQLite(password);
		StorIOSQLite.initWithCustomStorIOSQLite(encryptedStorIOSQLite);
	}
}
