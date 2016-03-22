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

package com.liferay.mobile.screens.webcontentdisplay;

import android.view.MotionEvent;
import android.webkit.WebView;

import com.liferay.mobile.screens.cache.CacheListener;

/**
 * @author Jose Manuel Navarro
 */
public interface WebContentDisplayListener extends CacheListener {

	String onWebContentReceived(WebContentDisplayScreenlet source, String html);

	void onWebContentFailure(WebContentDisplayScreenlet source, Exception e);

	void onWebContentClicked(WebView.HitTestResult result, MotionEvent event);

}