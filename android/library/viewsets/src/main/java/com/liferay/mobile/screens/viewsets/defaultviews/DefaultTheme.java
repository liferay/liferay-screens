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

package com.liferay.mobile.screens.viewsets.defaultviews;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;

import com.liferay.mobile.screens.viewsets.R;

/**
 * @author Javier Gamarra
 */
public class DefaultTheme {

	public static void initIfThemeNotPresent(Context context) {
		TypedValue outValue = new TypedValue();
		context.getTheme().resolveAttribute(ATTRIBUTE_TO_SEARCH_FOR, outValue, true);
		if (outValue.coerceToString() == null) {
			ContextThemeWrapper w = new ContextThemeWrapper(context, DefaultTheme.DEFAULT_THEME);
			context.getTheme().setTo(w.getTheme());
		}
	}

	public static final int ATTRIBUTE_TO_SEARCH_FOR = R.attr.colorPrimary;
	public static final Integer DEFAULT_THEME = R.style.default_theme;
}
