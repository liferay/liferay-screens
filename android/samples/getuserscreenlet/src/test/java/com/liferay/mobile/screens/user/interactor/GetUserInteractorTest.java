/*
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
package com.liferay.mobile.screens.user.interactor;

import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Mounir Hallab
 */
@RunWith(Enclosed.class)
public class GetUserInteractorTest {

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenGetUser {

        @Test(expected = IllegalArgumentException.class)
        public void shouldRaiseIllegalArgumentException() throws Exception {
            GetUserInteractor interactor = new GetUserInteractor();

            interactor.execute(null, "xxx");
        }
    }

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenGetUserWithAttribute {
        private GetUserInteractor interactorSpy;
        private UserConnector serviceMock;

        @Before
        public void setup() {
            interactorSpy = Mockito.spy(new GetUserInteractor());
            serviceMock = Mockito.mock(UserConnector.class);

            Mockito.doReturn(serviceMock).when(interactorSpy).getUserConnector();
        }

        @Test
        public void shouldCallGetUserByEmailAddressByDefault() throws Exception {
            interactorSpy.execute("user", null);

            Mockito.verify(serviceMock).getUserByEmailAddress(0, "user");
        }

        @Test
        public void shouldCallGetUserByUserId() throws Exception {
            interactorSpy.execute("1", "userId");

            Mockito.verify(serviceMock).getUserById(1);
        }

        @Test
        public void shouldCallGetUserByScreenName() throws Exception {
            interactorSpy.execute("user", "screenName");

            Mockito.verify(serviceMock).getUserByScreenName(0, "user");
        }

        @Test
        public void shouldCallGetUserByEmailAddress() throws Exception {
            interactorSpy.execute("user", "emailAddress");

            Mockito.verify(serviceMock).getUserByEmailAddress(0, "user");
        }
    }
}