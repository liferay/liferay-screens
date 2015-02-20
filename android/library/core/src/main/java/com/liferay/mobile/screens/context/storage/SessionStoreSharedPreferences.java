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

import android.content.Context;
import android.content.SharedPreferences;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;

/**
 * @author Jose Manuel Navarro
 */
public class SessionStoreSharedPreferences implements SessionStore {

	@Override
	public void storeSession() {
		if (_sharedPref == null) {
			throw new IllegalStateException("You need to set the context");
		}
		if (_auth == null) {
			throw new IllegalStateException("You need to be logged in to store the session");
		}
		if (_user == null) {
			throw new IllegalStateException("You need to set user attributes to store the session");
		}

		SharedPreferences.Editor editor = _sharedPref.edit();

		editor.putString("username", _auth.getUsername());
		editor.putString("password", _auth.getPassword());
		editor.putString("attributes", _user.toString());
		editor.putString("server", LiferayServerContext.getServer());
		editor.putLong("groupId", LiferayServerContext.getGroupId());
		editor.putLong("companyId", LiferayServerContext.getCompanyId());

		editor.commit();
	}

	@Override
	public String getStoreName() {
		return "liferay-screens-" + LiferayServerContext.getServer();
	}

	@Override
	public void setAuthentication(BasicAuthentication auth) {
		_auth = auth;
	}

	@Override
	public void setUser(User user) {
		_user = user;
	}

	@Override
	public void setContext(Context ctx) {
		if (ctx == null) {
			throw new IllegalStateException("Context cannot be null");
		}
		_sharedPref = ctx.getSharedPreferences(getStoreName(), Context.MODE_PRIVATE);
	}

	private SharedPreferences _sharedPref;
	private BasicAuthentication _auth;
	private User _user;

}