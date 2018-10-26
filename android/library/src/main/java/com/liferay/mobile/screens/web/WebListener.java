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

package com.liferay.mobile.screens.web;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;

/**
 * @author Sarai Díaz García
 */
public interface WebListener extends BaseCacheListener {

    /**
     * Called when the screenlet loads the page correctly.
     */
    void onPageLoaded(String url);

    /**
     * Called when we want to notify a message from the WKWebView used in the view.
     *
     * @param namespace source namespace key.
     * @param body source namespace body
     */
    void onScriptMessageHandler(String namespace, String body);
}
