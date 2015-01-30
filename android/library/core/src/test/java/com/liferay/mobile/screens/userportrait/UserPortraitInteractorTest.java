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

package com.liferay.mobile.screens.userportrait;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorImpl;
import com.liferay.mobile.screens.util.MockFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
@RunWith(Enclosed.class)
public class UserPortraitInteractorTest {

	@Config(emulateSdk = 18)
	@RunWith(RobolectricTestRunner.class)
	public static class WhenLoadingFromPortraitId {

		@Before
		public void setUp() {
			LiferayScreensContext.init(Robolectric.application);
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenListenerIsNull() throws Exception {
			UserPortraitInteractorImpl interactor = new UserPortraitInteractorImpl();

			try {
				interactor.load(true, 123, "xxx");
			} catch(IllegalArgumentException e) {
				Assert.assertEquals("Listener cannot be null", e.getMessage());
			}
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenPortraitIdIsZero() throws Exception {
			UserPortraitInteractorImpl interactor = new UserPortraitInteractorImpl();
			interactor.onScreenletAttachted(MockFactory.mockPicassoTargetListener());

			try {
				interactor.load(true, 0, "xxx");
			} catch(IllegalArgumentException e) {
				Assert.assertEquals("portraitId cannot be null", e.getMessage());
			}
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenUUIDIsNull() throws Exception {
			UserPortraitInteractorImpl interactor = new UserPortraitInteractorImpl();
			interactor.onScreenletAttachted(MockFactory.mockPicassoTargetListener());

			try {
				interactor.load(true, 123, null);
			} catch(IllegalArgumentException e) {
				Assert.assertEquals("userId cannot be null or empty", e.getMessage());
			}
		}

		@Test
		public void shouldRaiseInvalidArgumentWhenUUIDIsEmpty() throws Exception {
			UserPortraitInteractorImpl interactor = new UserPortraitInteractorImpl();
			interactor.onScreenletAttachted(MockFactory.mockPicassoTargetListener());

			try {
				interactor.load(true, 123, "");
			} catch(IllegalArgumentException e) {
				Assert.assertEquals("userId cannot be null or empty", e.getMessage());
			}
		}

	}

}