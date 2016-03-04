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

package com.liferay.mobile.screens.context;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.oauth.OAuth;
import com.liferay.mobile.android.oauth.OAuthConfig;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.context.storage.CredentialsStorage;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;

/**
 * @author Silvio Santos
 */
public class SessionContext {

	public static void logout() {
		_currentUserSession = null;
		_currentUser = null;
	}

	public static Session createBasicSession(String username, String password) {
		Authentication authentication = new BasicAuthentication(username, password);

		_currentUserSession = new SessionImpl(LiferayServerContext.getServer(), authentication);

		return _currentUserSession;
	}

	public static Session createOAuthSession(OAuthConfig config) {
		OAuth oAuth = new OAuthAuthentication(config);

		_currentUserSession = new SessionImpl(LiferayServerContext.getServer(), oAuth);

		return _currentUserSession;
	}

	public static Session createSessionFromCurrentSession() {
		if (_currentUserSession == null) {
			throw new IllegalStateException("You need to be logged in to get a session");
		}

		return new SessionImpl(LiferayServerContext.getServer(), _currentUserSession.getAuthentication());
	}

	public static boolean isLoggedIn() {
		return _currentUserSession != null;
	}

	public static boolean hasUserInfo() {
		return _currentUserSession != null && _currentUser != null;
	}

	public static Authentication getAuthentication() {
		return (_currentUserSession == null) ? null : _currentUserSession.getAuthentication();
	}

	public static User getCurrentUser() {
		return _currentUser;
	}

	public static Long getUserId() {
		return _currentUser == null ? null : _currentUser.getId();
	}

	public static void setCurrentUser(User value) {
		_currentUser = value;
	}

	public static void storeCredentials(CredentialsStorageBuilder.StorageType storageType) {
		if (!isLoggedIn()) {
			throw new IllegalStateException("You must have a session created to store it");
		}

		CredentialsStorage storage = new CredentialsStorageBuilder()
			.setContext(LiferayScreensContext.getContext())
			.setAuthentication(_currentUserSession.getAuthentication())
			.setUser(getCurrentUser())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		storage.storeCredentials();
	}

	public static void removeStoredCredentials(CredentialsStorageBuilder.StorageType storageType) {
		CredentialsStorage storage = new CredentialsStorageBuilder()
			.setContext(LiferayScreensContext.getContext())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		storage.removeStoredCredentials();
	}

	public static void loadStoredCredentials(CredentialsStorageBuilder.StorageType storageType)
		throws IllegalStateException {

		CredentialsStorage storage = new CredentialsStorageBuilder()
			.setContext(LiferayScreensContext.getContext())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		if (storage.loadStoredCredentials()) {
			_currentUserSession = new SessionImpl(LiferayServerContext.getServer(), storage.getAuthentication());
			_currentUser = storage.getUser();
		}
	}

	private static void checkIfStorageTypeIsSupported(CredentialsStorageBuilder.StorageType storageType, CredentialsStorage storage) {
		if (storage == null) {
			throw new UnsupportedOperationException("StorageType " + storageType + "is not supported");
		}
	}

	private static Session _currentUserSession;
	private static User _currentUser;

}