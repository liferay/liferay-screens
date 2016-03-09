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
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.RobolectricManifestTestRunner;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BaseCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BasicCredentialsStorageSharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
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

	@RunWith(RobolectricManifestTestRunner.class)
	@Config(constants = BuildConfig.class, sdk = 18)
	public static class WhenStoreCredentials {

		@Before
		public void setUp() {
			LiferayScreensContext.init(RuntimeEnvironment.application);
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

			SharedPreferences sharedPref =
				RuntimeEnvironment.application.getApplicationContext().getSharedPreferences(
					BaseCredentialsStorageSharedPreferences.getStoreName(), Context.MODE_PRIVATE);

			assertEquals("user123", sharedPref.getString("username", "not-present"));
			assertEquals("pass123", sharedPref.getString("password", "not-present"));
			assertEquals(LiferayServerContext.getServer(), sharedPref.getString("server", "not-present"));
			assertEquals(LiferayServerContext.getGroupId(), sharedPref.getLong("groupId", 0));
			assertEquals(LiferayServerContext.getCompanyId(), sharedPref.getLong("companyId", 0));
			assertEquals("{\"userId\":123}", sharedPref.getString("attributes", "not-present"));
		}

	}

	@RunWith(RobolectricManifestTestRunner.class)
	@Config(constants = BuildConfig.class, sdk = 18)
	public static class WhenRemoveStoredCredentials {

		@Before
		public void setUp() {
			LiferayScreensContext.init(RuntimeEnvironment.application);
		}

		@Test
		public void shouldRemoveTheStoredCredentials() throws Exception {
			BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
			setBasicTestDataInStore(store);
			store.storeCredentials();

			store.removeStoredCredentials();

			SharedPreferences sharedPref =
				RuntimeEnvironment.application.getApplicationContext().getSharedPreferences(
					BaseCredentialsStorageSharedPreferences.getStoreName(), Context.MODE_PRIVATE);

			assertFalse(sharedPref.contains("username"));
		}

	}

	@RunWith(RobolectricManifestTestRunner.class)
	@Config(constants = BuildConfig.class, sdk = 18)
	public static class WhenLoadingStoredCredentials {

		@Before
		public void setUp() {
			LiferayScreensContext.init(RuntimeEnvironment.application);
		}

		@Test
		public void shouldNotLoadWhenCredentialsAreNotStored() throws Exception {
			BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
			store.setContext(RuntimeEnvironment.application.getApplicationContext());
			store.removeStoredCredentials();

			assertFalse(store.loadStoredCredentials());
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionIfStoredCredentialsAreNotConsistent() throws Exception {
			BasicCredentialsStorageSharedPreferences store = new BasicCredentialsStorageSharedPreferences();
			setBasicTestDataInStore(store);
			store.storeCredentials();

			// Don't recreate the store object because SharedPreferences are mocked by
			// Robolectric and it uses an in-memory store

			LiferayServerContext.setServer("http://otherhost.com");

			store.loadStoredCredentials();
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

	}

}