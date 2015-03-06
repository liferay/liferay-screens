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

import android.content.Context;

import com.liferay.mobile.screens.viewsets.R;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @author Javier Gamarra
 */
public class MaterialCrouton {

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
		Crouton.makeText((android.app.Activity) context, error, MaterialCrouton.ALERT).show();
	}

	public static void info(Context context, String message) {
		Crouton.makeText((android.app.Activity) context, message, MaterialCrouton.INFO).show();
	}

}
