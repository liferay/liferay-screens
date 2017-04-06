package com.liferay.mobile.screens.viewsets.defaultviews.portlet;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.portlet.view.PortletDisplayViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Sarai Díaz García
 */

public class PortletDisplayView extends FrameLayout implements PortletDisplayViewModel {

	private BaseScreenlet screenlet;
	private WebView webView;
	private ProgressBar progressBar;

	public PortletDisplayView(Context context) {
		super(context);
	}

	public PortletDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PortletDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		webView = (WebView) findViewById(R.id.liferay_portlet_webview);

		progressBar = (ProgressBar) findViewById(R.id.liferay_portlet_progress);
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
	public void showFinishOperation(String url) {
		if (webView != null) {
			webView.getSettings().setJavaScriptEnabled(true);
			webView.loadUrl(url);

			webView.setWebViewClient(new WebViewClient() {
				public void onPageFinished(WebView view, String url) {
					view.setVisibility(VISIBLE);
					progressBar.setVisibility(GONE);
				}
			});
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
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}
}
