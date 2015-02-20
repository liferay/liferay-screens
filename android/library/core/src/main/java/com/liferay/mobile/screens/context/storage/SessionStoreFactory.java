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
		AUTO,
		SHARED_PREFERENCES,
		ACCOUNT_MANAGER
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

		_accountManagerPermissionsGranted = SessionStoreAccountManager.isPermissionGranted(_ctx);

		if (storageType == StorageType.ACCOUNT_MANAGER && !_accountManagerPermissionsGranted) {
			throw new IllegalStateException("You need to grant " +
				"GET_ACCOUNTS, AUTHENTICATE_ACCOUNTS and ACCOUNT_MANAGER permissions in your " +
				"manifest in order to store the session in the AccountManager");
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

		switch (_storageType) {
			case ACCOUNT_MANAGER:
				return new SessionStoreAccountManager(_ctx);

			case SHARED_PREFERENCES:
				return new SessionStoreSharedPreferences(_ctx);

			case AUTO:
				if (_accountManagerPermissionsGranted = null) {
					_accountManagerPermissionsGranted =
						SessionStoreAccountManager.isPermissionGranted(_ctx);
				}

				return (_accountManagerPermissionsGranted) ?
					new SessionStoreAccountManager(_ctx) : new SessionStoreSharedPreferences(_ctx);
		}

		return null;
	}

	private BasicAuthentication _auth;
	private User _user;
	private StorageType _storageType = StorageType.AUTO;
	private Context _ctx;

	private Boolean _accountManagerPermissionsGranted;

}