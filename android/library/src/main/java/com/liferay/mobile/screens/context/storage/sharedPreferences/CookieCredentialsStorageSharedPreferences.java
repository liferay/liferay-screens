/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.context.storage.sharedPreferences;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.screens.context.AuthenticationType;

/**
 * @author Jose Manuel Navarro
 */
public class CookieCredentialsStorageSharedPreferences extends BaseCredentialsStorageSharedPreferences {

    private static final String AUTH = "auth";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String COOKIE_HEADER = "cookie_header";
    private static final String SHOULD_HANDLE_EXPIRATION = "should_handle_expiration";
    private static final String COOKIE_EXPIRATION_TIME = "cookie_expiration_time";
    private static final String COOKIE_LAST_REFRESH = "cookie_last_refresh";

    @Override
    protected void storeAuth(Authentication auth) {
        CookieAuthentication cookieAuthentication = (CookieAuthentication) auth;
        getSharedPref().edit()
            .putString(AUTH, AuthenticationType.COOKIE.name())
            .putString(USERNAME, cookieAuthentication.getUsername())
            .putString(PASSWORD, cookieAuthentication.getPassword())
            .putString(AUTH_TOKEN, cookieAuthentication.getAuthToken())
            .putString(COOKIE_HEADER, cookieAuthentication.getCookieHeader())
            .putBoolean(SHOULD_HANDLE_EXPIRATION, cookieAuthentication.shouldHandleExpiration())
            .putInt(COOKIE_EXPIRATION_TIME, cookieAuthentication.getCookieExpirationTime())
            .putLong(COOKIE_LAST_REFRESH, cookieAuthentication.getLastCookieRefresh())
            .apply();
    }

    @Override
    protected Authentication loadAuth() {
        String username = getSharedPref().getString(USERNAME, null);
        String password = getSharedPref().getString(PASSWORD, null);
        String authToken = getSharedPref().getString(AUTH_TOKEN, null);
        String cookieHeader = getSharedPref().getString(COOKIE_HEADER, null);
        boolean shouldHandleExpiration = getSharedPref().getBoolean(SHOULD_HANDLE_EXPIRATION, true);
        int cookieExpirationTime =
            getSharedPref().getInt(COOKIE_EXPIRATION_TIME, CookieAuthentication.COOKIE_EXPIRATION_TIME);
        long lastCookieRefresh = getSharedPref().getLong(COOKIE_LAST_REFRESH, 0);

        if (username == null || password == null || authToken == null || cookieHeader == null) {
            return null;
        }

        return new CookieAuthentication(authToken, cookieHeader, username, password, shouldHandleExpiration,
            cookieExpirationTime, lastCookieRefresh);
    }
}