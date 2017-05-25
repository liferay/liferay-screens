package com.liferay.mobile.screens.viewsets.defaultviews.portlet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.portlet.view.PortletDisplayViewModel;
import com.liferay.mobile.screens.util.AssetReader;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.DetailImageActivity;
import java.util.List;

/**
 * @author Sarai Díaz García
 */

public class PortletDisplayView extends FrameLayout implements PortletDisplayViewModel {

	private BaseScreenlet screenlet;
	private WebView webView;
	private ProgressBar progressBar;
	private boolean biggerPagination;

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
	public void showFinishOperation(String url, final String injectedJs, final String injectedCss) {
		if (webView != null) {
			webView.getSettings().setJavaScriptEnabled(true);
			webView.addJavascriptInterface(new PortletDisplayInterface(getContext()), "android");

			webView.loadUrl(url);

			webView.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					view.setVisibility(VISIBLE);
					progressBar.setVisibility(GONE);

					String biggerPaginationStyle = "";

					if (biggerPagination) {
						biggerPaginationStyle = AssetReader.read(getContext(), R.raw.bigger_pagination);
					}

					//TODO check if it's mandatory to load css first and then js
					webView.loadUrl("javascript:document.getElementsByTagName('html')[0].innerHTML += '<style>" +
						injectedCss + "</style>';" + biggerPaginationStyle + injectedJs);
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
	public void setBiggerPagination(boolean biggerPagination) {
		this.biggerPagination = biggerPagination;
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

	public class PortletDisplayInterface {
		Context context;
		String[] allImgSrc;

		PortletDisplayInterface(Context c) {
			context = c;
		}

		@JavascriptInterface
		public void setAllImgSrc(String[] allImgSrc) {
			this.allImgSrc = allImgSrc;
		}

		@JavascriptInterface
		public void showItem(final int srcPosition) {
			Intent intent = new Intent(getContext(), DetailImageActivity.class);
			intent.putExtra("imgSrcPosition", srcPosition);
			intent.putExtra("allImgSrc", allImgSrc);
			Activity activity = LiferayScreensContext.getActivityFromContext(getContext());
			activity.startActivity(intent);
		}
	}
}
