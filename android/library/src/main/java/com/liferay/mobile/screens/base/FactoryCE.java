package com.liferay.mobile.screens.base;

import com.liferay.mobile.screens.context.storage.CredentialsStorage;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BasicCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.context.storage.sharedPreferences.CookieCredentialsStorageSharedPreferences;

/**
 * @author Javier Gamarra
 */
public class FactoryCE implements AbstractFactory {

	@Override
	public BasicCredentialsStorageSharedPreferences getBasicCredentialsStorageSharedPreferences() {
		return new BasicCredentialsStorageSharedPreferences();
	}

	@Override
	public CredentialsStorage getCookieCredentialsStorageSharedPreferences() {
		return new CookieCredentialsStorageSharedPreferences();
	}
}
