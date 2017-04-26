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

package com.liferay.mobile.screens.testapp.customviews;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.viewsets.defaultviews.webcontent.display.WebContentDisplayView;

/**
 * @author Sarai Díaz García
 */

public class WebContentCustomCssDisplayView extends WebContentDisplayView {

	public WebContentCustomCssDisplayView(Context context) {
		super(context);
	}

	public WebContentCustomCssDisplayView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public WebContentCustomCssDisplayView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public String getCustomCssStyle() {
		return "<style>.MobileCSS {padding: 4%; width: 92%;}</style>";
	}
}
