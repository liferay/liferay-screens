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

package com.liferay.mobile.screens.context.storage;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

import org.json.JSONObject;

import static android.Manifest.permission.ACCOUNT_MANAGER;
import static android.Manifest.permission.AUTHENTICATE_ACCOUNTS;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * @author Jose Manuel Navarro
 */
public class SessionStoreSharedPreferences implements SessionStorage {

	public SessionStoreSharedPreferences(Context ctx) {
		_sharedPref = ctx.getSharedPreferences(getStoreName(), Context.MODE_PRIVATE);
	}

	@Override
	public void storeSession() {
		BasicAuthentication auth = SessionContext.getAuthentication();

		if (auth == null) {
			throw new IllegalStateException("You need to be logged in to store the session");
		}
		if (SessionContext.getUser() == null) {
			throw new IllegalStateException("You need to set user attributes to store the session");
		}

		SharedPreferences.Editor editor = _sharedPref.edit();

		editor.putString("username", auth.getUsername());
		editor.putString("password", auth.getPassword());
		editor.putString("attributes", SessionContext.getUser().toString());
		editor.putString("server", LiferayServerContext.getServer());
		editor.putLong("groupId", LiferayServerContext.getGroupId());
		editor.putLong("companyId", LiferayServerContext.getCompanyId());

		editor.commit();
	}

	@Override
	public String getStoreName() {
		return "liferay-screens-" + LiferayServerContext.getServer();
	}

	private static SharedPreferences _sharedPref;

}