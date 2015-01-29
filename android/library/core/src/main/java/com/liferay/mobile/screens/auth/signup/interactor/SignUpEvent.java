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

package com.liferay.mobile.screens.auth.signup.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class SignUpEvent extends BasicEvent {

	public SignUpEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public SignUpEvent(int targetScreenletId, JSONObject userAttributes) {
		super(targetScreenletId);

		_userAttributes = userAttributes;
	}

	public JSONObject getUserAttributes() {
		return _userAttributes;
	}

	private JSONObject _userAttributes;

}