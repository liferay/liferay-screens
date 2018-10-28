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

package com.liferay.mobile.screens.userportrait;

import android.content.Context;
import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadInteractor;
import com.liferay.mobile.screens.util.MockFactory;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class UserPortraitInteractorTest {

    @RunWith(RobolectricTestRunner.class)
    @Config(constants = BuildConfig.class, sdk = 23)
    public static class WhenLoadingFromPortraitId {

        @Before
        public void setUp() {
            Context ctx = RuntimeEnvironment.application.getApplicationContext();
            SessionContext.logout();
            //LiferayScreensContext.init(ctx);
        }

        @Test
        public void shouldRaiseInvalidArgumentWhenSessionIsNull() {
            UserPortraitLoadInteractor interactor = new UserPortraitLoadInteractor();

            try {
                interactor.execute(123L);
            } catch (Exception e) {
                Assert.assertEquals("You need to be logged in to get a session", e.getMessage());
            }
        }

        @Test
        public void shouldRaiseInvalidArgumentWhenPortraitIdIsZero() {
            UserPortraitLoadInteractor interactor = new UserPortraitLoadInteractor();
            interactor.onScreenletAttached(MockFactory.mockUserPortraitScreenlet());

            try {
                interactor.execute(true, 0L, "xxx");
            } catch (Exception e) {
                Assert.assertEquals("portraitId cannot be empty", e.getMessage());
            }
        }

        @Test
        public void shouldRaiseInvalidArgumentWhenUUIDIsNull() {
            UserPortraitLoadInteractor interactor = new UserPortraitLoadInteractor();
            interactor.onScreenletAttached(MockFactory.mockUserPortraitScreenlet());

            try {
                interactor.execute(true, 123L, null);
            } catch (Exception e) {
                Assert.assertEquals("userId cannot be empty", e.getMessage());
            }
        }

        @Test
        public void shouldRaiseInvalidArgumentWhenUUIDIsEmpty() {
            UserPortraitLoadInteractor interactor = new UserPortraitLoadInteractor();
            interactor.onScreenletAttached(MockFactory.mockUserPortraitScreenlet());

            try {
                interactor.execute(true, 123L, "");
            } catch (Exception e) {
                Assert.assertEquals("userId cannot be empty", e.getMessage());
            }
        }
    }
}
