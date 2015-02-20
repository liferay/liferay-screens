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
import android.content.pm.PackageManager;

import com.liferay.mobile.screens.context.LiferayServerContext;

import static android.Manifest.permission.ACCOUNT_MANAGER;
import static android.Manifest.permission.AUTHENTICATE_ACCOUNTS;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * @author Jose Manuel Navarro
 */
public class SessionStoreAccountManager implements SessionStore {

	public SessionStoreAccountManager(Context ctx) {
		_ctx = ctx;
	}

	@Override
	public void storeSession() {
	}

	@Override
	public String getStoreName() {
		return "liferay-screens-" + LiferayServerContext.getServer();
	}

	public static boolean isPermissionGranted(Context ctx) {
		PackageManager packageMgr = ctx.getPackageManager();
		String packageName = ctx.getPackageName();

		int getAccounts = packageMgr.checkPermission(GET_ACCOUNTS, packageName);
		int authenticateAccounts = packageMgr.checkPermission(AUTHENTICATE_ACCOUNTS, packageName);
		int manageAccounts = packageMgr.checkPermission(ACCOUNT_MANAGER, packageName);

		return getAccounts == PERMISSION_GRANTED
			&& authenticateAccounts == PERMISSION_GRANTED
			&& manageAccounts == PERMISSION_GRANTED;
	}


	private Context _ctx;

}