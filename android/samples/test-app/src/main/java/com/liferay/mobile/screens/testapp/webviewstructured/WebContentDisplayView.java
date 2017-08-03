package com.liferay.mobile.screens.testapp.webviewstructured;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.liferay.mobile.screens.ddl.model.DDMStructure;
import com.liferay.mobile.screens.testapp.R;
import com.liferay.mobile.screens.viewsets.defaultviews.webcontent.display.WebContentStructuredDisplayView;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Javier Gamarra
 */
public class WebContentDisplayView extends WebContentStructuredDisplayView {

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
		super.showFinishOperation(webContent, customCss);

		DDMStructure ddmStructure = webContent.getDDMStructure();

		TextView firstField = (TextView) findViewById(R.id.web_content_first_field);
		firstField.setText(String.valueOf(ddmStructure.getField(0).getCurrentValue()));

		TextView secondField = (TextView) findViewById(R.id.web_content_second_field);
		secondField.setText(String.valueOf(ddmStructure.getField(1).getCurrentValue()));
	}
}
