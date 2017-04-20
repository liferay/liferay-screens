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

	public static final String AUTH = "auth";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	@Override
	protected void storeAuth(Authentication auth) {
		CookieAuthentication cookieAuthentication = (CookieAuthentication) auth;
		getSharedPref().edit()
			.putString(AUTH, AuthenticationType.COOKIE.name())
			.putString(USERNAME, cookieAuthentication.getUsername())
			.putString(PASSWORD, cookieAuthentication.getPassword())
			.apply();
	}

	@Override
	protected Authentication loadAuth() {
		String userName = getSharedPref().getString(USERNAME, null);
		String password = getSharedPref().getString(PASSWORD, null);

		if (userName == null || password == null) {
			return null;
		}

		return new CookieAuthentication("", "", userName, password);
	}
}