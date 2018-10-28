package com.liferay.mobile.screens.viewsets.defaultviews.web.cordova;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import org.apache.cordova.engine.SystemWebViewClient;
import org.apache.cordova.engine.SystemWebViewEngine;

/**
 * @author Víctor Galán Grande
 */

public class ScreensCordovaWebViewClient extends SystemWebViewClient {

    public ScreensCordovaWebViewClient(SystemWebViewEngine parentEngine) {
        super(parentEngine);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (error.getPrimaryError() == SslError.SSL_EXPIRED) {
            handler.cancel();
        } else {
            handler.proceed();
        }
    }
}
