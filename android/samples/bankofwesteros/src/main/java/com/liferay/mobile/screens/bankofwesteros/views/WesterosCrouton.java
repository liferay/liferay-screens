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

package com.liferay.mobile.screens.bankofwesteros.views;

import android.app.Activity;
import android.content.Context;

import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.viewsets.material.MaterialCrouton;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @author Javier Gamarra
 */
public class WesterosCrouton extends MaterialCrouton {

	public static final int HEIGHT = 250;

	public static Configuration CONFIGURATION = new Configuration.Builder().setInAnimation(R.anim.slide_up).setOutAnimation(R.anim.slide_down).build();
	public static Style INFO = new Style.Builder().setHeight(HEIGHT).setBackgroundColor(R.color.material_primary_dark).setConfiguration(CONFIGURATION).build();
	public static Style ALERT = new Style.Builder().setHeight(HEIGHT).setBackgroundColor(R.color.westeros_yellow).setConfiguration(CONFIGURATION).build();

	public static int POSITION = R.id.crouton_view_anchor;

	public static void error(Context context, String message, Exception e) {
		String error = message;
		if (e instanceof IllegalArgumentException) {
			//TODO create validation exception
			error = e.getMessage();
		}
		Activity activity = getContextFromActivity(context);
		Crouton.showText(activity, error, ALERT, POSITION);
	}

	public static void info(Context context, String message) {
		Activity activity = getContextFromActivity(context);
		Crouton.showText(activity, message, INFO, POSITION);
	}

}
