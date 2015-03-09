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
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.context.storage.CredentialsStore;
import com.liferay.mobile.screens.context.storage.CredentialsStoreBuilder;

/**
 * @author Silvio Santos
 */
public class SessionContext {

	public static void clearSession() {
		_session = null;
		_loggedUser = null;
	}

	public static Session createSession(String username, String password) {
		Authentication authentication = new BasicAuthentication(username, password);

		_session = new SessionImpl(LiferayServerContext.getServer(), authentication);

		return _session;
	}

	public static Session createSessionFromCurrentSession() {
		if (_session == null) {
			throw new IllegalStateException("You need to be logged in to get a session");
		}

		BasicAuthentication basicAuth = (BasicAuthentication) _session.getAuthentication();

		return createSession(basicAuth.getUsername(), basicAuth.getPassword());
	}

	public static boolean hasSession() {
		return _session != null;
	}

	public static BasicAuthentication getAuthentication() {
		return (_session == null) ? null : (BasicAuthentication) _session.getAuthentication();
	}

	public static User getLoggedUser() {
		return _loggedUser;
	}

	public static void setLoggedUser(User value) {
		_loggedUser = value;
	}

	public static void storeSession(CredentialsStoreBuilder.StorageType storageType) {
		if (!hasSession()) {
			throw new IllegalStateException("You must have a session created to store it");
		}

		CredentialsStore storage = new CredentialsStoreBuilder()
			.setContext(LiferayScreensContext.getContext())
			.setAuthentication((BasicAuthentication) _session.getAuthentication())
			.setUser(getLoggedUser())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		storage.storeCredentials();
	}

	public static void removeStoredSession(CredentialsStoreBuilder.StorageType storageType) {
		CredentialsStore storage = new CredentialsStoreBuilder()
			.setContext(LiferayScreensContext.getContext())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		storage.removeStoredCredentials();
	}

	public static void loadSessionFromStore(CredentialsStoreBuilder.StorageType storageType)
		throws IllegalStateException {

		CredentialsStore storage = new CredentialsStoreBuilder()
			.setContext(LiferayScreensContext.getContext())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		if (storage.loadStoredCredentials()) {
			_session = new SessionImpl(
				LiferayServerContext.getServer(), storage.getAuthentication());
			_loggedUser = storage.getUser();
		}
	}

	private static void checkIfStorageTypeIsSupported(CredentialsStoreBuilder.StorageType storageType, CredentialsStore storage) {
		if (storage == null) {
			throw new UnsupportedOperationException("StorageType " + storageType + "is not supported");
		}
	}

	private static Session _session;
	private static User _loggedUser;

}