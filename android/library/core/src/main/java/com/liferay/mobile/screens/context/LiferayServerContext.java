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

package com.liferay.mobile.screens.context;

/**
 * @author Silvio Santos
 */
public class LiferayServerContext {

	public static long getCompanyId() {
		return _companyId;
	}

    public static void setCompanyId(long companyId) {
        _companyId = companyId;
    }

    public static long getGroupId() {
		return _groupId;
	}

    public static void setGroupId(long groupId) {
        _groupId = groupId;
    }

	public static String getServer() {
		return _server;
	}

    public static void setServer(String server) {
        _server = server;
    }

    private static long _companyId;
    private static long _groupId;
    private static String _server;

}