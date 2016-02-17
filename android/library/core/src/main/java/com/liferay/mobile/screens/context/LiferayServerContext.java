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

import android.content.res.Resources;

import com.liferay.mobile.screens.R;

/**
 * @author Silvio Santos
 */
public class LiferayServerContext {

	public static void loadFromResources(Resources resources, final String packageName) {
		int companyIdentifier = resources.getIdentifier("liferay_company_id", "integer", packageName);
		int groupIdentifier = resources.getIdentifier("liferay_group_id", "integer", packageName);

		_companyId = getValueFromIntegerOrString(resources, R.string.liferay_company_id, companyIdentifier);
		_groupId = getValueFromIntegerOrString(resources, R.string.liferay_group_id, groupIdentifier);
		_server = resources.getString(R.string.liferay_server);
		_liferayPortalVersion = LiferayPortalVersion.fromInt(resources.getInteger(R.integer.liferay_portal_version));
		_customServiceVersionFactory = resources.getString(R.string.liferay_custom_service_version_factory);
	}

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

	public static boolean isLiferay7() {
		return LiferayPortalVersion.VERSION_70.equals(_liferayPortalVersion);
	}

	public static boolean isLiferay62() {
		return LiferayPortalVersion.VERSION_70.equals(_liferayPortalVersion);
	}

	public static String getCustomServiceVersionFactory() {
		return _customServiceVersionFactory;
	}

	private static long getValueFromIntegerOrString(final Resources resources, final int stringId, int integerId) {
		return integerId == 0 ? Long.valueOf(resources.getString(stringId)) : resources.getInteger(integerId);
	}

	private static long _companyId;
	private static long _groupId;
	private static String _server;
	private static LiferayPortalVersion _liferayPortalVersion;
	private static String _customServiceVersionFactory;
}