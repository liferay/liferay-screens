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
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Jose Manuel Navarro
 */
public class CredentialsStoreSharedPreferences implements CredentialsStore {

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
			.putString("username", _auth.getUsername())
			.putString("password", _auth.getPassword())
			.putString("attributes", _user.toString())
			.putString("server", LiferayServerContext.getServer())
			.putLong("groupId", LiferayServerContext.getGroupId())
			.putLong("companyId", LiferayServerContext.getCompanyId())
			.apply();
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

		String server = _sharedPref.getString("server", null);
		String userName = _sharedPref.getString("username", null);
		String password = _sharedPref.getString("password", null);
		String userAttributes = _sharedPref.getString("attributes", null);

		if (server == null || userName == null || password == null || userAttributes == null) {
			// nothing saved
			return false;
		}

		long groupId = _sharedPref.getLong("groupId", 0);
		long companyId = _sharedPref.getLong("companyId", 0);

		if (!server.equals(LiferayServerContext.getServer()) ||
			groupId != LiferayServerContext.getGroupId() ||
			companyId != LiferayServerContext.getCompanyId()) {

			throw new IllegalStateException("Stored credential values are not consistent with current ones");
		}

		_auth = new BasicAuthentication(userName, password);

		try {
			_user = new User(new JSONObject(userAttributes));
		}
		catch (JSONException e) {
			throw new IllegalStateException("Stored user attributes are corrupted");
		}

		return true;
	}

	@Override
	public String getStoreName() {
		try {
			URL url = new URL(LiferayServerContext.getServer());
			return "liferay-screens-" + url.getHost() + "-" + url.getPort();
		}
		catch (MalformedURLException e) {
			LiferayLogger.e("Error parsing url", e);
		}

		return "liferay-screens";
	}

	@Override
	public BasicAuthentication getAuthentication() {
		return _auth;
	}

	@Override
	public void setAuthentication(BasicAuthentication auth) {
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

	private SharedPreferences _sharedPref;
	private BasicAuthentication _auth;
	private User _user;

}