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

	@Override
	protected void storeAuth(Authentication auth) {
		OAuth oauth = (OAuth) auth;

		getSharedPref()
			.edit()
			.putString("auth", AuthenticationType.OAUTH.name())
			.putString("oauth_consumerKey", oauth.getConsumerKey())
			.putString("oauth_consumerSecret", oauth.getConsumerSecret())
			.putString("oauth_token", oauth.getToken())
			.putString("oauth_tokenSecret", oauth.getTokenSecret())
			.apply();
	}

	@Override
	protected Authentication loadAuth() {
		String consumerKey = getSharedPref().getString("oauth_consumerKey", null);
		String consumerSecret = getSharedPref().getString("oauth_consumerSecret", null);
		String token = getSharedPref().getString("oauth_token", null);
		String tokenSecret = getSharedPref().getString("oauth_tokenSecret", null);

		if (consumerKey == null || consumerSecret == null || token == null || tokenSecret == null) {
			return null;
		}

		return new OAuth(consumerKey, consumerSecret, token, tokenSecret);
	}

}