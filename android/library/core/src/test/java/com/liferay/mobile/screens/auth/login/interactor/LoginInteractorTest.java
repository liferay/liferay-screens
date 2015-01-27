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

import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.util.MockFactory;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

/**
 * @author Silvio Santos
 */
@RunWith(Enclosed.class)
public class LoginInteractorTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenAuthMethodIsEmail {

		@Test
		public void shouldCallGetUserByEmailService() throws Exception {
			LoginInteractorImpl interactor = MockFactory.spyLoginInteractor();
			UserService service = MockFactory.mockUserService();

			doReturn(
				service
			).when(
				interactor
			).getUserService(_LOGIN_EMAIL, _LOGIN_PASSWORD);

			interactor.login(_LOGIN_EMAIL, _LOGIN_PASSWORD, AuthMethod.EMAIL);

			verify(
				interactor
			).sendGetUserByEmailRequest(_LOGIN_EMAIL, _LOGIN_PASSWORD);

			verify(
				service
			).getUserByEmailAddress(_companyId, _LOGIN_EMAIL);
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenAuthMethodIsId {

		@Test
		public void shouldCallGetUserByIdService() throws Exception {
			LoginInteractorImpl interactor = MockFactory.spyLoginInteractor();
			UserService service = MockFactory.mockUserService();

			String userId = String.valueOf(_LOGIN_USER_ID);

			doReturn(
				service
			).when(
				interactor
			).getUserService(userId, _LOGIN_PASSWORD);

			interactor.login(userId, _LOGIN_PASSWORD, AuthMethod.USER_ID);

			verify(
				interactor
			).sendGetUserByIdRequest(_LOGIN_USER_ID, _LOGIN_PASSWORD);

			verify(
				service
			).getUserById(_LOGIN_USER_ID);
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenAuthMethodIsScreenName {

		@Test
		public void shouldCallGetUserByScreenNameService() throws Exception {
			LoginInteractorImpl interactor = MockFactory.spyLoginInteractor();
			UserService service = MockFactory.mockUserService();

			doReturn(
				service
			).when(
				interactor
			).getUserService(_LOGIN_SCREEN_NAME, _LOGIN_PASSWORD);

			interactor.login(
				_LOGIN_SCREEN_NAME, _LOGIN_PASSWORD, AuthMethod.SCREEN_NAME);

			verify(
				interactor
			).sendGetUserByScreenName(_LOGIN_SCREEN_NAME, _LOGIN_PASSWORD);

			verify(
				service
			).getUserByScreenName(_companyId, _LOGIN_SCREEN_NAME);
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenLoginMethodIsCalled {

		@Test
		public void shouldCallValidate() {
			LoginInteractorImpl interactor = MockFactory.spyLoginInteractor();

			interactor.login(_LOGIN_EMAIL, _LOGIN_PASSWORD, AuthMethod.EMAIL);

			verify(
				interactor
			).validate(_LOGIN_EMAIL, _LOGIN_PASSWORD, AuthMethod.EMAIL);
		}
	}

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenValidateMethodIsCalled {

		@Test (expected = IllegalArgumentException.class)
		public void shouldRaiseExceptionOnNullLogin() {
			LoginInteractorImpl interactor = MockFactory.spyLoginInteractor();

			interactor.validate(null, _LOGIN_PASSWORD, AuthMethod.EMAIL);
		}

		@Test (expected = IllegalArgumentException.class)
		public void shouldRaiseExceptionOnNullPassword() {
			LoginInteractorImpl interactor = MockFactory.spyLoginInteractor();

			interactor.validate(_LOGIN_EMAIL, null, AuthMethod.EMAIL);
		}

		@Test (expected = IllegalArgumentException.class)
		public void shouldRaiseExceptionOnNullAuthMethod() {
			LoginInteractorImpl interactor = MockFactory.spyLoginInteractor();

			interactor.validate(_LOGIN_EMAIL, _LOGIN_PASSWORD, null);
		}
	}

	private static final String _LOGIN_EMAIL = "test@liferay.com";

	private static final String _LOGIN_PASSWORD = "test";

	private static final String _LOGIN_SCREEN_NAME = "test";

	private static final long _LOGIN_USER_ID = 10658;

	private static final long _companyId = LiferayServerContext.getCompanyId();

}