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
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.cache.executor.Executor;
import com.liferay.mobile.screens.context.storage.CredentialsStorage;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.security.AccessControlException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class SessionContext {

	private static Session currentUserSession;
	private static User currentUser;

	private SessionContext() {
		super();
	}

	public static void logout() {
		currentUserSession = null;
		currentUser = null;
	}

	public static Session createBasicSession(String username, String password) {
		Authentication authentication = new BasicAuthentication(username, password);

		currentUserSession = new SessionImpl(LiferayServerContext.getServer(), authentication);

		return currentUserSession;
	}

	public static Session createOAuthSession(OAuthConfig config) {
		OAuth oAuth = new OAuthAuthentication(config);

		currentUserSession = new SessionImpl(LiferayServerContext.getServer(), oAuth);

		return currentUserSession;
	}

	public static Session createSessionFromCurrentSession() {
		if (currentUserSession == null) {
			throw new IllegalStateException("You need to be logged in to get a session");
		}

		return new SessionImpl(LiferayServerContext.getServer(), currentUserSession.getAuthentication());
	}

	public static boolean isLoggedIn() {
		return currentUserSession != null;
	}

	public static boolean hasUserInfo() {
		return currentUserSession != null && currentUser != null;
	}

	public static Authentication getAuthentication() {
		return (currentUserSession == null) ? null : currentUserSession.getAuthentication();
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User value) {
		currentUser = value;
	}

	public static Long getUserId() {
		return currentUser == null ? null : currentUser.getId();
	}

	public static void storeCredentials(CredentialsStorageBuilder.StorageType storageType) {
		if (!isLoggedIn()) {
			throw new IllegalStateException("You must have a session created to store it");
		}

		CredentialsStorage storage = new CredentialsStorageBuilder().setContext(LiferayScreensContext.getContext())
			.setAuthentication(currentUserSession.getAuthentication())
			.setUser(getCurrentUser())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		storage.storeCredentials();
	}

	public static void removeStoredCredentials(CredentialsStorageBuilder.StorageType storageType) {
		CredentialsStorage storage = new CredentialsStorageBuilder().setContext(LiferayScreensContext.getContext())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		storage.removeStoredCredentials();
	}

	public static void loadStoredCredentials(CredentialsStorageBuilder.StorageType storageType)
		throws IllegalStateException {

		CredentialsStorage storage = new CredentialsStorageBuilder().setContext(LiferayScreensContext.getContext())
			.setStorageType(storageType)
			.build();

		checkIfStorageTypeIsSupported(storageType, storage);

		if (storage.loadStoredCredentials()) {
			currentUserSession = new SessionImpl(LiferayServerContext.getServer(), storage.getAuthentication());
			currentUser = storage.getUser();
		}
	}

	public static void relogin(LoginListener loginListener) {
		if (currentUserSession == null || currentUserSession.getAuthentication() == null) {
			loginListener.onLoginFailure(new AccessControlException("Missing user attributes"));
		}

		refreshUserAttributes(loginListener,
			new SessionImpl(LiferayServerContext.getServer(), currentUserSession.getAuthentication()));
	}

	private static void refreshUserAttributes(final LoginListener loginListener, final Session session) {
		Executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					CurrentUserConnector currentUserConnector =
						ServiceProvider.getInstance().getCurrentUserConnector(session);
					JSONObject jsonObject = currentUserConnector.getCurrentUser();
					if (jsonObject != null) {

						User user = new User(jsonObject);
						currentUser = user;
						loginListener.onLoginSuccess(user);
					} else {
						SessionContext.logout();
						loginListener.onLoginFailure(new AccessControlException("User invalid"));
					}
				} catch (Exception e) {
					loginListener.onLoginFailure(e);
				}
			}
		});
	}

	private static void checkIfStorageTypeIsSupported(CredentialsStorageBuilder.StorageType storageType,
		CredentialsStorage storage) {
		if (storage == null) {
			throw new UnsupportedOperationException("StorageType " + storageType + "is not supported");
		}
	}
}