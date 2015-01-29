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

package com.liferay.mobile.screens.auth.login.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.ScreenletAsyncTaskCallback;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class LoginCallback extends ScreenletAsyncTaskCallback<JSONObject> {

	public LoginCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new LoginEvent(targetScreenletId, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new LoginEvent(targetScreenletId, e);
	}

	@Override
	public JSONObject transform(Object obj) throws Exception {
		return (JSONObject)obj;
	}

}