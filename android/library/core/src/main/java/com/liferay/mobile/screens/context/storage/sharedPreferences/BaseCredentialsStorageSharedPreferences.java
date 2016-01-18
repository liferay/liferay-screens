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

import android.content.Context;
import android.content.SharedPreferences;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorage;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author Jose Manuel Navarro
 */
public abstract class BaseCredentialsStorageSharedPreferences implements CredentialsStorage {

	@Override
	public void storeCredentials() {
		if (_sharedPref == null) {
			throw new IllegalStateException("You need to set the context");
		}
		if (_auth == null) {
			throw new IllegalStateException("You need to be logged in to store the session");
		}
		if (_user == null) {
			throw new IllegalStateException("You need to set user attributes to store the session");
		}

		_sharedPref
			.edit()
			.putString("attributes", _user.toString())
			.putString("server", LiferayServerContext.getServer())
			.putLong("groupId", LiferayServerContext.getGroupId())
			.putLong("companyId", LiferayServerContext.getCompanyId())
			.apply();

		storeAuth(_auth);
	}

	@Override
	public void removeStoredCredentials() {
		if (_sharedPref == null) {
			throw new IllegalStateException("You need to set the context");
		}

		_sharedPref
			.edit()
			.clear()
			.apply();

		_user = null;
		_auth = null;
	}

	@Override
	public boolean loadStoredCredentials() throws IllegalStateException {
		if (_sharedPref == null) {
			throw new IllegalStateException("You need to set the context");
		}

		String userAttributes = _sharedPref.getString("attributes", null);
		String server = _sharedPref.getString("server", null);
		long groupId = _sharedPref.getLong("groupId", 0);
		long companyId = _sharedPref.getLong("companyId", 0);

		_auth = loadAuth();

		if (_auth == null || server == null || userAttributes == null
			|| groupId == 0 || companyId == 0) {
			// nothing saved
			return false;
		}

		if (!server.equals(LiferayServerContext.getServer()) ||
			groupId != LiferayServerContext.getGroupId() ||
			companyId != LiferayServerContext.getCompanyId()) {

			_auth = null;
			_user = null;

			throw new IllegalStateException("Stored credential values are not consistent with current ones");
		}

		try {
			_user = new User(new JSONObject(userAttributes));
		}
		catch (JSONException e) {
			throw new IllegalStateException("Stored user attributes are corrupted");
		}

		return true;
	}

	public static String getStoreName() {
		try {
			URL url = new URL(LiferayServerContext.getServer());
			return "liferay-screens-" + url.getHost() + "-" + url.getPort();
		}
		catch (MalformedURLException e) {
			LiferayLogger.e("Error parsing url", e);
		}

		return "liferay-screens";
	}

	public static AuthenticationType getStoredAuthenticationType(Context context) {
		SharedPreferences sharedPref = context.getSharedPreferences(
			getStoreName(), Context.MODE_PRIVATE);
		return AuthenticationType.valueOf(sharedPref.getString("auth", AuthenticationType.VOID.name()));
	}

	@Override
	public Authentication getAuthentication() {
		return _auth;
	}

	@Override
	public void setAuthentication(Authentication auth) {
		_auth = auth;
	}

	@Override
	public User getUser() {
		return _user;
	}

	@Override
	public void setUser(User user) {
		_user = user;
	}

	@Override
	public void setContext(Context context) {
		if (context == null) {
			throw new IllegalStateException("Context cannot be null");
		}
		_sharedPref = context.getSharedPreferences(getStoreName(), Context.MODE_PRIVATE);
	}

	protected SharedPreferences getSharedPref() {
		return _sharedPref;
	}

	protected abstract void storeAuth(Authentication auth);

	protected abstract Authentication loadAuth();

	private SharedPreferences _sharedPref;
	private Authentication _auth;
	private User _user;

}