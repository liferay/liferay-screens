package com.liferay.mobile.screens.viewsets.defaultviews.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.CordovaLifeCycleObserver;
import com.liferay.mobile.screens.viewsets.defaultviews.web.cordova.ScreensCordovaWebView;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.web.util.InjectableScript;
import com.liferay.mobile.screens.web.view.WebViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarai Díaz García
 * @author Víctor Galán
 */

public class WebView extends FrameLayout implements WebViewModel, ScreensWebView.Listener {

    private BaseScreenlet screenlet;
    private android.webkit.WebView webView;
    private ScreensWebView screensWebView;
    private ProgressBar progressBar;
    private static final String URL_LOGIN = "/c/portal/login";
    private List<InjectableScript> scriptsToInject = new ArrayList<>();

    public WebView(Context context) {
        super(context);
    }

    public WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        progressBar = findViewById(R.id.liferay_portlet_progress);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (screensWebView != null) {
            screensWebView.onAttached();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (screensWebView != null) {
            screensWebView.onDetached();
        }
    }

    @Override
    public void showStartOperation(String actionName) {
        if (progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
        if (webView != null) {
            webView.setVisibility(GONE);
        }
    }

    @Override
    public void injectScript(InjectableScript script) {
        injectScript("window.currentFile = '" + script.getName() + "';");
        injectScript(script.getContent());
    }

    @Override
    public void addScript(InjectableScript script) {
        scriptsToInject.add(script);
    }

    public void injectScript(final String js) {
        String wrapped = "javascript:(function() {" + js + "})()";
        webView.loadUrl(wrapped);
    }

    @Override
    public void postUrl(String url, String body) {
        if (webView != null) {
            webView.postUrl(url, body.getBytes());
        }
    }

    @Override
    public void loadUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        if (progressBar != null) {
            progressBar.setVisibility(GONE);
        }
        if (webView != null) {
            webView.setVisibility(VISIBLE);
        }

        LiferayLogger.e("Error with portlet", e);
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new UnsupportedOperationException(
            "showFinishOperation(String) is not supported." + " Use showFinishOperation(String, String) instead.");
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
    public void configureView(boolean isCordovaEnabled, CordovaLifeCycleObserver observer) {
        if (screensWebView != null) {
            return;
        }

        if (isCordovaEnabled) {
            screensWebView =
                new ScreensCordovaWebView(LiferayScreensContext.getActivityFromContext(getContext()), observer);
        } else {
            screensWebView = new ScreensNativeWebView(LiferayScreensContext.getActivityFromContext(getContext()));
        }

        screensWebView.setListener(this);

        webView = screensWebView.getView();
        webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT));

        // Disable selection in webView
        webView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                long contentLength) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(url), mimetype);

                Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
                activity.startActivity(intent);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new PortletDisplayInterface(), "android");

        addView(webView);
    }

    @Override
    public void setScrollEnabled(boolean enabled) {
        if (enabled) {
            webView.setOnTouchListener(null);
        } else {
            webView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return (event.getAction() == MotionEvent.ACTION_MOVE);
                }
            });
        }
    }

    @Override
    public void clearCache() {
        webView.clearCache(true);
    }

    @Override
    public android.webkit.WebView getWebView() {
        return webView;
    }

    @Override
    public void onPageStarted() {
        webView.setVisibility(GONE);
        if (progressBar != null) {
            progressBar.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageFinished(String url) {
        if (!url.contains(URL_LOGIN)) {

            for (InjectableScript script : scriptsToInject) {
                injectScript(script);
            }

            // The webview shows a white screen before loading its content, this prevent that.
            webView.setAlpha(0);
            webView.setVisibility(VISIBLE);
            webView.animate().setStartDelay(200).alpha(1.0f);

            if (progressBar != null) {
                progressBar.setVisibility(GONE);
            }

            ((WebScreenlet) getScreenlet()).onPageLoaded(url);
        }
    }

    @Override
    public void onPageError(Exception e) {
        ((WebScreenlet) getScreenlet()).error(e, WebScreenlet.DEFAULT_ACTION);
    }

    private class PortletDisplayInterface {

        private PortletDisplayInterface() {
        }

        @JavascriptInterface
        public void postMessage(String namespace, String body) {
            ((WebScreenlet) getScreenlet()).onScriptMessageHandler(namespace, body);
        }
    }
}
