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

package com.liferay.mobile.screens.viewsets.material;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultCrouton;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @author Javier Gamarra
 */
public class MaterialCrouton extends DefaultCrouton{

	public static final Style INFO;
	public static final Style ALERT;

	static {
		INFO = new Style.Builder().setBackgroundColor(R.color.material_primary).build();
		ALERT = new Style.Builder().setBackgroundColor(R.color.liferay_red).build();
	}

	public static void error(Context context, String message, Exception e) {
		String error = message;
		if (e instanceof IllegalArgumentException) {
			//TODO create validation exception
			error = e.getMessage();
		}
		Activity activity = getContextFromActivity(context);
		Crouton.showText(activity, error, MaterialCrouton.ALERT, android.R.id.content);
	}

	public static void info(Context context, String message) {
		Activity activity = getContextFromActivity(context);
		Crouton.showText(activity, message, MaterialCrouton.INFO, android.R.id.content);
	}

}
