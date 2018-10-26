/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
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
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BaseCredentialsStorageSharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES;
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

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenCreateSession {

        private Session session;

        @Before
        public void setUp() {
            session = SessionContext.createBasicSession("username", "password");
            assertNotNull(session);
        }

        @Test
        public void shouldCreateTheRightSession() throws Exception {
            assertTrue(session.getAuthentication() instanceof BasicAuthentication);

            BasicAuthentication auth = (BasicAuthentication) session.getAuthentication();

            assertEquals("username", auth.getUsername());
            assertEquals("password", auth.getPassword());
            assertTrue(SessionContext.isLoggedIn());
        }

        @Test
        public void shouldCreateASessionFromCurrentSession() throws Exception {
            Session secondSession = SessionContext.createSessionFromCurrentSession();

            assertNotNull(secondSession);

            BasicAuthentication auth1 = (BasicAuthentication) session.getAuthentication();
            BasicAuthentication auth2 = (BasicAuthentication) secondSession.getAuthentication();

            assertEquals(auth1.getUsername(), auth2.getUsername());
            assertEquals(auth1.getPassword(), auth2.getPassword());

            assertNotSame(session, secondSession);
        }

        @Test
        public void shouldReturnTheBasicAuthentication() throws Exception {
            BasicAuthentication auth = (BasicAuthentication) SessionContext.getAuthentication();

            assertNotNull(auth);
            assertSame(session.getAuthentication(), auth);

            assertEquals("username", auth.getUsername());
            assertEquals("password", auth.getPassword());
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenLogout {

        @Before
        public void setUp() {
            SessionContext.createBasicSession("username", "password");
            SessionContext.logout();
        }

        @Test
        public void shouldClearTheSession() throws Exception {
            assertFalse(SessionContext.isLoggedIn());
        }

        @Test(expected = IllegalStateException.class)
        public void shouldNotAllowToCreateASessionFromCurrentSession() throws Exception {
            SessionContext.createSessionFromCurrentSession();
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenSettingUserAttributes {

        @Before
        public void setUp() throws JSONException {
            SessionContext.createBasicSession("username", "password");
            SessionContext.setCurrentUser(new User(new JSONObject().put("userId", 123)));
        }

        @Test
        public void shouldReturnTheUserObject() throws Exception {
            assertNotNull(SessionContext.getCurrentUser());
        }

        @Test
        public void userObjectShouldContainTheUserAttributes() throws Exception {
            assertEquals(123, SessionContext.getCurrentUser().getId());
        }

        @Test
        public void shouldClearUserWhenSessionIsCleared() throws Exception {
            SessionContext.logout();
            assertNull(SessionContext.getCurrentUser());
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenStoreSessionInSharedPreferences {

        @Test(expected = IllegalStateException.class)
        public void shouldRaiseExceptionWhenContextIsNotPresent() throws Exception {
            LiferayScreensContext.deinit();

            SessionContext.createBasicSession("user123", "pass123");
            SessionContext.setCurrentUser(new User(new JSONObject().put("userId", 123)));

            SessionContext.storeCredentials(SHARED_PREFERENCES);
        }

        @Test(expected = IllegalStateException.class)
        public void shouldRaiseExceptionWhenSessionIsNotPresent() throws Exception {
            Context ctx = RuntimeEnvironment.application.getApplicationContext();
            LiferayScreensContext.init(ctx);

            SessionContext.logout();
            SessionContext.setCurrentUser(new User(new JSONObject().put("userId", 123)));

            SessionContext.storeCredentials(SHARED_PREFERENCES);
        }

        @Test(expected = IllegalStateException.class)
        public void shouldRaiseExceptionWhenUserAttributesAreNotPresent() throws Exception {
            Context ctx = RuntimeEnvironment.application.getApplicationContext();
            LiferayScreensContext.init(ctx);

            SessionContext.logout(); // to clean user
            SessionContext.createBasicSession("user123", "pass123");

            SessionContext.storeCredentials(SHARED_PREFERENCES);
        }

        @Test
        public void shouldStoreBasicCredentialsInSharedPreferences() throws Exception {
            SessionContext.createBasicSession("user123", "pass123");

            Context ctx = RuntimeEnvironment.application.getApplicationContext();
            LiferayScreensContext.init(ctx);

            JSONObject userAttributes = new JSONObject().put("userId", 123);
            User user = new User(userAttributes);

            SessionContext.setCurrentUser(user);

            SessionContext.storeCredentials(SHARED_PREFERENCES);

            String sharedPreferencesName = BaseCredentialsStorageSharedPreferences.getStoreName();
            SharedPreferences sharedPref = ctx.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);

            assertEquals(LiferayServerContext.getServer(), sharedPref.getString("server", "not-present"));
            assertEquals(LiferayServerContext.getGroupId(), sharedPref.getLong("groupId", 0));
            assertEquals(LiferayServerContext.getCompanyId(), sharedPref.getLong("companyId", 0));
            assertEquals("{\"userId\":123}", sharedPref.getString("attributes", "not-present"));

            assertEquals("user123", sharedPref.getString("username", "not-present"));
            assertEquals("pass123", sharedPref.getString("password", "not-present"));
        }

        @Test
        public void shouldLoadBasicCredentials() throws Exception {
            SessionContext.createBasicSession("user123", "pass123");

            Context ctx = RuntimeEnvironment.application.getApplicationContext();
            LiferayScreensContext.init(ctx);

            JSONObject userAttributes = new JSONObject().put("userId", 123);
            User user = new User(userAttributes);

            SessionContext.setCurrentUser(user);

            SessionContext.storeCredentials(SHARED_PREFERENCES);
            SessionContext.logout();
            SessionContext.loadStoredCredentials(SHARED_PREFERENCES);

            String sharedPreferencesName = BaseCredentialsStorageSharedPreferences.getStoreName();
            SharedPreferences sharedPref = ctx.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);

            assertEquals(sharedPref.getString("server", "not-present"), LiferayServerContext.getServer());
            assertEquals(sharedPref.getLong("groupId", 0), LiferayServerContext.getGroupId());
            assertEquals(sharedPref.getLong("companyId", 0), LiferayServerContext.getCompanyId());
            assertEquals("{\"userId\":123}", sharedPref.getString("attributes", "not-present"));

            BasicAuthentication auth = (BasicAuthentication) SessionContext.getAuthentication();

            assertEquals("user123", auth.getUsername());
            assertEquals("pass123", auth.getPassword());
        }
    }
}