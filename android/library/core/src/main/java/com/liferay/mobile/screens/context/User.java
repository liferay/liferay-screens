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

import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class User {

	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String USER_ID = "userId";
	public static final String UUID = "uuid";
	public static final String PORTRAIT_ID = "portraitId";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";

	public User(JSONObject jsonObject) {
		_jsonObject = jsonObject;
		_attributes = new HashMap<>(jsonObject.length());

		Iterator<String> it = jsonObject.keys();

		while (it.hasNext()) {
			String key = it.next();

			try {
				_attributes.put(key, jsonObject.get(key));
			}
			catch (JSONException e) {
				LiferayLogger.e("Error parsing json", e);
			}
		}
	}

	public long getId() {
		return (long) _attributes.get(USER_ID);
	}

	public String getUuid() {
		return _attributes.get(UUID).toString();
	}

	public long getPortraitId() {
		return (long) _attributes.get(PORTRAIT_ID);
	}

	public String getFirstName() {
		return getString(FIRST_NAME);
	}

	public String getLastName() {
		return getString(LAST_NAME);
	}

	public String getEmail() {
		return getString(EMAIL_ADDRESS);
	}

	public Map<String, Object> getAttributes() {
		return _attributes;
	}

	public String getString(String key) {
		return (String) _attributes.get(key);
	}

	public long getInt(String key) {
		return (int) _attributes.get(key);
	}

	public long getLong(String key) {
		return (long) _attributes.get(key);
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	private Map<String, Object> _attributes;
	private JSONObject _jsonObject;
}
