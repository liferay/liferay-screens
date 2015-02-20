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
public class SessionStoreFactory {

	public static enum StorageType {

		// There values ar synch-ed with 'credentialStore' attr
		AUTO(1),
		SHARED_PREFERENCES(2),
		ACCOUNT_MANAGER(3);

		StorageType(int value) {
			_value = value;
		}

		public static StorageType valueOf(int value) {
			for (StorageType s : StorageType.values()) {
				if (s._value == value) {
					return s;
				}
			}

			return AUTO;
		}

		private int _value;
	}

	public SessionStoreFactory setAuthentication(BasicAuthentication auth) {
		if (auth == null) {
			throw new IllegalStateException("Authentication cannot be null. Make sure you have a session created");
		}

		_auth = auth;

		return this;
	}

	public SessionStoreFactory setUser(User user) {
		if (user == null) {
			throw new IllegalStateException("User cannot be null. Make sure you have a session created");
		}

		_user = user;

		return this;
	}

	public SessionStoreFactory setContext(Context ctx) {
		if (ctx == null) {
			throw new IllegalStateException("Context cannot be null");
		}

		_ctx = ctx;

		return this;
	}

	public SessionStoreFactory setStorageType(StorageType storageType) {
		if (_ctx == null) {
			throw new IllegalStateException("You must set the context before storageType");
		}

		if (storageType == StorageType.ACCOUNT_MANAGER) {
			_accountManagerPermissionsGranted = SessionStoreAccountManager.isPermissionGranted(_ctx);

			if (!_accountManagerPermissionsGranted) {
				throw new IllegalStateException("You need to grant " +
					"GET_ACCOUNTS, AUTHENTICATE_ACCOUNTS and MANAGE_ACCOUNTS permissions in your " +
					"manifest in order to store the session in the AccountManager");
			}
		}

		_storageType = storageType;

		return this;
	}

	public SessionStore create() {
		if (_ctx == null) {
			throw new IllegalStateException("You must call setContext() before");
		}
		if (_auth == null) {
			throw new IllegalStateException("You must call setAuthentication() before");
		}
		if (_user == null) {
			throw new IllegalStateException("You must call setUser() before");
		}

		SessionStore sessionStore;

		switch (_storageType) {
			case ACCOUNT_MANAGER:
				sessionStore = new SessionStoreAccountManager();
				break;

			case SHARED_PREFERENCES:
				sessionStore = new SessionStoreSharedPreferences();
				break;

			default:
				if (_accountManagerPermissionsGranted = null) {
					_accountManagerPermissionsGranted =
						SessionStoreAccountManager.isPermissionGranted(_ctx);
				}

				sessionStore = (_accountManagerPermissionsGranted) ?
					new SessionStoreAccountManager() : new SessionStoreSharedPreferences();

				break;
		}

		sessionStore.setContext(_ctx);
		sessionStore.setAuthentication(_auth);
		sessionStore.setUser(_user);

		return sessionStore;
	}

	private BasicAuthentication _auth;
	private User _user;
	private StorageType _storageType = StorageType.AUTO;
	private Context _ctx;

	private Boolean _accountManagerPermissionsGranted;

}