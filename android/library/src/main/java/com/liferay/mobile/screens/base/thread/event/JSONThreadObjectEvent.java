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

package com.liferay.mobile.screens.base.thread.event;

import com.liferay.mobile.screens.base.thread.IdCache;

import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public abstract class JSONThreadObjectEvent extends BasicThreadEvent implements IdCache {

	public JSONThreadObjectEvent(Exception e) {
		super(e);
	}

	public JSONThreadObjectEvent(JSONObject jsonObject) {
		super();
		_jsonObject = jsonObject;
	}

	public JSONObject getJSONObject() {
		return _jsonObject;
	}

	@Override
	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public JSONObject getJsonObject() {
		return _jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public boolean isCachedRequest() {
		return cachedRequest;
	}

	public void setCachedRequest(boolean cachedRequest) {
		this.cachedRequest = cachedRequest;
	}

	private JSONObject _jsonObject;
	private Locale _locale;
	private long _groupId;
	private String _userId;
	private boolean cachedRequest;

}