package com.liferay.mobile.screens.testapp.webviewstructured;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.liferay.mobile.screens.testapp.R;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Javier Gamarra
 */
public class WebContentDisplayView extends com.liferay.mobile.screens.viewsets.defaultviews.webcontent.display.WebContentDisplayView {

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
	public void showFinishOperation(WebContent webContent) {
		super.showFinishOperation(webContent);

		TextView contentField = (TextView) findViewById(R.id.web_content_field);
		contentField.setText(webContent.getDDMStructure().getField(0).getCurrentValue().toString());
	}
}
