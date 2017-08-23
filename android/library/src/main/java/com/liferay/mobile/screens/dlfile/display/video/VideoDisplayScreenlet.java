/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.dlfile.display.video;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class VideoDisplayScreenlet extends BaseFileDisplayScreenlet {

	public VideoDisplayScreenlet(Context context) {
		super(context);
	}

	public VideoDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VideoDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public VideoDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public VideoDisplayScreenletListener getListener() {
		return ((VideoDisplayScreenletListener) listener);
	}
}
