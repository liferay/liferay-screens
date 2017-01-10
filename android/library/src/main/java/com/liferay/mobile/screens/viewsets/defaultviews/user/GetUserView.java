package com.liferay.mobile.screens.viewsets.defaultviews.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.user.GetUserScreenlet;
import com.liferay.mobile.screens.user.view.GetUserViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

public class GetUserView extends LinearLayout implements GetUserViewModel, View.OnClickListener {

	private EditText textValue;
	private BaseScreenlet screenlet;

	public GetUserView(Context context) {
		super(context);
	}

	public GetUserView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GetUserView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void showStartOperation(String actionName) {
	}

	@Override
	public void showFinishOperation(String actionName) {
		LiferayLogger.i("Get user successful");
	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {
		LiferayLogger.e("Could not get user", e);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();

		textValue = (EditText) findViewById(R.id.textValue);

		Button getUserButton = (Button) findViewById(R.id.getUserButton);
		getUserButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		GetUserScreenlet screenlet = (GetUserScreenlet) getParent();

		screenlet.performUserAction();
	}

	@Override
	public String getTextValue() {
		return textValue.getText().toString();
	}

	@Override
	public void setTextValue(String textValue) {
		this.textValue.setText(textValue);
	}

	@Override
	public BaseScreenlet getScreenlet() {
		return screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		this.screenlet = screenlet;
	}
}
