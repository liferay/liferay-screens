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

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.liferay.mobile.screens.context.SessionContext.StorageType.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * @author Silvio Santos
 */
@RunWith(Enclosed.class)
public class SessionContextTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenCreateSession {

		@Before
		public void setUp() {
			_session = SessionContext.createSession("username", "password");
			assertNotNull(_session);
		}

		@Test
		public void shouldCreateTheRightSession() throws Exception {
			assertTrue(_session.getAuthentication() instanceof BasicAuthentication);

			BasicAuthentication auth = (BasicAuthentication) _session.getAuthentication();

			assertEquals("username", auth.getUsername());
			assertEquals("password", auth.getPassword());
			assertTrue(SessionContext.hasSession());
		}

		@Test
		public void shouldCreateASessionFromCurrentSession() throws Exception {
			Session secondSession = SessionContext.createSessionFromCurrentSession();

			assertNotNull(secondSession);

			BasicAuthentication auth1 = (BasicAuthentication) _session.getAuthentication();
			BasicAuthentication auth2 = (BasicAuthentication) secondSession.getAuthentication();

			assertEquals(auth1.getUsername(), auth2.getUsername());
			assertEquals(auth1.getPassword(), auth2.getPassword());

			assertNotSame(_session, secondSession);
		}

		@Test
		public void shouldReturnTheBasicAuthentication() throws Exception {
			BasicAuthentication auth = SessionContext.getAuthentication();

			assertNotNull(auth);
			assertSame(_session.getAuthentication(), auth);

			assertEquals("username", auth.getUsername());
			assertEquals("password", auth.getPassword());
		}

		private Session _session;

	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenClearSession {

		@Before
		public void setUp() {
			SessionContext.createSession("username", "password");
			SessionContext.clearSession();
		}

		@Test
		public void shouldClearTheSession() throws Exception {
			assertFalse(SessionContext.hasSession());
		}

		@Test(expected = IllegalStateException.class)
		public void shouldNotAllowToCreateASessionFromCurrentSession() throws Exception {
			SessionContext.createSessionFromCurrentSession();
		}

	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenSettingUserAttributes {

		@Before
		public void setUp() throws JSONException {
			SessionContext.createSession("username", "password");
			SessionContext.setUserAttributes(new JSONObject().put("userId", 123));
		}

		@Test
		public void shouldReturnTheUserObject() throws Exception {
			assertNotNull(SessionContext.getUser());
		}

		@Test
		public void userObjectShouldContainTheUserAttributes() throws Exception {
			assertEquals(123, SessionContext.getUser().getId());
		}

		@Test
		public void shouldClearUserWhenSessionIsCleared() throws Exception {
			SessionContext.clearSession();
			assertNull(SessionContext.getUser());
		}

	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenStoringTheCredentials {

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenContextIsNotPresent() throws Exception {
			SessionContext.createSession("user123", "pass123");

			SessionContext.setUserAttributes(new JSONObject().put("userId", 123));

			SessionContext.storeSession(SHARED_PREFERENCES);
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenSessionIsNotPresent() throws Exception {
			Context ctx = Robolectric.getShadowApplication().getApplicationContext();
			LiferayScreensContext.init(ctx);

			SessionContext.setUserAttributes(new JSONObject().put("userId", 123));

			SessionContext.clearSession();
			SessionContext.storeSession(SHARED_PREFERENCES);
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenUserAttributesAreNotPresent() throws Exception {
			SessionContext.clearSession(); // to clean user
			SessionContext.createSession("user123", "pass123");

			Context ctx = Robolectric.getShadowApplication().getApplicationContext();
			LiferayScreensContext.init(ctx);

			SessionContext.storeSession(SHARED_PREFERENCES);
		}

		@Test
		public void shouldStoreTheCredentialsInSharedPreferences() throws Exception {
			SessionContext.createSession("user123", "pass123");

			Context ctx = Robolectric.getShadowApplication().getApplicationContext();
			LiferayScreensContext.init(ctx);

			JSONObject userAttributes = new JSONObject().put("userId", 123);
			SessionContext.setUserAttributes(userAttributes);

			SessionContext.storeSession(SHARED_PREFERENCES);

			SharedPreferences sharedPref =
				ctx.getSharedPreferences(SessionContext.getStoreName(), Context.MODE_PRIVATE);

			assertEquals("user123", sharedPref.getString("username", "not-present"));
			assertEquals("pass123", sharedPref.getString("password", "not-present"));
			assertEquals(LiferayServerContext.getServer(), sharedPref.getString("server", "not-present"));
			assertEquals(LiferayServerContext.getGroupId(), sharedPref.getLong("groupId", 0));
			assertEquals(LiferayServerContext.getCompanyId(), sharedPref.getLong("companyId", 0));
			assertEquals(userAttributes.toString(), sharedPref.getString("attributes", "not-present"));
		}

	}

}