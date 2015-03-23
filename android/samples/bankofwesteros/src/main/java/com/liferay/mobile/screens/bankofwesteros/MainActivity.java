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
		_signInView.setOnTouchListener(new FlingTouchListener(this, createSignInListener()));

		_signUpView = (FrameLayout) findViewById(R.id.sign_up_view);
		_signUpView.setOnTouchListener(new FlingTouchListener(this, createSignUpListener()));

		_forgotPasswordSubView = findViewById(R.id.forgot_password_subview);
		_signInSubView = findViewById(R.id.sign_in_subview);

		_signUpSubView = findViewById(R.id.sign_up_subview);
		_termsSubView = findViewById(R.id.terms_subview);

		findViewById(R.id.background).setOnClickListener(this);

		final View mainView = findViewById(R.id.main_view);
		mainView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				_signInView.setY(_signInPosition);
				_signUpView.setY(_signUpPosition);
				_forgotPasswordSubView.setX(WIDTH);
				_termsSubView.setX(WIDTH);
				mainView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
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

	private void toSignInSubView() {
		_signInSubView.animate().x(0);
		_forgotPasswordSubView.animate().x(WIDTH);
	}

	private void toForgotPasswordSubView() {
		_signInSubView.animate().x(-WIDTH);
		_forgotPasswordSubView.animate().x(0);
	}

	private void toSignUpSubView() {
		_signUpSubView.animate().x(0);
		_termsSubView.animate().x(WIDTH);
	}

	private void toTermsSubView() {
		_signUpSubView.animate().x(-WIDTH);
		_termsSubView.animate().x(0);
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

	private FlingListener createSignInListener() {
		return new FlingListener() {
			@Override
			public void onFlingLeft() {
				toForgotPasswordSubView();
			}

			@Override
			public void onFlingRight() {
				toSignInSubView();
			}

			@Override
			public void onFlingUp() {
				toSignInView();
			}

			@Override
			public void onFlingDown() {
				toBackgroundView();
			}

			@Override
			public void onTouch() {
				toSignInView();
			}
		};
	}

	private FlingListener createSignUpListener() {
		return new FlingListener() {
			@Override
			public void onFlingLeft() {
				toTermsSubView();
			}

			@Override
			public void onFlingRight() {
				toSignUpSubView();
			}

			@Override
			public void onFlingUp() {
				toSignUpView();
			}

			@Override
			public void onFlingDown() {
				toSignInView();
			}

			@Override
			public void onTouch() {
				toSignUpView();
			}
		};
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
	private FrameLayout _signUpView;
	private View _forgotPasswordSubView;
	private View _signInSubView;
	private View _signUpSubView;
	private View _termsSubView;
}
