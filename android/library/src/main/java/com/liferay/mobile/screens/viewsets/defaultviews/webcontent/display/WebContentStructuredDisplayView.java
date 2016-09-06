package com.liferay.mobile.screens.viewsets.defaultviews.webcontent.display;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;
import com.liferay.mobile.screens.webcontent.display.view.WebContentDisplayViewModel;

/**
 * @author Javier Gamarra
 */
public class WebContentStructuredDisplayView extends LinearLayout implements WebContentDisplayViewModel {

	private BaseScreenlet _screenlet;
	private TextView _contentField;

	public WebContentStructuredDisplayView(Context context) {
		super(context);
	}

	public WebContentStructuredDisplayView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public WebContentStructuredDisplayView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void showFinishOperation(WebContent webContent) {
		String value = getValueFromLabelFields(webContent);

		if (_contentField != null) {
			_contentField.setText(value);
		}
	}

	@Override
	public void showStartOperation(String actionName) {

	}

	@Override
	public void showFinishOperation(String actionName) {

	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {

	}

	@Override
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	protected String getValueFromLabelFields(WebContent webContent) {
		WebContentDisplayScreenlet screenlet = (WebContentDisplayScreenlet) getScreenlet();
		String labelFields = screenlet.getLabelFields();

		if (labelFields.isEmpty()) {
			return (String) webContent.getDDMStructure().getField(0).getCurrentValue();
		}

		StringBuilder stringBuilder = new StringBuilder("");

		for (String label : labelFields.split(",")) {
			Field field = webContent.getDDMStructure().getFieldByName(label);
			if (field != null) {
				stringBuilder.append(field.getCurrentValue());
				stringBuilder.append("\r\n");
			}
		}

		return stringBuilder.toString();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setOrientation(LinearLayout.VERTICAL);

		_contentField = (TextView) findViewById(R.id.web_content_field);
	}
}
