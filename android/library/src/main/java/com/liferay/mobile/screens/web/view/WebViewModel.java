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

package com.liferay.mobile.screens.web.view;

import android.webkit.WebView;
import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.CordovaLifeCycleObserver;
import com.liferay.mobile.screens.web.util.InjectableScript;

/**
 * @author Sarai Díaz García
 */
public interface WebViewModel extends BaseViewModel {

    /**
     * Loads the url on the WebView using a POST request {@link WebView}
     *
     * @param url page url.
     * @param body body of the post request
     */
    void postUrl(String url, String body);

    /**
     * Loads the url on the WebView {@link WebView}
     *
     * @param url page url.
     */
    void loadUrl(String url);

    /**
     * Inject script into the page loaded. This method only works when the page is loaded
     *
     * @param script {@link InjectableScript}.
     */
    void injectScript(InjectableScript script);

    /**
     * Add script to be injected when the page is loaded
     *
     * @param script script to be injected after the page is loaded
     */
    void addScript(InjectableScript script);

    /**
     * Create the WebView. Depending on the isCordovaEnabled flag it will create a normal WebView or
     * a Cordova WebView.
     *
     * @param isCordovaEnabled whether or not cordova is enabled in the screenlet
     * @param observer special object that will notify the cordova webview about the activity
     * lifecycle methods
     */
    void configureView(boolean isCordovaEnabled, CordovaLifeCycleObserver observer);

    /**
     * Controls wether or not the scroll of the webview should be enabled
     */
    void setScrollEnabled(boolean enabled);

    /**
     * Clear the webView cache
     */
    void clearCache();

    /*
     * Grab the webView, this method has to be called after the configuration is set.
     */
    WebView getWebView();
}
