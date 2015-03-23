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
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements View.OnClickListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		setContentView(R.layout.main);

		findViewById(R.id.sign_in_view).setOnClickListener(this);
		findViewById(R.id.sign_up_view).setOnClickListener(this);
		findViewById(R.id.background).setOnClickListener(this);

	}

	@Override
	public void onClick(final View view) {
		if (view.getId() == R.id.sign_in_view) {
			LinearLayout signInView = (LinearLayout) view;

			TransitionManager.beginDelayedTransition(signInView);
			changeMarginAndHeight(signInView, 0, -1150, 0, 0, 1050);

			LinearLayout signUpView = (LinearLayout) findViewById(R.id.sign_up_view);
			View signUpScreenlet = findViewById(R.id.signup_screenlet);
			signUpScreenlet.setVisibility(View.GONE);

			TransitionManager.beginDelayedTransition(signUpView);
		}
		else if (view.getId() == R.id.sign_up_view) {

			LinearLayout signUpView = (LinearLayout) view;
			View signUpScreenlet = findViewById(R.id.signup_screenlet);
			signUpScreenlet.setVisibility(View.VISIBLE);

			TransitionManager.beginDelayedTransition(signUpView);
			changeMargin(signUpView, 0, 0, 0, 0);

			LinearLayout signInView = (LinearLayout) findViewById(R.id.sign_in_view);
			changeMargin(signInView, 20, 20, 20, 0);

			TransitionManager.beginDelayedTransition(signInView);
		}
		else {
			LinearLayout signin = (LinearLayout) findViewById(R.id.sign_in_view);

			TransitionManager.beginDelayedTransition(signin);
			changeMarginAndHeight(signin, 0, 0, 0, 0, 200);
		}
	}

	private void changeMargin(View view, int marginLeft, int marginTop, int marginRight, int marginBottom) {
		changeMarginAndHeight(view, marginLeft, marginTop, marginRight, marginBottom, null);
	}

	private void changeMarginAndHeight(View view, int marginLeft, int marginTop, int marginRight, int marginBottom, Integer height) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		if (height != null) {
			layoutParams.height = height;
		}
		layoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		view.setLayoutParams(layoutParams);
	}

}
