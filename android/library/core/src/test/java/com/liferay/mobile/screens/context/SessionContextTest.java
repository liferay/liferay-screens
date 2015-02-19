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

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
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

	}

}