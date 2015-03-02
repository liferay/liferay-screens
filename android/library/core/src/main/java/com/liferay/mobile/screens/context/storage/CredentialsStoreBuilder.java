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

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.context.User;

/**
 * @author Jose Manuel Navarro
 */
public class CredentialsStoreBuilder {

	public static enum StorageType {

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

	public CredentialsStoreBuilder setAuthentication(BasicAuthentication auth) {
		if (auth == null) {
			throw new IllegalStateException("Authentication cannot be null. Make sure you have a session created");
		}

		_auth = auth;

		return this;
	}

	public CredentialsStoreBuilder setUser(User user) {
		if (user == null) {
			throw new IllegalStateException("User cannot be null. Make sure you have a session created");
		}

		_user = user;

		return this;
	}

	public CredentialsStoreBuilder setContext(Context ctx) {
		if (ctx == null) {
			throw new IllegalStateException("Context cannot be null");
		}

		_context = ctx;

		return this;
	}

	public CredentialsStoreBuilder setStorageType(StorageType storageType) {
		if (_context == null) {
			throw new IllegalStateException("You must set the context before storageType");
		}

		_storageType = storageType;

		return this;
	}

	public CredentialsStore build() {
		if (_context == null) {
			throw new IllegalStateException("You must call setContext() before");
		}

		CredentialsStore credentialsStore;

		switch (_storageType) {
			case SHARED_PREFERENCES:
				credentialsStore = new CredentialsStoreSharedPreferences();
				break;

			case AUTO:
				// TODO right now, we only support Shared Prefs.
				credentialsStore = new CredentialsStoreSharedPreferences();
				break;

			default:
				credentialsStore = new CredentialsStoreVoid();
				break;
		}

		credentialsStore.setContext(_context);

		if (_auth != null) {
			credentialsStore.setAuthentication(_auth);
		}

		if (_user != null) {
			credentialsStore.setUser(_user);
		}

		return credentialsStore;
	}

	private BasicAuthentication _auth;
	private User _user;
	private StorageType _storageType = StorageType.AUTO;
	private Context _context;

}