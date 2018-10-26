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

package com.liferay.mobile.screens.base.interactor.event;

import java.io.Serializable;
import org.json.JSONObject;

public class BasicEvent implements Serializable {

    private String actionName;
    private JSONObject jsonObject;
    private int targetScreenletId;
    private Exception exception;

    public BasicEvent() {
        super();
    }

    public BasicEvent(JSONObject jsonObject) {
        super();
        this.jsonObject = jsonObject;
    }

    public BasicEvent(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean isFailed() {
        return exception != null;
    }

    public int getTargetScreenletId() {
        return targetScreenletId;
    }

    public void setTargetScreenletId(int targetScreenletId) {
        this.targetScreenletId = targetScreenletId;
    }

    public JSONObject getJSONObject() {
        return jsonObject;
    }

    public void setJSONObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}