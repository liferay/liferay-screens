/*
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
package com.liferay.mobile.screens.user.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.user.GetUserScreenlet;
import com.liferay.mobile.screens.user.R;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Mounir Hallab
 */
public class GetUserView extends LinearLayout implements GetUserViewModel, View.OnClickListener {

	private EditText textValue;
	private BaseScreenlet screenlet;

	public GetUserView(Context context) {
		super(context);
	}

	public GetUserView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public GetUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public GetUserView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
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

		textValue = findViewById(R.id.textValue);

		View getUserButton = findViewById(R.id.getUserButton);
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
