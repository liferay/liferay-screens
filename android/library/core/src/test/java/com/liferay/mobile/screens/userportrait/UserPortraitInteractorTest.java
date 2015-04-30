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

import com.liferay.mobile.screens.BuildConfig;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadInteractorImpl;
import com.liferay.mobile.screens.util.MockFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class UserPortraitInteractorTest {

	public static final String LIBRARY_CORE_SRC_MAIN_ANDROID_MANIFEST_XML = "../src/main/AndroidManifest.xml";

	@RunWith(RobolectricGradleTestRunner.class)
	@Config(constants = BuildConfig.class, emulateSdk = 21, manifest = LIBRARY_CORE_SRC_MAIN_ANDROID_MANIFEST_XML)
	public static class WhenLoadingFromPortraitId {

		@Before
		public void setUp() {
			LiferayScreensContext.init(RuntimeEnvironment.application);
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenListenerIsNull() throws Exception {
			UserPortraitLoadInteractorImpl interactor = new UserPortraitLoadInteractorImpl(0);

			try {
				interactor.load(true, 123, "xxx");
			}
			catch (IllegalArgumentException e) {
				Assert.assertEquals("Listener cannot be empty", e.getMessage());
			}
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenPortraitIdIsZero() throws Exception {
			UserPortraitLoadInteractorImpl interactor = new UserPortraitLoadInteractorImpl(0);
			interactor.onScreenletAttachted(MockFactory.mockUserPortraitInteractorListener());

			try {
				interactor.load(true, 0, "xxx");
			}
			catch (IllegalArgumentException e) {
				Assert.assertEquals("portraitId cannot be empty", e.getMessage());
			}
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenUUIDIsNull() throws Exception {
			UserPortraitLoadInteractorImpl interactor = new UserPortraitLoadInteractorImpl(0);
			interactor.onScreenletAttachted(MockFactory.mockUserPortraitInteractorListener());

			try {
				interactor.load(true, 123, null);
			}
			catch (IllegalArgumentException e) {
				Assert.assertEquals("userId cannot be empty", e.getMessage());
			}
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenUUIDIsEmpty() throws Exception {
			UserPortraitLoadInteractorImpl interactor = new UserPortraitLoadInteractorImpl(0);
			interactor.onScreenletAttachted(MockFactory.mockUserPortraitInteractorListener());

			try {
				interactor.load(true, 123, "");
			}
			catch (IllegalArgumentException e) {
				Assert.assertEquals("userId cannot be empty", e.getMessage());
			}
		}

	}

}