package com.liferay.mobile.screens.base;

import com.liferay.mobile.screens.context.storage.CredentialsStorage;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BasicCredentialsStorageSharedPreferences;

/**
 * @author Javier Gamarra
 */
public interface AbstractFactory {

    BasicCredentialsStorageSharedPreferences getBasicCredentialsStorageSharedPreferences();

    CredentialsStorage getCookieCredentialsStorageSharedPreferences();

    CredentialsStorage getOAuth2CredentialsStorageSharedPreferences();
}
