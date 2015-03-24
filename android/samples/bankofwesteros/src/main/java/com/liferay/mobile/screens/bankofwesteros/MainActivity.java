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

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

public class MainActivity extends CardActivity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		_middlePosition = convertDpToPx(100);

		_signInView = (FrameLayout) findViewById(R.id.sign_in_view);
		_signInView.setOnTouchListener(new FlingTouchListener(this, createSignInListener()));

		_signUpView = (FrameLayout) findViewById(R.id.sign_up_view);
		_signUpView.setOnTouchListener(new FlingTouchListener(this, createSignUpListener()));

		_forgotPasswordSubView = findViewById(R.id.forgot_password_subview);
		_signInSubView = findViewById(R.id.sign_in_subview);

		_signUpSubView = findViewById(R.id.sign_up_subview);
		_termsSubView = findViewById(R.id.terms_subview);

		_background = (LinearLayout) findViewById(R.id.background);
		_background.setOnClickListener(this);

		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(new LoginListener() {
			@Override
			public void onLoginSuccess(User user) {
				_background.animate().alpha(0);
				int maxHeightInDp = convertDpToPx(_maxHeight);
				_signUpView.animate().y(maxHeightInDp);
				_signInView.animate().y(maxHeightInDp)
					.setListener(new Animator.AnimatorListener() {
						@Override
						public void onAnimationStart(Animator animation) {

						}

						@Override
						public void onAnimationEnd(Animator animation) {
							Intent intent = new Intent(MainActivity.this, IssuesActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(intent);
						}

						@Override
						public void onAnimationCancel(Animator animation) {

						}

						@Override
						public void onAnimationRepeat(Animator animation) {

						}
					});
			}

			@Override
			public void onLoginFailure(Exception e) {

			}
		});

	}

	@Override
	protected void heightAndWidthReady() {
		int iconHeightInDp = getResources().getDimensionPixelSize(R.dimen.icon_height);
		_signInPosition = _maxHeight - 2 * iconHeightInDp;
		_signUpPosition = _maxHeight - iconHeightInDp;
		_signInView.setY(_signInPosition);
		_signUpView.setY(_signUpPosition);
		_forgotPasswordSubView.setX(_maxWidth);
		_termsSubView.setX(_maxWidth);
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

	private void toSignUpSubView() {
		goLeft(_signUpSubView, _termsSubView);
	}

	private void toTermsSubView() {
		goRight(_signUpSubView, _termsSubView);
	}

	private void toBackgroundView() {
		_signInView.animate().y(_signInPosition);
	}

	private void toSignInView() {
		TransitionManager.beginDelayedTransition(_signInView);
		setFrameLayoutMargins(_signInView, 0, 0, 0, 0);
		_signInView.animate().y(_middlePosition);

		_signUpView.animate().y(_signUpPosition);
	}

	private void toSignUpView() {
		moveCardToTop(_signUpView, _signInView);
	}


	private FlingListener createSignInListener() {
		return new FlingListener() {
			@Override
			public void onFlingLeft() {
				goRight(_signInSubView, _forgotPasswordSubView);
			}

			@Override
			public void onFlingRight() {
				goLeft(_signInSubView, _forgotPasswordSubView);
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


	private int _signInPosition;
	private int _signUpPosition;
	private int _middlePosition;

	private LinearLayout _background;
	private FrameLayout _signInView;
	private FrameLayout _signUpView;
	private View _forgotPasswordSubView;
	private View _signInSubView;
	private View _signUpSubView;
	private View _termsSubView;
}
