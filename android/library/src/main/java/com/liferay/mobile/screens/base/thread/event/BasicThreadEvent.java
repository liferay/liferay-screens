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

import java.io.Serializable;
import org.json.JSONObject;

public class BasicThreadEvent implements Serializable {

	private String actionName;
	private transient JSONObject jsonObject;
	private Exception exception;
	private int targetScreenletId;

	public BasicThreadEvent() {
		super();
	}

	public BasicThreadEvent(JSONObject jsonObject) {
		super();
		this.jsonObject = jsonObject;
	}

	public BasicThreadEvent(Exception exception) {
		this.exception = exception;
	}

	public Exception getException() {
		return exception;
	}

	public int getTargetScreenletId() {
		return targetScreenletId;
	}

	public void setJSONObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public void setTargetScreenletId(int targetScreenletId) {
		this.targetScreenletId = targetScreenletId;
	}

	public boolean isFailed() {
		return exception != null;
	}

	public JSONObject getJSONObject() {
		return jsonObject;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
}