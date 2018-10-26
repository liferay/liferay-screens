package com.liferay.mobile.screens.viewsets.defaultviews.web;

import android.webkit.WebView;

/**
 * @author Víctor Galán Grande
 */

public interface ScreensWebView {

    WebView getView();

    void setListener(ScreensWebView.Listener listener);

    void onAttached();

    void onDetached();

    interface Listener {

        void onPageStarted();

        void onPageFinished(String url);

        void onPageError(Exception e);
    }
}