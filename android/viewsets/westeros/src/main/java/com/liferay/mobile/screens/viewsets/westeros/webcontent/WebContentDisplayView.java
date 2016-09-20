package com.liferay.mobile.screens.viewsets.westeros.webcontent;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.westeros.R;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;
import com.liferay.mobile.screens.webcontent.display.view.WebContentDisplayViewModel;

/**
 * @author Sarai Díaz García
 */
public class WebContentDisplayView extends FrameLayout implements WebContentDisplayViewModel, View.OnTouchListener {

	private static final String STYLES = "<style>"
		+ ".MobileCSS { margin: 0 auto; width:92%; color: white;} "
		+ ".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, "
		+ ".MobileCSS h2, .MobileCSS h3{ "
		+ "font-size: 110%; font-weight: 200;"
		+ "font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;} "
		+ ".MobileCSS img { width: 100% !important; } "
		+ ".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }"
		+ "</style>";
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
	public void showFinishOperation(WebContent webContent) {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}
		if (webView != null) {
			webView.setVisibility(View.VISIBLE);

			LiferayLogger.i("article loaded: " + webContent);

			String styledHtml = STYLES + "<div class=\"MobileCSS\">" + webContent.getHtml() + "</div>";

			//TODO check encoding
			webView.loadDataWithBaseURL(LiferayServerContext.getServer(), styledHtml, "text/html", "utf-8", null);
			webView.setBackgroundColor(Color.TRANSPARENT);
			webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
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

		LiferayLogger.e(getContext().getString(com.liferay.mobile.screens.R.string.loading_article_error), e);
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
		WebView.HitTestResult result = webView.getHitTestResult();
		((WebContentDisplayScreenlet) getScreenlet()).onWebContentClicked(result, event);
		return false;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		webView = (WebView) findViewById(R.id.liferay_webview);
		progressBar = (ProgressBar) findViewById(R.id.liferay_webview_progress);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		WebContentDisplayScreenlet screenlet = (WebContentDisplayScreenlet) getScreenlet();
		if (webView != null) {
			if (screenlet.isJavascriptEnabled()) {
				webView.getSettings().setJavaScriptEnabled(true);
				webView.setWebChromeClient(new WebChromeClient());
			}
			webView.setOnTouchListener(this);
		}
	}
}