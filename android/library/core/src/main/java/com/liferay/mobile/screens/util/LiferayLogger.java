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

import android.util.Log;

import com.liferay.mobile.screens.BuildConfig;

/**
 * @author Javier Gamarra
 */
public class LiferayLogger {

	public static void d(String message) {
		if (loggingEnabled()) {
			Log.d(TAG, message);
		}
	}

	public static void i(String message) {
		if (loggingEnabled()) {
			Log.i(TAG, message);
		}
	}

	public static void e(String message) {
		if (loggingEnabled()) {
			Log.e(TAG, message);
		}
	}

	public static void e(String message, Exception e) {
		if (loggingEnabled()) {
			Log.e(TAG, message, e);
		}
	}

	private static boolean loggingEnabled() {
		return BuildConfig.DEBUG || LOGGING_ENABLED;
	}

	private static final boolean LOGGING_ENABLED = true;
	private static final String TAG = "LiferayScreens";
}
