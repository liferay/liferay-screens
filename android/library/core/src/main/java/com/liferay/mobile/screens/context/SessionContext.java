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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.context.storage.SessionStorage;
import com.liferay.mobile.screens.context.storage.SessionStoreSharedPreferences;

import org.json.JSONObject;

import static android.Manifest.permission.ACCOUNT_MANAGER;
import static android.Manifest.permission.AUTHENTICATE_ACCOUNTS;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * @author Silvio Santos
 */
public class SessionContext {

	public static enum StorageType {
		AUTO,
		SHARED_PREFERENCES,
		ACCOUNT_MANAGER
	}

	public static void clearSession() {
		_session = null;
		_user = null;
	}

	public static Session createSession(String username, String password) {
		Authentication authentication = new BasicAuthentication(
			username, password);

		_session = new SessionImpl(
			LiferayServerContext.getServer(), authentication);

		return _session;
	}

	public static Session createSessionFromCurrentSession() {
		if (_session == null) {
			throw new IllegalStateException(
				"You need to be logged in to get a session");
		}

		BasicAuthentication basicAuth =
			(BasicAuthentication) _session.getAuthentication();

		return createSession(basicAuth.getUsername(), basicAuth.getPassword());
	}

	public static boolean hasSession() {
		return _session != null;
	}

	public static void setUserAttributes(JSONObject userAttributes) {
		_user = new User(userAttributes);
	}

	public static BasicAuthentication getAuthentication() {
		return (_session == null) ? null : (BasicAuthentication) _session.getAuthentication();
	}

	public static User getUser() {
		return _user;
	}

	public static String getStoreName() {
		return "liferay-screens-" + LiferayServerContext.getServer();
	}

	public static void storeSession(StorageType storageType) {
		if (LiferayScreensContext.getContext() == null) {
			throw new IllegalStateException("LiferayScreensContext has to be init");
		}
		if (_session == null) {
			throw new IllegalStateException("You need to be logged in to store the session");
		}
		if (_user == null) {
			throw new IllegalStateException("You need to set user attributes to store the session");
		}

		SessionStorage storage;

		boolean permissionGranted = isAccountManagerPermissionGranted();

		if (storageType == StorageType.ACCOUNT_MANAGER
			|| (storageType == StorageType.AUTO && permissionGranted)) {

			if (!permissionGranted) {
				throw new IllegalStateException("You need to grant " +
					"GET_ACCOUNTS, AUTHENTICATE_ACCOUNTS and ACCOUNT_MANAGER permissions in your " +
					"manifest in order to store the session in the AccountManager");
			}

			// TODO store in AccountManager
			storage = null;
		}
		else {
			storage = new SessionStoreSharedPreferences(
				LiferayScreensContext.getContext(), getStoreName());
		}

		storage.storeSession();
	}

	protected static boolean isAccountManagerPermissionGranted() {
		PackageManager packageMgr = LiferayScreensContext.getContext().getPackageManager();
		String packageName = LiferayScreensContext.getContext().getPackageName();

		int getAccounts = packageMgr.checkPermission(GET_ACCOUNTS, packageName);
		int authenticateAccounts = packageMgr.checkPermission(AUTHENTICATE_ACCOUNTS, packageName);
		int manageAccounts = packageMgr.checkPermission(ACCOUNT_MANAGER, packageName);

		return getAccounts == PERMISSION_GRANTED
			&& authenticateAccounts == PERMISSION_GRANTED
			&& manageAccounts == PERMISSION_GRANTED;
	}

	private static Session _session;
	private static User _user;

}