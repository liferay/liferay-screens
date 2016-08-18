/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.base.thread.event;

import java.util.Locale;
import org.json.JSONObject;

public abstract class OfflineEventNew extends BasicThreadEvent {

	public OfflineEventNew() {
		super();
	}

	public OfflineEventNew(JSONObject jsonObject) {
		super(jsonObject);
	}

	public OfflineEventNew(Exception e) {
		super(e);
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

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

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	private Locale _locale;
	private long _groupId;
	private long _userId;
	private boolean cachedRequest;
	private String cacheKey;
}