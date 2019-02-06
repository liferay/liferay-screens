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

package com.liferay.mobile.screens.util.extensions

import org.json.JSONArray
import org.json.JSONObject

/**
 * @author Victor Oliveira
 */
fun JSONObject.getOptional(name: String): Any? {
	return runCatching { this.get(name) }.getOrNull()
}

fun JSONObject.getOptionalJSONObject(name: String): JSONObject? {
	return runCatching { this.getJSONObject(name) }.getOrNull()
}

fun JSONObject.getOptionalString(name: String): String? {
	return runCatching { this.getString(name) }.getOrNull()
}

fun JSONObject.getOptionalJSONArray(name: String): JSONArray? {
	return runCatching { this.getJSONArray(name) }.getOrNull()
}
