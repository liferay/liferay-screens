package com.liferay.mobile.screens.viewsets.defaultviews.webcontent.display;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author Sarai Díaz García
 */

public class WebContentWithCustomCssDisplayView extends WebContentDisplayView {
	public WebContentWithCustomCssDisplayView(Context context) {
		super(context);
	}

	public WebContentWithCustomCssDisplayView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public WebContentWithCustomCssDisplayView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public String getCustomCssStyle() {
		return "<style>"
			+ ".MobileCSS {padding: 4%; width: 92%;} "
			+ ".MobileCSS, .MobileCSS span, .MobileCSS p, .MobileCSS h1, "
			+ ".MobileCSS h2, .MobileCSS h3{ "
			+ "font-size: 110%; font-weight: 200;"
			+ "font-family: \"Helvetica Neue\", Helvetica, Arial, sans-serif;} "
			+ ".MobileCSS img { width: 100% !important; } "
			+ ".span2, .span3, .span4, .span6, .span8, .span10 { width: 100%; }"
			+ "</style>";
	}
}
