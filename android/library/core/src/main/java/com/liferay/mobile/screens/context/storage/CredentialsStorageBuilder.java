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

		StorageType(int value) {
			_value = value;
		}

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
		CredentialsStorage credentialsStorage;
		Class credentialStoreClass;

		if (_auth == null) {
			// figure out the type from stored value
			switch (BaseCredentialsStorageSharedPreferences.getStoredAuthenticationType(_context)) {
				case BASIC:
					credentialStoreClass = BasicCredentialsStorageSharedPreferences.class;
					break;
				case OAUTH:
					credentialStoreClass = OAuthCredentialsStorageSharedPreferences.class;
					break;
				default:
					credentialStoreClass = CredentialsStorageVoid.class;
			}
		}
		else {
			if (_auth instanceof BasicAuthentication) {
				credentialStoreClass = BasicCredentialsStorageSharedPreferences.class;
			}
			else if (_auth instanceof OAuth) {
				credentialStoreClass = OAuthCredentialsStorageSharedPreferences.class;
			}
			else {
				throw new IllegalStateException("Authentication type is not supported");
			}
		}

		try {
			if (StorageType.SHARED_PREFERENCES.equals(_storageType)) {
				credentialsStorage = (CredentialsStorage) credentialStoreClass.newInstance();
			}
			else if (StorageType.AUTO.equals(_storageType)) {
				// TODO right now, we only support Shared Prefs.
				credentialsStorage = (CredentialsStorage) credentialStoreClass.newInstance();
			}
			else {
				credentialsStorage = new CredentialsStorageVoid();
			}

		}
		catch (Exception e) {
			throw new IllegalStateException("Store can't be instantiated");
		}

		return credentialsStorage;
	}

	private Authentication _auth;
	private User _user;
	private StorageType _storageType = StorageType.AUTO;
	private Context _context;

}