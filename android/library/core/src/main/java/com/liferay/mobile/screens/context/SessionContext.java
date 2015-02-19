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

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class SessionContext {

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

	public static User getUser() {
		return _user;
	}

	private static Session _session;
	private static User _user;

}