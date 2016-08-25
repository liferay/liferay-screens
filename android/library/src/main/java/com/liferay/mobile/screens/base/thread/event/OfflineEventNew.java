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
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
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

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	private Locale locale;
	private long groupId;
	private long userId;
	private boolean cachedRequest;
	private String cacheKey;
	private boolean dirty;
}