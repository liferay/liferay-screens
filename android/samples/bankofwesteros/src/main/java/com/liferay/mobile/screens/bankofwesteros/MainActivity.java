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

package com.liferay.mobile.screens.bankofwesteros;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {

	//TODO change for device width
	public static final int WIDTH = 1200;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//TODO check in other devices, maybe dimen.xml with mdpi resource needed
		_signInPosition = convertPxToDp(370);
		_signUpPosition = convertPxToDp(440);
		_middlePosition = convertPxToDp(100);
		_topPosition = convertPxToDp(30);

		_signInView = (FrameLayout) findViewById(R.id.sign_in_view);
		_signInView.setOnClickListener(this);

		_signUpView = (LinearLayout) findViewById(R.id.sign_up_view);
		_signUpView.setOnClickListener(this);

		_forgotPasswordSubView = findViewById(R.id.forgot_password_subview);
		_loginSubView = findViewById(R.id.sign_in_subview);
		findViewById(R.id.background).setOnClickListener(this);

		final View mainView = findViewById(R.id.main_view);
		mainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				_signInView.setY(_signInPosition);
				_signUpView.setY(_signUpPosition);
				_forgotPasswordSubView.setX(WIDTH);
				mainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
			}
		});

		findViewById(R.id.forgot_change).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				_loginSubView.animate().x(-WIDTH);
				_forgotPasswordSubView.animate().x(0);
			}
		});

	}

	@Override
	public void onClick(final View view) {
		//TODO move to another class
		if (view.getId() == R.id.sign_in_view) {
			toSignInView();
		}
		else if (view.getId() == R.id.sign_up_view) {
			toSignUpView();
		}
		else {
			toBackgroundView();
		}
	}

	private void toBackgroundView() {
		_signInView.animate().y(_signInPosition);
	}


	private void toSignInView() {
		TransitionManager.beginDelayedTransition(_signInView);
		setMargins(_signInView, 0, 0, 0, 0);
		_signInView.animate().y(_middlePosition);

		_signUpView.animate().y(_signUpPosition);
	}

	private void toSignUpView() {
		TransitionManager.beginDelayedTransition(_signInView);

		_signUpView.animate().y(_topPosition);

		int margin = _topPosition / 2;
		setMargins(_signInView, margin, 0, margin, 0);
		_signInView.animate().y(margin);
	}

	private void setMargins(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
		FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
		layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		view.setLayoutParams(layoutParams);
	}

	private int convertPxToDp(int dp) {
		Resources resources = getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
	}

	private int _signInPosition;
	private int _signUpPosition;
	private int _middlePosition;
	private int _topPosition;

	private FrameLayout _signInView;
	private LinearLayout _signUpView;
	private View _forgotPasswordSubView;
	private View _loginSubView;
}
