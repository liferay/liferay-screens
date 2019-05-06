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

package com.liferay.mobile.screens.context.storage.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorage;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public abstract class BaseCredentialsStorageSharedPreferences implements CredentialsStorage {

    private SharedPreferences sharedPreferences;
    private Authentication auth;
    private User user;

    public static String getStoreName() {
        try {
            URL url = new URL(LiferayServerContext.getServer());
            return "liferay-screens-" + url.getHost() + "-" + url.getPort();
        } catch (MalformedURLException e) {
            LiferayLogger.e("Error parsing url", e);
        }

        return "liferay-screens";
    }

    public static AuthenticationType getStoredAuthenticationType(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(getStoreName(), Context.MODE_PRIVATE);
        return AuthenticationType.valueOf(sharedPref.getString("auth", AuthenticationType.VOID.name()));
    }

    @Override
    public void storeCredentials() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("You need to set the context");
        }
        if (auth == null) {
            throw new IllegalStateException("You need to be logged in to store the session");
        }
        if (user == null) {
            throw new IllegalStateException("You need to set user attributes to store the session");
        }

        sharedPreferences.edit()
            .putString("attributes", new JSONObject(user.getValues()).toString())
            .putString("server", LiferayServerContext.getServer())
            .putLong("groupId", LiferayServerContext.getGroupId())
            .putLong("companyId", LiferayServerContext.getCompanyId())
            .apply();

        storeAuth(auth);
    }

    @Override
    public void removeStoredCredentials() {
        if (sharedPreferences == null) {
            throw new IllegalStateException("You need to set the context");
        }

        sharedPreferences.edit().clear().apply();

        user = null;
        auth = null;
    }

    public boolean loadStoredCredentials() throws IllegalStateException {
        return loadStoredCredentials(false);
    }

    public boolean loadStoredCredentialsAndServer() throws IllegalStateException {
        return loadStoredCredentials(true);
    }

    public boolean loadStoredCredentials(boolean shouldLoadServer) throws IllegalStateException {
        if (sharedPreferences == null) {
            throw new IllegalStateException("You need to set the context");
        }

        String userAttributes = sharedPreferences.getString("attributes", null);
        String server = sharedPreferences.getString("server", null);
        long groupId = sharedPreferences.getLong("groupId", 0);
        long companyId = sharedPreferences.getLong("companyId", 0);

        auth = loadAuth();

        if (auth == null || server == null || userAttributes == null || groupId == 0 || companyId == 0) {
            // nothing saved
            return false;
        }

        if (shouldLoadServer) {
            LiferayServerContext.setGroupId(groupId);
            LiferayServerContext.setCompanyId(companyId);
        } else if (!server.equals(LiferayServerContext.getServer())
            || groupId != LiferayServerContext.getGroupId()
            || companyId != LiferayServerContext.getCompanyId()) {

            auth = null;
            user = null;

            LiferayLogger.e("Stored credential values are not consistent with current ones");
            removeStoredCredentials();
            return false;
        }

        try {
            user = new User(new JSONObject(userAttributes));
        } catch (JSONException e) {
            LiferayLogger.e("Stored user attributes are corrupted");
            removeStoredCredentials();
            return false;
        }

        return true;
    }

    @Override
    public Authentication getAuthentication() {
        return auth;
    }

    @Override
    public void setAuthentication(Authentication auth) {
        this.auth = auth;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setContext(Context context) {
        if (context == null) {
            throw new IllegalStateException("Context cannot be null");
        }
        sharedPreferences = context.getApplicationContext().getSharedPreferences(getStoreName(), Context.MODE_PRIVATE);
    }

    protected SharedPreferences getSharedPref() {
        return sharedPreferences;
    }

    protected abstract void storeAuth(Authentication auth);

    protected abstract Authentication loadAuth();
}
