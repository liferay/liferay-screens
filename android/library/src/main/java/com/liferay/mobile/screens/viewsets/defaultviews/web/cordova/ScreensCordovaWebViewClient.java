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
	public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler,
		SslError sslError) {
		if (sslError.hasError(SslError.SSL_EXPIRED)
			|| sslError.hasError(SslError.SSL_DATE_INVALID)
			|| sslError.hasError(SslError.SSL_IDMISMATCH)
			|| sslError.hasError(SslError.SSL_INVALID)
			|| sslError.hasError(SslError.SSL_NOTYETVALID)
			|| sslError.hasError(SslError.SSL_UNTRUSTED)) {
			sslErrorHandler.cancel();
		} else {
			super.onReceivedSslError(webView, sslErrorHandler, sslError);
		}
	}
}
