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

package com.liferay.mobile.screens.themes.webcontentdisplay;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.liferay.mobile.screens.base.BaseViewModel;
import com.liferay.mobile.screens.themes.R;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;

/**
 * @author Silvio Santos
 */
public class WebContentDisplayScreenletView extends FrameLayout
	implements BaseViewModel, WebContentDisplayListener {

	public WebContentDisplayScreenletView(Context context) {
		this(context, null);
	}

	public WebContentDisplayScreenletView(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public WebContentDisplayScreenletView(
			Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	public String onWebContentReceived(String html) {
		String styledHtml = STYLES + "<div class=\"MobileCSS\">" + html + "</div>";

		_webView.loadDataWithBaseURL(LiferayServerContext.getServer(), styledHtml,
				"text/html",
				"utf-8",	 //TODO <- really?
				null);

		return html;
	}

	@Override
	public void onWebContentFailure(Exception e) {
		//TODO show load error to user??
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_webView = (WebView) findViewById(R.id.web_view);
	}

	private static final String STYLES =
		"<style>" +
		".MobileCSS {padding: 4%; width: 92%;} " +
		".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, .MobileCSS h2, .MobileCSS h3{ " +
		"font-size: 110%; font-weight: 200;" +
			"font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;} " +
		".MobileCSS img { width: 100% !important; } " +
		".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }" +
		"</style>";

	private WebView _webView;

}