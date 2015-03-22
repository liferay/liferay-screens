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

		findViewById(R.id.sign_in_button).setOnClickListener(this);
		findViewById(R.id.sign_up_button).setOnClickListener(this);
		findViewById(R.id.background).setOnClickListener(this);

	}

	@Override
	public void onClick(final View view) {
		if (view.getId() == R.id.sign_in_button) {
			TransitionManager.beginDelayedTransition((android.view.ViewGroup) view);
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			layoutParams.height = 1050;
			layoutParams.setMargins(0, -1150, 0, 0);
			view.setLayoutParams(layoutParams);


			LinearLayout signUpView = (LinearLayout) findViewById(R.id.sign_up_button);
			View signUpScreenlet = findViewById(R.id.signup_screenlet);
			signUpScreenlet.setVisibility(View.GONE);
			TransitionManager.beginDelayedTransition((android.view.ViewGroup) signUpView);

		}
		else if (view.getId() == R.id.sign_up_button) {


			LinearLayout signInView = (LinearLayout) findViewById(R.id.sign_in_button);


			LinearLayout signUpView = (LinearLayout) view;
			View signUpScreenlet = findViewById(R.id.signup_screenlet);
			signUpScreenlet.setVisibility(View.VISIBLE);
			TransitionManager.beginDelayedTransition((android.view.ViewGroup) signUpView);

			changeMargin(signUpView);

			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) signInView.getLayoutParams();
			layoutParams.setMargins(20, 20, 20, 0);
			signInView.setLayoutParams(layoutParams);
			TransitionManager.beginDelayedTransition((android.view.ViewGroup) signInView);

		}
		else {

			View signin = findViewById(R.id.sign_in_button);
			TransitionManager.beginDelayedTransition((android.view.ViewGroup) signin);
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) signin.getLayoutParams();
			layoutParams.height = 200;
			layoutParams.setMargins(0, 0, 0, 0);
			signin.setLayoutParams(layoutParams);
		}
	}

	private void changeMargin(View view) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		layoutParams.setMargins(0, 0, 0, 0);
		view.setLayoutParams(layoutParams);
	}

}
