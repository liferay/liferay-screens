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

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayScreenlet;

/**
 * @author Silvio Santos
 */
public class WebContentDisplayScreenletView extends WebView
	implements BaseViewModel, WebContentDisplayListener {

	public WebContentDisplayScreenletView(Context context) {
		super(context, null);
	}

	public WebContentDisplayScreenletView(Context context, AttributeSet attributes) {

		super(context, attributes, 0);
	}

	public WebContentDisplayScreenletView(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	public void onWebContentFailure(WebContentDisplayScreenlet source, Exception e) {
		//TODO show load error to user??
	}

	@Override
	public String onWebContentReceived(WebContentDisplayScreenlet source, String html) {
		String styledHtml =
			STYLES + "<div class=\"MobileCSS\">" + html + "</div>";

		//TODO check encoding
		loadDataWithBaseURL(
			LiferayServerContext.getServer(), styledHtml, "text/html", "utf-8",
			null);

		return html;
	}

	private static final String STYLES =
		"<style>" +
		".MobileCSS {padding: 4%; width: 92%;} " +
		".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, " +
			".MobileCSS h2, .MobileCSS h3{ " +
		"font-size: 110%; font-weight: 200;" +
			"font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;} " +
		".MobileCSS img { width: 100% !important; } " +
		".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }" +
		"</style>";

}