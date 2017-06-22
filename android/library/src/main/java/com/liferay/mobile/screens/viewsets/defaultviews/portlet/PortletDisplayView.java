package com.liferay.mobile.screens.viewsets.defaultviews.portlet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
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
	public void showFinishOperation(View view) {
		screenlet.addView(view, LinearLayout.LayoutParams.MATCH_PARENT,
			LinearLayout.LayoutParams.MATCH_PARENT);
		webView.setVisibility(GONE);
		progressBar.setVisibility(GONE);
		screenlet.setVisibility(VISIBLE);
		LiferayLogger.d("Asset display loaded successfully");
	}

	@Override
	public void showFinishOperation(String url, String body, final String injectedJs) {
		if (webView != null) {
			webView.getSettings().setJavaScriptEnabled(true);
			webView.addJavascriptInterface(new PortletDisplayInterface(), "android");

			webView.postUrl(url, body.getBytes());

			webView.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
				}

				@Override
				public void onPageCommitVisible(WebView view, String url) {
					super.onPageCommitVisible(view, url);

					view.setVisibility(VISIBLE);
					progressBar.setVisibility(GONE);

					webView.loadUrl(injectedJs);
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
	public void showFinishOperation(String actionName) {
		throw new UnsupportedOperationException("showFinishOperation(String) is not supported."
			+ " Use showFinishOperation(String, String) instead.");
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	private class PortletDisplayInterface {

		private PortletDisplayInterface() {
		}

		@JavascriptInterface
		public void postMessage(String namespace, String body) {
			((PortletDisplayScreenlet) getScreenlet()).onScriptMessageHandler(namespace, body);
		}
	}
}
