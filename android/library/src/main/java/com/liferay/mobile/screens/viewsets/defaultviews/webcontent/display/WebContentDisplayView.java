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

package com.liferay.mobile.screens.viewsets.defaultviews.webcontent.display;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;
import com.liferay.mobile.screens.webcontent.display.view.WebContentDisplayViewModel;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * @author Silvio Santos
 */
public class WebContentDisplayView extends FrameLayout implements WebContentDisplayViewModel, View.OnTouchListener {

    protected WebView webView;
    protected ProgressBar progressBar;
    private BaseScreenlet screenlet;

    public WebContentDisplayView(Context context) {
        super(context);
    }

    public WebContentDisplayView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public WebContentDisplayView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public void showStartOperation(String actionName) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (webView != null) {
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new AssertionError();
    }

    @Override
    public void showFinishOperation(WebContent webContent, String customCSs) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (webView != null) {
            webView.setVisibility(View.VISIBLE);

            LiferayLogger.i("article loaded: " + webContent);

            String styledHtml =
                "<style>" + customCSs + "</style>" + "<div class=\"MobileCSS\">" + webContent.getHtml() + "</div>";

            //TODO check encoding
            webView.loadDataWithBaseURL(LiferayServerContext.getServer(), styledHtml, "text/html", "utf-8", null);
        }
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (webView != null) {
            webView.setVisibility(View.VISIBLE);
        }

        LiferayLogger.e(getContext().getString(R.string.loading_article_error), e);
    }

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return ((WebContentDisplayScreenlet) getScreenlet()).onWebContentTouched(webView, event);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        webView = findViewById(R.id.liferay_webview);
        progressBar = findViewById(R.id.liferay_webview_progress);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        WebContentDisplayScreenlet screenlet = (WebContentDisplayScreenlet) getScreenlet();
        if (webView != null) {
            WebSettings webSettings = webView.getSettings();
            if (screenlet.isJavascriptEnabled()) {
                webSettings.setJavaScriptEnabled(true);
                webView.setWebChromeClient(new WebChromeClient());
            }
            webSettings.setDomStorageEnabled(true);
            webSettings.setDatabaseEnabled(true);
            webView.setWebViewClient(getWebViewClientWithCustomHeader());
            webView.setOnTouchListener(this);
        }
    }

    public WebViewClient getWebViewClientWithCustomHeader() {
        return new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return ((WebContentDisplayScreenlet) getScreenlet()).onUrlClicked(url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                return getResource(url.trim());
            }

            @Override
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return getResource(request.getUrl().toString());
            }

            private WebResourceResponse getResource(String url) {
                try {
                    OkHttpClient httpClient = LiferayServerContext.getOkHttpClientNoCache();
                    com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder().url(url);

                    Request request = builder.build();
                    com.squareup.okhttp.Response response = httpClient.newCall(request).execute();

                    MediaType mediaType = response.body().contentType();
                    String name = mediaType.charset() == null ? "UTF-8" : mediaType.charset().name();

                    return new WebResourceResponse(mediaType.type() + "/" + mediaType.subtype(), name,
                        response.body().byteStream());
                } catch (Exception e) {
                    return null;
                }
            }
        };
    }
}