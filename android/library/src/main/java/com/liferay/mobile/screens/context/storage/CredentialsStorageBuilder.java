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
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.android.auth.oauth2.OAuth2Authentication;
import com.liferay.mobile.screens.base.AbstractFactory;
import com.liferay.mobile.screens.base.FactoryProvider;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.sharedPreferences.BaseCredentialsStorageSharedPreferences;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Jose Manuel Navarro
 */
public class CredentialsStorageBuilder {

    private Authentication auth;
    private User user;
    private Context context;
    private StorageType storageType;

    public CredentialsStorageBuilder setAuthentication(Authentication auth) {
        if (auth == null) {
            throw new IllegalStateException("Authentication cannot be null. Make sure you have a session created");
        }

        this.auth = auth;

        return this;
    }

    public CredentialsStorageBuilder setUser(User user) {
        if (user == null) {
            throw new IllegalStateException("User cannot be null. Make sure you have a session created");
        }

        this.user = user;

        return this;
    }

    public CredentialsStorageBuilder setContext(Context context) {
        if (context == null) {
            throw new IllegalStateException("Context cannot be null");
        }

        this.context = context;

        return this;
    }

    public CredentialsStorageBuilder setStorageType(StorageType storageType) {
        if (context == null) {
            throw new IllegalStateException("You must set the context before storageType");
        }

        this.storageType = storageType;

        //TODO implement other storage mechanisms
        LiferayLogger.d("We currently support only one type of storage: " + storageType.value);
        return this;
    }

    public CredentialsStorage build() {
        if (context == null) {
            throw new IllegalStateException("You must call setContext() before");
        }

        CredentialsStorage credentialsStorage = createStore();

        credentialsStorage.setContext(context);

        if (auth != null) {
            credentialsStorage.setAuthentication(auth);
        }

        if (user != null) {
            credentialsStorage.setUser(user);
        }

        return credentialsStorage;
    }

    protected CredentialsStorage createStore() {

        AbstractFactory instance = FactoryProvider.getInstance();

        if (storageType == StorageType.NONE) {
            return new CredentialsStorageVoid();
        }

        if (auth == null) {
            // figure out the type from stored value
            switch (BaseCredentialsStorageSharedPreferences.getStoredAuthenticationType(context)) {
                case BASIC:
                    return instance.getBasicCredentialsStorageSharedPreferences();
                case COOKIE:
                    return instance.getCookieCredentialsStorageSharedPreferences();
                case OAUTH2REDIRECT:
                case OAUTH2USERNAMEANDPASSWORD:
                    return instance.getOAuth2CredentialsStorageSharedPreferences();
                default:
                    return new CredentialsStorageVoid();
            }
        } else {
            if (auth instanceof CookieAuthentication) {
                return instance.getCookieCredentialsStorageSharedPreferences();
            } else if (auth instanceof BasicAuthentication) {
                return instance.getBasicCredentialsStorageSharedPreferences();
            } else if (auth instanceof OAuth2Authentication) {
                return instance.getOAuth2CredentialsStorageSharedPreferences();
            } else {
                throw new IllegalStateException("Authentication type is not supported");
            }
        }
    }

    public enum StorageType {

        // These values are synced with 'credentialStore' attr
        NONE(0), AUTO(1), SHARED_PREFERENCES(2);

        private final int value;

        StorageType(int value) {
            this.value = value;
        }

        public static StorageType valueOf(int value) {
            for (StorageType s : values()) {
                if (s.value == value) {
                    return s;
                }
            }
            return NONE;
        }

        public int toInt() {
            return value;
        }
    }
}