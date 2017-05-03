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

package com.liferay.mobile.screens.viewsets.westeros.webcontent;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Sarai Díaz García
 */
public class WebContentDisplayView
	extends com.liferay.mobile.screens.viewsets.defaultviews.webcontent.display.WebContentDisplayView {

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
	public void showFinishOperation(WebContent webContent, String customCss) {
		if (progressBar != null) {
			progressBar.setVisibility(View.GONE);
		}
		if (webView != null) {
			webView.setVisibility(View.VISIBLE);

			LiferayLogger.i("article loaded: " + webContent);

			String styledHtml = customCss + "<div class=\"MobileCSS\">" + webContent.getHtml() + "</div>";

			//TODO check encoding
			webView.loadDataWithBaseURL(LiferayServerContext.getServer(), styledHtml, "text/html", "utf-8", null);
			webView.setBackgroundColor(Color.TRANSPARENT);
			webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		}
	}
}