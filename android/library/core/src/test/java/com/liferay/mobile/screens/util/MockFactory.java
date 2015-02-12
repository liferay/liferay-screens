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

package com.liferay.mobile.screens.util;

import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractorImpl;
import com.squareup.picasso.Target;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

/**
 * @author Silvio Santos
 */
public class MockFactory {

	public static Target mockPicassoTargetListener() {
		return mock(Target.class);
	}

	public static LoginListener mockLoginListener() {
		return mock(LoginListener.class);
	}

	public static UserService mockUserService() {
		return mock(UserService.class);
	}

	public static LoginInteractorImpl spyLoginInteractor(
		int targetScreenletId) {

		return spy(new LoginInteractorImpl(targetScreenletId));
	}

}