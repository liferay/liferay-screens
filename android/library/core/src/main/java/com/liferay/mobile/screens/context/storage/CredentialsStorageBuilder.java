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

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.oauth.OAuth;
import com.liferay.mobile.screens.base.AbstractFactory;
import com.liferay.mobile.screens.base.FactoryProvider;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BaseCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BasicCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.context.storage.sharedPreferences.OAuthCredentialsStorageSharedPreferences;

/**
 * @author Jose Manuel Navarro
 */
public class CredentialsStorageBuilder {

	public enum StorageType {

		// These values are synced with 'credentialStore' attr
		NONE(0),
		AUTO(1),
		SHARED_PREFERENCES(2);

		public static StorageType valueOf(int value) {
			for (StorageType s : values()) {
				if (s._value == value) {
					return s;
				}
			}
			return NONE;
		}

		public int toInt() {
			return _value;
		}

		StorageType(int value) {
			_value = value;
		}

		private int _value;
	}

	public CredentialsStorageBuilder setAuthentication(Authentication auth) {
		if (auth == null) {
			throw new IllegalStateException("Authentication cannot be null. Make sure you have a session created");
		}

		_auth = auth;

		return this;
	}

	public CredentialsStorageBuilder setUser(User user) {
		if (user == null) {
			throw new IllegalStateException("User cannot be null. Make sure you have a session created");
		}

		_user = user;

		return this;
	}

	public CredentialsStorageBuilder setContext(Context context) {
		if (context == null) {
			throw new IllegalStateException("Context cannot be null");
		}

		_context = context;

		return this;
	}

	public CredentialsStorageBuilder setStorageType(StorageType storageType) {
		if (_context == null) {
			throw new IllegalStateException("You must set the context before storageType");
		}

		_storageType = storageType;

		return this;
	}

	public CredentialsStorage build() {
		if (_context == null) {
			throw new IllegalStateException("You must call setContext() before");
		}

		CredentialsStorage credentialsStorage = createStore();

		credentialsStorage.setContext(_context);

		if (_auth != null) {
			credentialsStorage.setAuthentication(_auth);
		}

		if (_user != null) {
			credentialsStorage.setUser(_user);
		}

		return credentialsStorage;
	}

	protected CredentialsStorage createStore() {

		AbstractFactory instance = FactoryProvider.getInstance();

		if (_auth == null) {
			// figure out the type from stored value
			switch (BaseCredentialsStorageSharedPreferences.getStoredAuthenticationType(_context)) {
				case BASIC:
					return instance.getBasicCredentialsStorageSharedPreferences();
				case OAUTH:
					return instance.getOAuthCredentialsStorageSharedPreferences();
				default:
					return new CredentialsStorageVoid();
			}
		}
		else {
			if (_auth instanceof BasicAuthentication) {
				return instance.getBasicCredentialsStorageSharedPreferences();
			}
			else if (_auth instanceof OAuth) {
				return instance.getOAuthCredentialsStorageSharedPreferences();
			}
			else {
				throw new IllegalStateException("Authentication type is not supported");
			}
		}
	}

	private Authentication _auth;
	private User _user;
	//TODO implement other storage mechanisms
	private StorageType _storageType = StorageType.AUTO;
	private Context _context;

}