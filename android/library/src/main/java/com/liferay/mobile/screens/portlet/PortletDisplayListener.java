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

package com.liferay.mobile.screens.portlet;

import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.portlet.util.CssScript;
import com.liferay.mobile.screens.portlet.util.InjectableScript;
import com.liferay.mobile.screens.portlet.util.JsScript;

/**
 * @author Sarai Díaz García
 */
public interface PortletDisplayListener extends BaseCacheListener {

	/**
	 * Called when the screenlet loads the portlet correctly.
	 */
	void onRetrievePortletSuccess(String url);

	/**
	 * Called when we want to notify a message from the WKWebView used in the view.
	 *
	 * @param namespace source namespace key.
	 * @param body source namespace body
	 */
	void onScriptMessageHandler(String namespace, String body);

	/**
	 * Called this method when we want to search the css file to inject in the portlet
	 * when the {@link PortletConfiguration#isThemeEnabled} is on.
	 * It's not necessary the same filename as the portlet. For example:
	 * Portlet name: "com_liferay_document_library_web_portlet_IGDisplayPortlet"
	 * Filename: gallery.css
	 *
	 * @param portlet name of the internal portlet.
	 * @return {@link CssScript}
	 */
	InjectableScript cssForPortlet(String portlet);

	/**
	 * Called this method when we want to search the js file to inject in the portlet
	 * when the {@link PortletConfiguration#isThemeEnabled} is on.
	 * It's not necessary the same filename as the portlet. For example:
	 * Portlet name: "com_liferay_document_library_web_portlet_IGDisplayPortlet"
	 * Filename: gallery.js
	 *
	 * @param portlet name of the internal portlet.
	 * @return {@link JsScript}
	 */
	InjectableScript jsForPortlet(String portlet);
}
