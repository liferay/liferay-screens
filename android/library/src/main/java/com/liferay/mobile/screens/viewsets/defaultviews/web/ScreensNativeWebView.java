package com.liferay.mobile.screens.viewsets.defaultviews.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Víctor Galán Grande
 */

public class ScreensNativeWebView extends WebViewClient implements ScreensWebView {

    private ScreensWebView.Listener listener;
    private WebView webView;

    public ScreensNativeWebView(Context context) {
        webView = new WebView(context);
        webView.setWebViewClient(this);
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public WebView getView() {
        return webView;
    }

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onAttached() {

    }

    @Override
    public void onDetached() {

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

        if (listener != null) {
            listener.onPageStarted();
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if (listener != null) {
            listener.onPageFinished(url);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);

        if (listener != null) {
            listener.onPageError(new Exception(error.getDescription().toString()));
        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);

        if (listener != null) {
            listener.onPageError(new Exception(description));
        }
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
