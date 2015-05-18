package com.liferay.mobile.screens.viewsets.defaultviews.bookmark;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.bookmark.AddBookmarkScreenlet;
import com.liferay.mobile.screens.bookmark.view.AddBookmarkViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

/**
 * @author Javier Gamarra
 */
public class AddBookmarkView
	extends LinearLayout implements AddBookmarkViewModel, View.OnClickListener {

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
		LiferayCrouton.error(getContext(), "Could not add bookmark", e);
	}

	public void onClick(View v) {
		AddBookmarkScreenlet screenlet = (AddBookmarkScreenlet) getParent();

		screenlet.performUserAction();
	}

	public String getURL() {
		return _urlText.getText().toString();
	}

	public void setURL(String value) {
		_urlText.setText(value);
	}

	public String getTitle() {
		return _titleText.getText().toString();
	}

	public void setTitle(String value) {
		_titleText.setText(value);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();

		_urlText = (EditText) findViewById(R.id.url);
		_titleText = (EditText) findViewById(R.id.title);

		Button addButton = (Button) findViewById(R.id.add_button);
		addButton.setOnClickListener(this);
	}

	private EditText _urlText;
	private EditText _titleText;
}

