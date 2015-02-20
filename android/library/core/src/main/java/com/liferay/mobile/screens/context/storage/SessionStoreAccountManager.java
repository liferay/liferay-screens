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

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;

import java.net.MalformedURLException;
import java.net.URL;

import static android.Manifest.permission.MANAGE_ACCOUNTS;
import static android.Manifest.permission.AUTHENTICATE_ACCOUNTS;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * @author Jose Manuel Navarro
 */
public class SessionStoreAccountManager implements SessionStore {

	@Override
	public void storeSession() {
		if (_accountManager == null) {
			throw new IllegalStateException("You need to set the context");
		}
		if (_auth == null) {
			throw new IllegalStateException("You need to be logged in to store the session");
		}
		if (_user == null) {
			throw new IllegalStateException("You need to set user attributes to store the session");
		}

		Account account = getUserAccount(_auth.getUsername());
		if (account == null) {
			Bundle userData = new Bundle();
			userData.putString("attributes", _user.toString());
			userData.putString("server", LiferayServerContext.getServer());
			userData.putLong("groupId", LiferayServerContext.getGroupId());
			userData.putLong("companyId", LiferayServerContext.getCompanyId());

			_accountManager.addAccountExplicitly(
				new Account(_auth.getUsername(), getStoreName()), _auth.getPassword(), userData);
		}
		else {
			_accountManager.setUserData(account, "attributes", _user.toString());
			_accountManager.setUserData(account, "server", LiferayServerContext.getServer());
			_accountManager.setUserData(account, "groupId", String.valueOf(LiferayServerContext.getGroupId()));
			_accountManager.setUserData(account, "companyId", String.valueOf(LiferayServerContext.getCompanyId()));

			_accountManager.setPassword(account, _auth.getPassword());
		}
	}

	@Override
	public String getStoreName() {
		try {
			URL url = new URL(LiferayServerContext.getServer());
			return "liferay-screens-" + url.getHost() + "-" + url.getPort();
		}
		catch (MalformedURLException e) {
		}

		return "liferay-screens";
	}

	@Override
	public void setAuthentication(BasicAuthentication auth) {
		_auth = auth;
	}

	@Override
	public void setUser(User user) {
		_user = user;
	}

	@Override
	public void setContext(Context ctx) {
		_accountManager = AccountManager.get(ctx);

	}

	public static boolean isPermissionGranted(Context ctx) {
		PackageManager packageMgr = ctx.getPackageManager();
		String packageName = ctx.getPackageName();

		int getAccounts = packageMgr.checkPermission(GET_ACCOUNTS, packageName);
		int authenticateAccounts = packageMgr.checkPermission(AUTHENTICATE_ACCOUNTS, packageName);
		int manageAccounts = packageMgr.checkPermission(MANAGE_ACCOUNTS, packageName);

		return getAccounts == PERMISSION_GRANTED
			&& authenticateAccounts == PERMISSION_GRANTED
			&& manageAccounts == PERMISSION_GRANTED;
	}

	protected Account getUserAccount(String userName) {
		Account[] accounts = _accountManager.getAccountsByType(getStoreName());

		for (Account account : accounts) {
			if (account.name.equals(userName)) {
				return account;
			}
		}

		return null;
	}

	private BasicAuthentication _auth;
	private User _user;
	private AccountManager _accountManager;

}