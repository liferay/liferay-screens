package com.liferay.mobile.screens.bookmark.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.bookmark.AddBookmarkScreenlet;
import com.liferay.mobile.screens.bookmark.R;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public class AddBookmarkView extends LinearLayout implements AddBookmarkViewModel, View.OnClickListener {

	private EditText urlText;
	private EditText titleText;
	private BaseScreenlet screenlet;

	public AddBookmarkView(Context context) {
		super(context);
	}

	public AddBookmarkView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AddBookmarkView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void showStartOperation(String actionName) {

	}

	@Override
	public void showFinishOperation(String actionName) {
		LiferayLogger.i("Add bookmark successful");
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		LiferayLogger.e("Could not add bookmark", e);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}

	public void onClick(View v) {
		AddBookmarkScreenlet screenlet = (AddBookmarkScreenlet) getScreenlet();

		screenlet.performUserAction();
	}

	public String getURL() {
		return urlText.getText().toString();
	}

	public void setURL(String value) {
		urlText.setText(value);
	}

	public String getTitle() {
		return titleText.getText().toString();
	}

	public void setTitle(String value) {
		titleText.setText(value);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();

		urlText = findViewById(R.id.url);
		titleText = findViewById(R.id.title_bookmark);

		Button addButton = findViewById(R.id.add_button);
		addButton.setOnClickListener(this);
	}
}
