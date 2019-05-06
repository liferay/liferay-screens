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

package com.liferay.mobile.screens.context.storage;

import android.content.Context;
import android.content.SharedPreferences;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.android.auth.oauth2.OAuth2Authentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BaseCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BasicCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.context.storage.sharedPreferences.CookieCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.context.storage.sharedPreferences.OAuth2CredentialsStorageSharedPreferences;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class CredentialsStoreSharedPreferencesTest {

    private static void setBasicTestDataInStore(CredentialsStorage store) throws JSONException {
        store.setContext(RuntimeEnvironment.application.getApplicationContext());

        JSONObject userAttributes = new JSONObject();
        userAttributes.put("userId", 123);
        store.setUser(new User(userAttributes));

        SessionContext.createBasicSession("user123", "pass123");
        store.setAuthentication(SessionContext.getAuthentication());
    }

    private static void setCookieTestDataInStore(CredentialsStorage store) throws JSONException {
        store.setContext(RuntimeEnvironment.application.getApplicationContext());

        JSONObject userAttributes = new JSONObject();
        userAttributes.put("userId", 123);
        store.setUser(new User(userAttributes));

        CookieAuthentication authentication =
            new CookieAuthentication("authToken", "cookieHeader", "user123", "pass123", true, 1, 2);
        Session session = new SessionImpl(LiferayServerContext.getServer(), authentication);

        SessionContext.createCookieSession(session);
        store.setAuthentication(SessionContext.getAuthentication());
    }

    private static void setOAuth2TestInDataStore(CredentialsStorage store, String clientSecret) throws JSONException {
        store.setContext(RuntimeEnvironment.application.getApplicationContext());

        JSONObject userAttributes = new JSONObject();
        userAttributes.put("userId", 123);
        store.setUser(new User(userAttributes));

        List<String> scopes = new ArrayList<>();
        scopes.add("scope1");
        scopes.add("scope2");

        OAuth2Authentication authentication =
            new OAuth2Authentication("accessToken", "refreshToken", scopes, 150, "clientId", clientSecret);
        Session session = new SessionImpl(LiferayServerContext.getServer(), authentication);

        SessionContext.createCookieSession(session);
        store.setAuthentication(SessionContext.getAuthentication());
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenStoreCredentials {

        @Before
        public void setUp() {
            LiferayScreensContext.reinit(RuntimeEnvironment.application);
        }

        @Test(expected = IllegalStateException.class)
        public void shouldRaiseExceptionWhenContextIsNotPresent() throws Exception {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            setBasicTestDataInStore(store);

            store.setContext(null);

            store.storeCredentials();
        }

        @Test(expected = IllegalStateException.class)
        public void shouldRaiseExceptionWhenSessionIsNotPresent() throws Exception {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            setBasicTestDataInStore(store);

            store.setAuthentication(null);

            store.storeCredentials();
        }

        @Test(expected = IllegalStateException.class)
        public void shouldRaiseExceptionWhenUserAttributesAreNotPresent() throws Exception {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            setBasicTestDataInStore(store);

            store.setUser(null);

            store.storeCredentials();
        }

        @Test
        public void shouldStoreTheCredentialsInSharedPreferences() throws Exception {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            setBasicTestDataInStore(store);
            store.storeCredentials();

            SharedPreferences sharedPref = RuntimeEnvironment.application.getApplicationContext()
                .getSharedPreferences(BaseCredentialsStorageSharedPreferences.getStoreName(), Context.MODE_PRIVATE);

            assertEquals("user123", sharedPref.getString("username", "not-present"));
            assertEquals("pass123", sharedPref.getString("password", "not-present"));
            assertEquals(LiferayServerContext.getServer(), sharedPref.getString("server", "not-present"));
            assertEquals(LiferayServerContext.getGroupId(), sharedPref.getLong("groupId", 0));
            assertEquals(LiferayServerContext.getCompanyId(), sharedPref.getLong("companyId", 0));

            assertEquals("{\"userId\":123}", sharedPref.getString("attributes", "not-present"));
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenRemoveStoredCredentials {

        @Before
        public void setUp() {
            LiferayScreensContext.reinit(RuntimeEnvironment.application);
        }

        @Test
        public void shouldRemoveTheStoredCredentials() throws Exception {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            setBasicTestDataInStore(store);
            store.storeCredentials();

            store.removeStoredCredentials();

            SharedPreferences sharedPref = RuntimeEnvironment.application.getApplicationContext()
                .getSharedPreferences(BaseCredentialsStorageSharedPreferences.getStoreName(), Context.MODE_PRIVATE);

            assertFalse(sharedPref.contains("username"));
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenLoadingStoredCredentials {

        @Before
        public void setUp() {
            LiferayScreensContext.reinit(RuntimeEnvironment.application);
        }

        @Test
        public void shouldNotLoadWhenCredentialsAreNotStored() {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            store.setContext(RuntimeEnvironment.application.getApplicationContext());
            store.removeStoredCredentials();

            assertFalse(store.loadStoredCredentials());
        }

        @Test
        public void shouldRaiseExceptionIfStoredCredentialsAreNotConsistent() throws Exception {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            setBasicTestDataInStore(store);
            store.storeCredentials();

            // Don't recreate the store object because SharedPreferences are mocked by
            // Robolectric and it uses an in-memory store

            LiferayServerContext.setServer("http://otherhost.com");

            assertFalse(store.loadStoredCredentials());
        }

        @Test
        public void shouldLoadTheStoredValues() throws Exception {
            BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
            setBasicTestDataInStore(store);
            store.storeCredentials();

            BasicAuthentication savedAuth = (BasicAuthentication) store.getAuthentication();
            User savedUser = store.getUser();

            assertTrue(store.loadStoredCredentials());

            assertNotNull(store.getAuthentication());
            assertNotNull(store.getUser());

            assertNotSame(savedAuth, store.getAuthentication());
            assertNotSame(savedUser, store.getUser());

            BasicAuthentication auth = (BasicAuthentication) store.getAuthentication();

            assertEquals("user123", auth.getUsername());
            assertEquals("pass123", auth.getPassword());
            assertEquals(123, store.getUser().getId());
        }

        @Test
        public void shouldLoadTheStoredValuesForCookieStore() throws Exception {
            CookieCredentialsStorageSharedPreferences store = new CookieCredentialsStorageSharedPreferences();
            setCookieTestDataInStore(store);
            store.storeCredentials();

            CookieAuthentication savedAuth = (CookieAuthentication) store.getAuthentication();
            User savedUser = store.getUser();

            assertTrue(store.loadStoredCredentials());

            assertNotNull(store.getAuthentication());
            assertNotNull(store.getUser());

            assertNotSame(savedAuth, store.getAuthentication());
            assertNotSame(savedUser, store.getUser());

            CookieAuthentication auth = (CookieAuthentication) store.getAuthentication();

            assertEquals("authToken", auth.getAuthToken());
            assertEquals("cookieHeader", auth.getCookieHeader());
            assertEquals(true, auth.shouldHandleExpiration());
            assertEquals(1, auth.getCookieExpirationTime());
            assertEquals(2, auth.getLastCookieRefresh());
            assertEquals("user123", auth.getUsername());
            assertEquals("pass123", auth.getPassword());
            assertEquals(123, store.getUser().getId());
        }

        @Test
        public void shouldLoadTheStoredValuesForOAuth2Store() throws Exception {
            OAuth2CredentialsStorageSharedPreferences store = new OAuth2CredentialsStorageSharedPreferences();
            setOAuth2TestInDataStore(store, "clientSecret");
            store.storeCredentials();

            OAuth2Authentication savedAuth = (OAuth2Authentication) store.getAuthentication();
            User savedUser = store.getUser();

            assertTrue(store.loadStoredCredentials());

            assertNotNull(store.getAuthentication());
            assertNotNull(store.getUser());

            assertNotSame(savedAuth, store.getAuthentication());
            assertNotSame(savedUser, store.getUser());

            OAuth2Authentication auth = (OAuth2Authentication) store.getAuthentication();

            assertEquals("accessToken", auth.getAccessToken());
            assertEquals("refreshToken", auth.getRefreshToken());
            assertEquals("scope1", auth.getScope().get(0));
            assertEquals("scope2", auth.getScope().get(1));
            assertEquals(150, auth.getAccessTokenExpirationDate());
            assertEquals("clientId", auth.getClientId());
            assertEquals("clientSecret", auth.getClientSecret());
        }

        @Test
        public void shouldLoadTheStoredValuesForOAuth2StoreWithoutClientSecret() throws Exception {
            OAuth2CredentialsStorageSharedPreferences store = new OAuth2CredentialsStorageSharedPreferences();
            setOAuth2TestInDataStore(store, "");
            store.storeCredentials();

            OAuth2Authentication auth = (OAuth2Authentication) store.getAuthentication();

            assertEquals("", auth.getClientSecret());
        }
    }
}