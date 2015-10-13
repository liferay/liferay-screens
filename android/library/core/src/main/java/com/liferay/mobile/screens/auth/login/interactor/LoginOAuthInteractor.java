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
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v62.ScreensuserService;

/**
 * @author Jose Manuel Navarro
 */
public class LoginOAuthInteractor extends BaseLoginInteractor {

	public LoginOAuthInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	public OAuthConfig getOAuthConfig() {
		return _OAuthConfig;
	}

	public void setOAuthConfig(OAuthConfig value) {
		_OAuthConfig = value;
	}

	public void login() throws Exception {
		if (_OAuthConfig == null) {
			throw new IllegalArgumentException("OAuth configuration cannot be empty");
		}

		Session session = SessionContext.createOAuthSession(_OAuthConfig);
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));

		new ScreensuserService(session).getCurrentUser();
	}

	// NOTE: this interactor can store state because this attribute
	// isn't used after the request is fired.
	private OAuthConfig _OAuthConfig;

}