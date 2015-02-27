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

import com.liferay.mobile.screens.context.LiferayScreensContext;
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

import static com.liferay.mobile.screens.context.storage.SessionStoreBuilder.StorageType.SHARED_PREFERENCES;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class SessionStoreSharedPreferencesTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenStoreSession {

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenContextIsNotPresent() throws Exception {
			SessionStoreSharedPreferences store = new SessionStoreSharedPreferences();
			setTestData(store);

			store.setContext(null);

			store.storeSession();
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenSessionIsNotPresent() throws Exception {
			SessionStoreSharedPreferences store = new SessionStoreSharedPreferences();
			setTestData(store);

			store.setAuthentication(null);

			store.storeSession();
		}

		@Test(expected = IllegalStateException.class)
		public void shouldRaiseExceptionWhenUserAttributesAreNotPresent() throws Exception {
			SessionStoreSharedPreferences store = new SessionStoreSharedPreferences();
			setTestData(store);

			store.setUser(null);

			store.storeSession();
		}

		@Test
		public void shouldStoreTheCredentialsInSharedPreferences() throws Exception {
			SessionStoreSharedPreferences store = new SessionStoreSharedPreferences();
			setTestData(store);
			store.storeSession();

			String sharedPreferencesName = store.getStoreName();

			SharedPreferences sharedPref =
				Robolectric.getShadowApplication().getApplicationContext().getSharedPreferences(
					sharedPreferencesName, Context.MODE_PRIVATE);

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
			SessionStoreSharedPreferences store = new SessionStoreSharedPreferences();
			setTestData(store);
			store.storeSession();

			store.removeStoredSession();

			String sharedPreferencesName = SessionStoreBuilder.StorageType.getStoreName();

			SharedPreferences sharedPref =
				Robolectric.getShadowApplication().getApplicationContext().getSharedPreferences(
					sharedPreferencesName, Context.MODE_PRIVATE);

			assertEquals(-1, sharedPref.getInt("value", -1));
		}

	}

	private static void setTestData(SessionStore store) {
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