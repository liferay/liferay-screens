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

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;


public abstract class BaseLoginInteractor
	extends BaseRemoteInteractor<LoginListener>
	implements LoginInteractor {

	public BaseLoginInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void onEvent(JSONObjectEvent event) {
		System.out.println("event = [" + event + "]");
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			SessionContext.clearSession();
			getListener().onLoginFailure(event.getException());
		}
		else {
			User user = new User(event.getJSONObject());
			SessionContext.setLoggedUser(user);
			getListener().onLoginSuccess(user);
		}
	}

	public abstract void login() throws Exception;

}