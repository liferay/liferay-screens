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
import android.content.SharedPreferences;

import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
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

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenStoreCredentials {

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenContextIsNotPresent() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			setTestData(store);

			store.setContext(null);

			store.storeCredentials();
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenSessionIsNotPresent() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			setTestData(store);

			store.setAuthentication(null);

			store.storeCredentials();
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenUserAttributesAreNotPresent() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			setTestData(store);

			store.setUser(null);

			store.storeCredentials();
		}

		@Test
		public void shouldStoreTheCredentialsInSharedPreferences() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			setTestData(store);
			store.storeCredentials();

			SharedPreferences sharedPref =
				Robolectric.getShadowApplication().getApplicationContext().getSharedPreferences(
					store.getStoreName(), Context.MODE_PRIVATE);

			assertEquals("user123", sharedPref.getString("username", "not-present"));
			assertEquals("pass123", sharedPref.getString("password", "not-present"));
			assertEquals(LiferayServerContext.getServer(), sharedPref.getString("server", "not-present"));
			assertEquals(LiferayServerContext.getGroupId(), sharedPref.getLong("groupId", 0));
			assertEquals(LiferayServerContext.getCompanyId(), sharedPref.getLong("companyId", 0));
			assertEquals("{\"userId\":123}", sharedPref.getString("attributes", "not-present"));
		}

	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenRemoveStoredCredentials {

		@Test
		public void shouldRemoveTheStoredCredentials() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			setTestData(store);
			store.storeCredentials();

			store.removeStoredCredentials();

			SharedPreferences sharedPref =
				Robolectric.getShadowApplication().getApplicationContext().getSharedPreferences(
					store.getStoreName(), Context.MODE_PRIVATE);

			assertFalse(sharedPref.contains("username"));
		}

	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenLoadingStoredCredentials {

		@Test
		public void shouldNotLoadWhenCredentialsAreNotStored() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			store.setContext(Robolectric.getShadowApplication().getApplicationContext());
			store.removeStoredCredentials();

			assertFalse(store.loadStoredCredentials());
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionIfStoredCredentialsAreNotConsistent() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			setTestData(store);
			store.storeCredentials();

			// Don't recreate the store object because SharedPreferences are mocked by
			// Robolectric and it uses an in-memory store

			LiferayServerContext.setServer("http://otherhost.com");

			store.loadStoredCredentials();
		}

		@Test
		public void shouldLoadTheStoredValues() throws Exception {
			CredentialsStoreSharedPreferences store = new CredentialsStoreSharedPreferences();
			setTestData(store);
			store.storeCredentials();

			BasicAuthentication savedAuth = store.getAuthentication();
			User savedUser = store.getUser();

			assertTrue(store.loadStoredCredentials());

			assertNotNull(store.getAuthentication());
			assertNotNull(store.getUser());

			assertNotSame(savedAuth, store.getAuthentication());
			assertNotSame(savedUser, store.getUser());

			assertEquals("user123", store.getAuthentication().getUsername());
			assertEquals("pass123", store.getAuthentication().getPassword());
			assertEquals(123, store.getUser().getId());
		}

	}

	private static void setTestData(CredentialsStore store) {
		store.setContext(Robolectric.getShadowApplication().getApplicationContext());

		JSONObject userAttributes = null;
		try {
			userAttributes = new JSONObject().put("userId", 123);
		} catch (JSONException e) {
		}
		store.setUser(new User(userAttributes));

		SessionContext.createSession("user123", "pass123");
		store.setAuthentication(SessionContext.getAuthentication());
	}

}