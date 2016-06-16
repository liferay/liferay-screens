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

package com.liferay.mobile.screens.viewsets.westeros.userportrait;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Javier Gamarra
 */
public class UserPortraitView extends com.liferay.mobile.screens.viewsets.defaultviews.userportrait.UserPortraitView {

	public UserPortraitView(Context context) {
		super(context);
	}

	public UserPortraitView(
		Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public UserPortraitView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected int getDefaultBorderColor() {
		return R.color.colorPrimary_westeros;
	}

	@Override
	protected float getBorderWidth() {
		return 1f;
	}
}