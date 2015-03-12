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

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.oauth.OAuthConfig;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.OAuthAuthentication;
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
			.putString("attributes", _user.toString())
			.putString("server", LiferayServerContext.getServer())
			.putLong("groupId", LiferayServerContext.getGroupId())
			.putLong("companyId", LiferayServerContext.getCompanyId())
			.putString("consumerKey", LiferayServerContext.getConsumerKey())
			.putString("consumerSecret", LiferayServerContext.getConsumerSecret())
			.apply();

		storeAuthentication();
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
		String token = _sharedPref.getString("token", null);
		String tokenSecret = _sharedPref.getString("tokenSecret", null);
		String userAttributes = _sharedPref.getString("attributes", null);

		if (server == null || userAttributes == null ||
			((userName == null && password == null) ||
				(token == null || tokenSecret == null))) {
			// nothing saved
			return false;
		}

		long groupId = _sharedPref.getLong("groupId", 0);
		long companyId = _sharedPref.getLong("companyId", 0);
		String consumerKey = _sharedPref.getString("consumerKey", "");
		String consumerSecret = _sharedPref.getString("consumerSecret", "");

		if (!server.equals(LiferayServerContext.getServer()) ||
			groupId != LiferayServerContext.getGroupId() ||
			companyId != LiferayServerContext.getCompanyId() ||
			!consumerKey.equals(LiferayServerContext.getConsumerKey()) ||
			!consumerSecret.equals(LiferayServerContext.getConsumerSecret())) {

			throw new IllegalStateException("Stored credential values are not consistent with current ones");
		}

		if (_auth instanceof OAuthAuthentication) {
			_auth = new OAuthAuthentication(new OAuthConfig(server, consumerKey, consumerSecret));
		}
		else {
			_auth = new BasicAuthentication(userName, password);
		}

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

	private void storeAuthentication() {
		if (_auth instanceof BasicAuthentication) {
			BasicAuthentication basicAuthentication = (BasicAuthentication) _auth;
			_sharedPref
				.edit()
				.putString("username", basicAuthentication.getUsername())
				.putString("password", basicAuthentication.getPassword())
				.apply();
		}
		else {
			OAuthAuthentication oAuth = (OAuthAuthentication) _auth;
			_sharedPref
				.edit()
				.putString("token", oAuth.getToken())
				.putString("tokenSecret", oAuth.getTokenSecret())
				.apply();
		}
	}

	private SharedPreferences _sharedPref;
	private Authentication _auth;
	private User _user;

}