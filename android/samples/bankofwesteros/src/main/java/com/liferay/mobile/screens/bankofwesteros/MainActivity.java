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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, View.OnTouchListener {


	private static final float SWIPE_VELOCITY_THRESHOLD = 1f;
	private GestureDetector mDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		setContentView(R.layout.main);

		findViewById(R.id.sign_in_button).setOnClickListener(this);
		findViewById(R.id.sign_up_button).setOnClickListener(this);
		findViewById(R.id.background).setOnClickListener(this);

		findViewById(R.id.sign_in_button).setOnTouchListener(this);
		findViewById(R.id.sign_up_button).setOnTouchListener(this);
		findViewById(R.id.background).setOnTouchListener(this);

		mDetector = new GestureDetector(this, new MyGestureListener());
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

//
//			LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) signUpScreenlet.getLayoutParams();
//			layoutParams2.height = 50;
//			signUpScreenlet.setLayoutParams(layoutParams2);

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
//			changeHeight(signInView, 100);
			TransitionManager.beginDelayedTransition((android.view.ViewGroup) signInView);


//			signInView.animate().y(0);

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

	private void changeHeight(View view, Integer height) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		layoutParams.height = height;
		view.setLayoutParams(layoutParams);
	}

	private void changeMargin(View view) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
		layoutParams.setMargins(0, 0, 0, 0);
		view.setLayoutParams(layoutParams);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mDetector.onTouchEvent(event);
		return true;
	}


	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

			//TODO movement
			if (e2.getY() - e1.getY() > 0 && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
				Toast.makeText(MainActivity.this, "SUb", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(MainActivity.this, "Baj", Toast.LENGTH_LONG).show();
			}
			return true;
		}
	}
}
