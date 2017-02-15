/*
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

package com.liferay.mobile.screens.context;

/**
 * @author Javier Gamarra
 */
public enum AuthenticationType {

	BASIC(0), OAUTH(1), COOKIE(2), VOID(3);

	private final int value;

	AuthenticationType(int value) {
		this.value = value;
	}

	public static AuthenticationType getValue(int value) {
		for (AuthenticationType method : AuthenticationType.values()) {
			if (method.value == value) {
				return method;
			}
		}

		return VOID;
	}
}
