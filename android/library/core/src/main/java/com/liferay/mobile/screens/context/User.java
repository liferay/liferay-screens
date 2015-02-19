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

package com.liferay.mobile.screens.context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class User {

	public User(JSONObject jsonObject) {
		_jsonObject = jsonObject;
		_attributes = new HashMap<String, Object>(jsonObject.length());

		Iterator<String> it = jsonObject.keys();

		while (it.hasNext()) {
			String key = it.next();

			try {
				_attributes.put(key, jsonObject.get(key));
			} catch (JSONException e) {
			}
		}
	}

	public long getId() {
		return (int) _attributes.get("userId");
	}

	public String getUuid() {
		return _attributes.get("uuid").toString();
	}

	public long getPortraitId() {
		return (int) _attributes.get("portraitId");
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	private Map<String, Object> _attributes;
	private JSONObject _jsonObject;
}
