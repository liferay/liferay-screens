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

import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class User extends AssetEntry {

    public static final String EMAIL_ADDRESS = "emailAddress";
    public static final String USER_ID = "userId";
    public static final String UUID = "uuid";
    public static final String PORTRAIT_ID = "portraitId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String SCREEN_NAME = "screenName";
    public static final String GREETING = "greeting";
    public static final String JOB_TITLE = "jobTitle";
    public static final String LANGUAGE_ID = "languageId";
    public static final String EMAIL_ADDRESS_VERIFIED = "emailAddressVerified";
    public static final String LOCKOUT = "lockout";
    public static final String AGREED_TERMS_USE = "agreedToTermsOfUse";
    private JSONObject jsonObject;

    public User(JSONObject jsonObject) {
        this.values = new HashMap<>();
        try {
            values.putAll(JSONUtil.toMap(jsonObject));
        } catch (JSONException e) {
            LiferayLogger.e("Error parsing json", e);
        }
    }

    public User(Map<String, Object> map) {
        this.values = map;
        this.values.putAll((Map) map.get("user"));
    }

    public long getId() {
        return JSONUtil.castToLong(values.get(USER_ID));
    }

    public String getUuid() {
        return values.get(UUID).toString();
    }

    public long getPortraitId() {
        return JSONUtil.castToLong(values.get(PORTRAIT_ID));
    }

    public String getFirstName() {
        return getString(FIRST_NAME);
    }

    public String getLastName() {
        return getString(LAST_NAME);
    }

    public String getFullName() {
        return String.format("%s %s", getFirstName(), getLastName());
    }

    public String getEmail() {
        return getString(EMAIL_ADDRESS);
    }

    public String getScreenName() {
        return getString(SCREEN_NAME);
    }

    public String getGreeting() {
        return getString(GREETING);
    }

    public String getJobTitle() {
        return getString(JOB_TITLE);
    }

    public String getString(String key) {
        return (String) values.get(key);
    }

    public long getInt(String key) {
        return (int) values.get(key);
    }

    public long getLong(String key) {
        return (long) values.get(key);
    }

    @Override
    public String toString() {
        return values.toString();
    }
}
