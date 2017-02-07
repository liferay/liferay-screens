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

package com.liferay.mobile.screens.webcontent.display;

import android.view.MotionEvent;
import android.webkit.WebView;
import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.ddl.model.DDMStructure;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Jose Manuel Navarro
 */
public interface WebContentDisplayListener extends BaseCacheListener {

	/**
	 * Called when the web content’s HTML or {@link DDMStructure} is received.
	 * The HTML is available by calling the {@link WebContent#getHtml} method.
	 * To make some adaptations, the listener may return a modified version
	 * of the HTML. The original HTML is rendered if the listener returns `null`.
	 *
	 * @param html original {@link WebContent}
	 * @return modified or not {@link WebContent}
	 */
	WebContent onWebContentReceived(WebContent html);

	/**
	 * Called when something is clicked in the WebContent.
	 */
	void onWebContentClicked(WebView.HitTestResult result, MotionEvent event);
}