package com.liferay.mobile.screens.testapp.encrypted;

import android.support.annotation.NonNull;

import com.liferay.mobile.screens.cache.sql.StorIOSQLite;
import com.liferay.mobile.screens.testapp.encrypted.internal.EncryptedDefaultStorIOSQLite;
import com.liferay.mobile.screens.testapp.encrypted.internal.EncryptedSQLHelper;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;

import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class EncryptedStorIOSQLiteHelper {

	@NonNull
	public static EncryptedDefaultStorIOSQLite getEncryptedStorIOSQLite(String password) {
		Map<Class<?>, SQLiteTypeMapping<?>> typeMappingMap = StorIOSQLite.getDefaultTypeMappings();
		return new EncryptedDefaultStorIOSQLite(new EncryptedSQLHelper(), typeMappingMap, password);
	}

}
