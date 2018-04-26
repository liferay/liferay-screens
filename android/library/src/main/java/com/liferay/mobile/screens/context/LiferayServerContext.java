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

import android.content.res.Resources;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.screens.R;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class LiferayServerContext {

	private static final int MAX_SIZE = 100 * 1024 * 1024;
	private static String server;
	private static long companyId;
	private static long groupId;
	private static String classFactory;
	private static LiferayPortalVersion portalVersion;
	private static String versionFactory;
	private static OkHttpClient okHttpClient;

	private LiferayServerContext() {
		super();
	}

	public static void reloadFromResources(Resources resources, final String packageName) {

		int companyIdentifier = resources.getIdentifier("liferay_company_id", "integer", packageName);
		int groupIdentifier = resources.getIdentifier("liferay_group_id", "integer", packageName);

		companyId = getValueFromIntegerOrString(resources, R.string.liferay_company_id, companyIdentifier);
		groupId = getValueFromIntegerOrString(resources, R.string.liferay_group_id, groupIdentifier);
		server = resources.getString(R.string.liferay_server);
		classFactory = resources.getString(R.string.liferay_class_factory);
		portalVersion = LiferayPortalVersion.fromInt(resources.getInteger(R.integer.liferay_portal_version));
		versionFactory = resources.getString(R.string.liferay_version_factory);
	}

	public static void loadFromResources(Resources resources, final String packageName) {

		if (companyId == 0 || groupId == 0 || server == null) {
			reloadFromResources(resources, packageName);
		}
	}

	public static long getCompanyId() {
		return companyId;
	}

	public static void setCompanyId(long companyId) {
		LiferayServerContext.companyId = companyId;
	}

	public static long getGroupId() {
		return groupId;
	}

	public static void setGroupId(long groupId) {
		LiferayServerContext.groupId = groupId;
	}

	public static String getServer() {
		return server;
	}

	public static void setServer(String server) {
		LiferayServerContext.server = server;
	}

	public static String getClassFactory() {
		return classFactory;
	}

	public static void setClassFactory(String factoryClass) {
		classFactory = factoryClass;
	}

	public static boolean isLiferay71() {
		return LiferayPortalVersion.VERSION_71.equals(portalVersion);
	}

	public static boolean isLiferay7() {
		return LiferayPortalVersion.VERSION_70.equals(portalVersion) || LiferayPortalVersion.VERSION_71.equals(
			portalVersion);
	}

	public static boolean isLiferay62() {
		return LiferayPortalVersion.VERSION_62.equals(portalVersion);
	}

	public static String getVersionFactory() {
		return versionFactory;
	}

	public static LiferayPortalVersion getPortalVersion() {
		return portalVersion;
	}

	public static void setPortalVersion(LiferayPortalVersion portalVersion) {
		LiferayServerContext.portalVersion = portalVersion;
	}

	public static OkHttpClient getOkHttpClient() {
		synchronized (LiferayServerContext.class) {
			if (okHttpClient == null) {
				okHttpClient = new OkHttpClient();
				okHttpClient.setCache(new Cache(LiferayScreensContext.getContext().getCacheDir(), MAX_SIZE));
				okHttpClient.interceptors().add(new Interceptor() {
					@Override
					public Response intercept(Chain chain) throws IOException {
						Request originalRequest = chain.request();

						Request.Builder newRequestBuilder = originalRequest.newBuilder();

						Request newRequest = authenticateRequestIfNeeded(newRequestBuilder);

						return chain.proceed(newRequest);
					}
				});
			}
		}
		return okHttpClient;
	}

	public static OkHttpClient getOkHttpClientNoCache() {
		OkHttpClient noCacheClient = getOkHttpClient().clone();
		noCacheClient.interceptors().add(new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request originalRequest = chain.request();

				Request.Builder newRequestBuilder =
					originalRequest.newBuilder().cacheControl(CacheControl.FORCE_NETWORK);

				Request newRequest = authenticateRequestIfNeeded(newRequestBuilder);

				return chain.proceed(newRequest);
			}
		});

		return noCacheClient;
	}

	private static long getValueFromIntegerOrString(final Resources resources, final int stringId, int integerId) {
		return integerId == 0 ? Long.parseLong(resources.getString(stringId)) : resources.getInteger(integerId);
	}

	public static Request authenticateRequestIfNeeded(Request.Builder builder) {
		if (SessionContext.getAuthentication() instanceof CookieAuthentication) {

			Map<String, String> headers = getAuthHeaders();
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				builder.addHeader(entry.getKey(), entry.getValue());
			}
		}

		return builder.build();
	}

	public static Map<String, String> getAuthHeaders() {
		try {
			com.liferay.mobile.android.http.Request authRequest =
				new com.liferay.mobile.android.http.Request(null, null, null, null, 0);

			SessionContext.getAuthentication().authenticate(authRequest);

			return authRequest.getHeaders();
		} catch (Exception e) {
			return new HashMap<>();
		}
	}
}
