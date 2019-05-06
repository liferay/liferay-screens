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
import com.liferay.mobile.android.auth.Authentication;
import com.liferay.mobile.screens.context.User;

/**
 * @author Jose Manuel Navarro
 */
public class CredentialsStorageVoid implements CredentialsStorage {

    @Override
    public void storeCredentials() {
    }

    @Override
    public void removeStoredCredentials() {
    }

    @Override
    public boolean loadStoredCredentials() {
        return false;
    }

    @Override
    public boolean loadStoredCredentialsAndServer() throws IllegalStateException {
        return false;
    }

    @Override
    public Authentication getAuthentication() {
        return null;
    }

    @Override
    public void setAuthentication(Authentication auth) {

    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void setUser(User user) {
    }

    @Override
    public void setContext(Context ctx) {
    }
}
