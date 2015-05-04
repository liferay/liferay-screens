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

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;

import com.liferay.mobile.screens.viewsets.R;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @author Javier Gamarra
 */
public class LiferayCrouton {

	public static void error(Context context, String message, Exception e) {
		if (_alert == null) {
			new Builder().withAlertColor(R.color.default_red).build();
		}
		String error = message;
		if (e instanceof IllegalArgumentException) {
			//TODO create validation exception
			error = e.getMessage();
		}
		Activity activity = getContextFromActivity(context);

		Crouton.showText(activity, error, _alert, _position);
	}

	public static void info(Context context, String message) {
		if (_info == null) {
			new Builder().withInfoColor(R.color.default_primary_blue).build();
		}
		Activity activity = getContextFromActivity(context);
		Crouton.showText(activity, message, _info, _position);
	}

	protected static Activity getContextFromActivity(Context context) {
		if (context instanceof Activity) {
			return (Activity) context;
		}
		else {
			Context baseContext = ((ContextThemeWrapper) context).getBaseContext();
			return (Activity) baseContext;
		}
	}

	private static int _position = android.R.id.content;
	private static Style _info = null;
	private static Style _alert = null;

	public static class Builder {

		public Builder withInfoColor(int infoColor) {
			_infoColor = infoColor;
			return this;
		}

		public Builder withAlertColor(int alertColor) {
			_alertColor = alertColor;
			return this;
		}

		public Builder withHeight(int height) {
			_height = height;
			return this;
		}

		public Builder locatedIn(int position) {
			_positionStyle = position;
			return this;
		}

		public Builder withConfiguration(Configuration configuration) {
			_configuration = configuration;
			return this;
		}

		public void build() {
			if (_infoColor != 0) {
				_info = createStyle(_infoColor).build();
			}
			if (_alertColor != 0) {
				_alert = createStyle(_alertColor).build();
			}
			if (_positionStyle != 0) {
				_position = _positionStyle;
			}
		}

		private Style.Builder createStyle(int color) {
			Style.Builder builder = new Style.Builder();
			builder.setBackgroundColor(color);
			if (_height != 0) {
				builder.setHeight(_height);
			}
			if (_configuration != null) {
				builder.setConfiguration(_configuration);
			}
			return builder;
		}

		private int _infoColor;
		private int _alertColor;
		private int _positionStyle;
		private int _height;
		private Configuration _configuration;
	}
}
