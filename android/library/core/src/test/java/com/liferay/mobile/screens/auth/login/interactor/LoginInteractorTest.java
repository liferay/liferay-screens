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

package com.liferay.mobile.screens.auth.login.interactor;

import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.MockFactory;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Silvio Santos
 */
@RunWith(Enclosed.class)
public class LoginInteractorTest {

	@Config(constants = BuildConfig.class, emulateSdk = 18)
	@RunWith(RobolectricGradleTestRunner.class)
	public static class WhenAuthMethodIsEmail {

		@Test
		public void shouldCallGetUserByEmailService() throws Exception {
			LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			UserService serviceMock = MockFactory.mockUserService();

			doReturn(
				serviceMock
			).when(
				interactorSpy
			).getUserService(_LOGIN_EMAIL, _LOGIN_PASSWORD);

			interactorSpy.login(
				_LOGIN_EMAIL, _LOGIN_PASSWORD, AuthMethod.EMAIL);

			verify(
				interactorSpy
			).sendGetUserByEmailRequest(serviceMock, _LOGIN_EMAIL);

			verify(
				serviceMock
			).getUserByEmailAddress(_companyId, _LOGIN_EMAIL);
		}
	}

	@RunWith(RobolectricTestRunner.class)
	@Config(constants = BuildConfig.class, emulateSdk = 18)
	public static class WhenAuthMethodIsId {

		@Test
		public void shouldCallGetUserByIdService() throws Exception {
			LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			UserService serviceMock = MockFactory.mockUserService();

			String userId = String.valueOf(_LOGIN_USER_ID);

			doReturn(
				serviceMock
			).when(
				interactorSpy
			).getUserService(userId, _LOGIN_PASSWORD);

			interactorSpy.login(userId, _LOGIN_PASSWORD, AuthMethod.USER_ID);

			verify(
				interactorSpy
			).sendGetUserByIdRequest(serviceMock, _LOGIN_USER_ID);

			verify(
				serviceMock
			).getUserById(_LOGIN_USER_ID);
		}
	}

	@RunWith(RobolectricTestRunner.class)
	@Config(constants = BuildConfig.class, emulateSdk = 18)
	public static class WhenAuthMethodIsScreenName {

		@Test
		public void shouldCallGetUserByScreenNameService() throws Exception {
			LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			UserService serviceMock = MockFactory.mockUserService();

			doReturn(
				serviceMock
			).when(
				interactorSpy
			).getUserService(_LOGIN_SCREEN_NAME, _LOGIN_PASSWORD);

			interactorSpy.login(
				_LOGIN_SCREEN_NAME, _LOGIN_PASSWORD, AuthMethod.SCREEN_NAME);

			verify(
				interactorSpy
			).sendGetUserByScreenNameRequest(serviceMock, _LOGIN_SCREEN_NAME);

			verify(
				serviceMock
			).getUserByScreenName(_companyId, _LOGIN_SCREEN_NAME);
		}
	}

	@RunWith(RobolectricTestRunner.class)
	@Config(constants = BuildConfig.class, emulateSdk = 18)
	public static class WhenLoginMethodIsCalled {

		@Test
		public void shouldCallValidate() throws Exception {
			LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			doReturn(
				MockFactory.mockUserService()
			).when(
				interactorSpy
			).getUserService(_LOGIN_EMAIL, _LOGIN_PASSWORD);

			interactorSpy.login(
				_LOGIN_EMAIL, _LOGIN_PASSWORD, AuthMethod.EMAIL);

			verify(
				interactorSpy
			).validate(_LOGIN_EMAIL, _LOGIN_PASSWORD, AuthMethod.EMAIL);
		}
	}

	@RunWith(RobolectricTestRunner.class)
	@Config(constants = BuildConfig.class, emulateSdk = 18)
	public static class WhenLoginRequestCompletes {

		@Test
		public void shouldCallListenerSuccess() throws Exception {
			LoginListener listener = MockFactory.mockLoginListener();
			JSONObject result = new JSONObject();
			JSONObjectEvent event = new JSONObjectEvent(
				_TARGET_SCREENLET_ID, result);

			_loginWithResponseEvent(event, listener);

			verify(listener).onLoginSuccess(any(User.class));
		}

		@Test
		public void shouldCallListenerFailure() throws Exception {
			LoginListener listener = MockFactory.mockLoginListener();
			Exception e = new Exception();
			JSONObjectEvent event = new JSONObjectEvent(
				_TARGET_SCREENLET_ID, e);

			_loginWithResponseEvent(event, listener);

			verify(
				listener
			).onLoginFailure(e);
		}

		private void _loginWithResponseEvent(
			final JSONObjectEvent event, LoginListener listener)
			throws Exception {

			final LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			UserService serviceMock = MockFactory.mockUserService();

			doReturn(
				serviceMock
			).when(
				interactorSpy
			).getUserService(_LOGIN_EMAIL, _LOGIN_PASSWORD);

			interactorSpy.onScreenletAttachted(listener);

			when(
				serviceMock.getUserByEmailAddress(_companyId, _LOGIN_EMAIL)
			).then(
				new Answer<Void>() {

					@Override
					public Void answer(InvocationOnMock invocation)
						throws Throwable {

						interactorSpy.onEvent(event);

						return null;
					}
				});

			interactorSpy.login(
				_LOGIN_EMAIL, _LOGIN_PASSWORD, AuthMethod.EMAIL);
		}
	}

	@RunWith(RobolectricTestRunner.class)
	@Config(constants = BuildConfig.class, emulateSdk = 18)
	public static class WhenValidateMethodIsCalled {

		@Test(expected = IllegalArgumentException.class)
		public void shouldRaiseExceptionOnNullLogin() {
			LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			interactorSpy.validate(null, _LOGIN_PASSWORD, AuthMethod.EMAIL);
		}

		@Test(expected = IllegalArgumentException.class)
		public void shouldRaiseExceptionOnNullPassword() {
			LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			interactorSpy.validate(_LOGIN_EMAIL, null, AuthMethod.EMAIL);
		}

		@Test(expected = IllegalArgumentException.class)
		public void shouldRaiseExceptionOnNullAuthMethod() {
			LoginInteractorImpl interactorSpy =
				MockFactory.spyLoginInteractor(_TARGET_SCREENLET_ID);

			interactorSpy.validate(_LOGIN_EMAIL, _LOGIN_PASSWORD, null);
		}
	}

	private static final int _TARGET_SCREENLET_ID = 0;

	private static final String _LOGIN_EMAIL = "test@liferay.com";

	private static final String _LOGIN_PASSWORD = "test";

	private static final String _LOGIN_SCREEN_NAME = "test_screen_name";

	private static final long _LOGIN_USER_ID = 10658;

	private static final long _companyId = LiferayServerContext.getCompanyId();

}