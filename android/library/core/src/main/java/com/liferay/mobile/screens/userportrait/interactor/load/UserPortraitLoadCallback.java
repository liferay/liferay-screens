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

package com.liferay.mobile.screens.userportrait.interactor.load;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class UserPortraitLoadCallback extends InteractorAsyncTaskCallback<JSONObject> {

	public UserPortraitLoadCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public JSONObject transform(Object obj) throws Exception {
		return (JSONObject) obj;
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new UserPortraitLoadEvent(targetScreenletId, result);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new UserPortraitLoadEvent(targetScreenletId, e);
	}

}