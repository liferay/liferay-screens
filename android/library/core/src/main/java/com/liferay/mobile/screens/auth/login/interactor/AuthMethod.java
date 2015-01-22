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

/**
 * @author Silvio Santos
 */
public enum AuthMethod {

	EMAIL(0), SCREEN_NAME(1), USER_ID(2);

	public static AuthMethod getValue(int value) {
		for (AuthMethod method : AuthMethod.values()) {
			if (method._value == value) {
				return method;
			}
		}

		return EMAIL;
	}

	private AuthMethod(int value) {
		_value = value;
	}

	private int _value;

}