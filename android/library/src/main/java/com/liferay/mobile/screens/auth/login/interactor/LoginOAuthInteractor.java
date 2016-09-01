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

import com.liferay.mobile.android.oauth.OAuthConfig;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class LoginOAuthInteractor extends BaseLoginInteractor {

	@Override
	public BasicThreadEvent execute(Object[] args) throws Exception {

		if (config == null) {
			throw new IllegalArgumentException("OAuth configuration cannot be empty");
		}

		Session session = SessionContext.createOAuthSession(config);
		JSONObject jsonObject = ServiceProvider.getInstance().getCurrentUserConnector(session).getCurrentUser();

		return new BasicThreadEvent(jsonObject);
	}

	public OAuthConfig getOAuthConfig() {
		return config;
	}

	public void setOAuthConfig(OAuthConfig config) {
		this.config = config;
	}

	// NOTE: this interactor can store state because this attribute
	// isn't used after the request is fired.
	private OAuthConfig config;
}