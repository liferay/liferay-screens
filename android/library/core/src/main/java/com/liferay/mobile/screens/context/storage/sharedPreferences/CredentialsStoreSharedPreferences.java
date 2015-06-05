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
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.context.AuthenticationType;

/**
 * @author Jose Manuel Navarro
 */
public class CredentialsStoreSharedPreferences extends BaseCredentialsStoreSharedPreferences {

	@Override
	protected void storeAuth(Authentication auth) {
		BasicAuthentication basicAuth = (BasicAuthentication) auth;
		getSharedPref()
			.edit()
			.putString("auth", AuthenticationType.BASIC.name())
			.putString("username", basicAuth.getUsername())
			.putString("password", basicAuth.getPassword())
			.apply();
	}

	@Override
	protected Authentication loadAuth() {
		String userName = getSharedPref().getString("username", null);
		String password = getSharedPref().getString("password", null);

		if (userName == null || password == null) {
			return null;
		}

		return new BasicAuthentication(userName, password);
	}

}