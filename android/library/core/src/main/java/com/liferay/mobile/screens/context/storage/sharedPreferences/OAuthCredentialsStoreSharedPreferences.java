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
import com.liferay.mobile.android.oauth.OAuth;
import com.liferay.mobile.screens.context.AuthenticationType;


/**
 * @author Jose Manuel Navarro
 */
public class OAuthCredentialsStoreSharedPreferences extends BaseCredentialsStoreSharedPreferences {

	public static final String AUTH = "auth";
	public static final String OAUTH_CONSUMER_KEY = "oauth_consumerKey";
	public static final String OAUTH_CONSUMER_SECRET = "oauth_consumerSecret";
	public static final String OAUTH_TOKEN = "oauth_token";
	public static final String OAUTH_TOKEN_SECRET = "oauth_tokenSecret";

	@Override
	protected void storeAuth(Authentication auth) {
		OAuth oauth = (OAuth) auth;

		getSharedPref()
			.edit()
			.putString(AUTH, AuthenticationType.OAUTH.name())
			.putString(OAUTH_CONSUMER_KEY, oauth.getConfig().getConsumerKey())
			.putString(OAUTH_CONSUMER_SECRET, oauth.getConfig().getConsumerSecret())
			.putString(OAUTH_TOKEN, oauth.getConfig().getToken())
			.putString(OAUTH_TOKEN_SECRET, oauth.getConfig().getTokenSecret())
			.apply();
	}

	@Override
	protected Authentication loadAuth() {
		String consumerKey = getSharedPref().getString(OAUTH_CONSUMER_KEY, null);
		String consumerSecret = getSharedPref().getString(OAUTH_CONSUMER_SECRET, null);
		String token = getSharedPref().getString(OAUTH_TOKEN, null);
		String tokenSecret = getSharedPref().getString(OAUTH_TOKEN_SECRET, null);

		if (consumerKey == null || consumerSecret == null || token == null || tokenSecret == null) {
			return null;
		}

		return new OAuth(consumerKey, consumerSecret, token, tokenSecret);
	}

}