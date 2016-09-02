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

package com.liferay.mobile.screens.context;

import android.content.Context;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.widget.ImageView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.io.IOException;

/**
 * @author Silvio Santos
 */
public class LiferayServerContext {

	private static final int MAX_SIZE = 100 * 1024 * 1024;

	public static void reloadFromResources(Resources resources, final String packageName) {

		int companyIdentifier = resources.getIdentifier("liferay_company_id", "integer", packageName);
		int groupIdentifier = resources.getIdentifier("liferay_group_id", "integer", packageName);

		_companyId = getValueFromIntegerOrString(resources, R.string.liferay_company_id, companyIdentifier);
		_groupId = getValueFromIntegerOrString(resources, R.string.liferay_group_id, groupIdentifier);
		_server = resources.getString(R.string.liferay_server);
		_classFactory = resources.getString(R.string.liferay_class_factory);
		_portalVersion = LiferayPortalVersion.fromInt(resources.getInteger(R.integer.liferay_portal_version));
		_versionFactory = resources.getString(R.string.liferay_version_factory);
	}

	public static void loadFromResources(Resources resources, final String packageName) {

		if (_companyId == 0 || _groupId == 0 || _server == null) {
			reloadFromResources(resources, packageName);
		}
	}

	public static long getCompanyId() {
		return _companyId;
	}

	public static void setCompanyId(long companyId) {
		LiferayServerContext._companyId = companyId;
	}

	public static long getGroupId() {
		return _groupId;
	}

	public static void setGroupId(long groupId) {
		LiferayServerContext._groupId = groupId;
	}

	public static String getServer() {
		return _server;
	}

	public static void setServer(String server) {
		LiferayServerContext._server = server;
	}

	public static String getClassFactory() {
		return _classFactory;
	}

	public static void setFactoryClass(String factoryClass) {
		_classFactory = factoryClass;
	}

	public static boolean isLiferay7() {
		return LiferayPortalVersion.VERSION_70.equals(_portalVersion);
	}

	public static boolean isLiferay62() {
		return LiferayPortalVersion.VERSION_70.equals(_portalVersion);
	}

	public static String getVersionFactory() {
		return _versionFactory;
	}

	public static LiferayPortalVersion getPortalVersion() {
		return _portalVersion;
	}

	public static void setPortalVersion(LiferayPortalVersion portalVersion) {
		LiferayServerContext._portalVersion = portalVersion;
	}

	public static OkHttpClient getOkHttpClient() {
		if (okHttpClient == null) {
			okHttpClient = new OkHttpClient();
			okHttpClient.setCache(new Cache(LiferayScreensContext.getContext().getCacheDir(), MAX_SIZE));
		}

		return okHttpClient;
	}

	public static OkHttpClient getOkHttpClientNoCache() {
		OkHttpClient noCacheClient = getOkHttpClient().clone();
		noCacheClient.interceptors().add(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request originalRequest = chain.request();

				Request newRequest = originalRequest.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();

				return chain.proceed(newRequest);
			}
		});

		return noCacheClient;
	}

	private static long getValueFromIntegerOrString(final Resources resources, final int stringId, int integerId) {
		return integerId == 0 ? Long.valueOf(resources.getString(stringId)) : resources.getInteger(integerId);
	}

	private static String _server;
	private static long _companyId;
	private static long _groupId;
	private static String _classFactory;
	private static LiferayPortalVersion _portalVersion;
	private static String _versionFactory;

	private static OkHttpClient okHttpClient;
}