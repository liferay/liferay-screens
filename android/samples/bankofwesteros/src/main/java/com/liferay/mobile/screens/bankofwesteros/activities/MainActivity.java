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

package com.liferay.mobile.screens.bankofwesteros.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.ImageView;

import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.bankofwesteros.utils.Card;
import com.liferay.mobile.screens.bankofwesteros.utils.EndAnimationListener;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.bankofwesteros.views.SignUpListener;
import com.liferay.mobile.screens.bankofwesteros.views.SignUpScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

import de.keyboardsurfer.android.widget.crouton.Configuration;


public class MainActivity extends CardActivity implements View.OnClickListener, LoginListener, ForgotPasswordListener, SignUpListener {

	public static final int CARD1_REST_POSITION = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		_background = (ImageView) findViewById(R.id.background);
		_background.setOnClickListener(this);

		//TODO move to the screenlet?
		_forgotPasswordText = findViewById(R.id.forgot_password_text);
		_forgotPasswordText.setOnClickListener(this);
		_forgotPasswordField = (EditText) findViewById(R.id.forgot_password_email);

		_loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		_loginScreenlet.setListener(this);

		_forgotPasswordScreenlet = (ForgotPasswordScreenlet) findViewById(R.id.forgot_password_screenlet);
		_forgotPasswordScreenlet.setListener(this);

		_signUpScreenlet = (SignUpScreenlet) findViewById(R.id.signup_screenlet);
		_signUpScreenlet.setListener(this);

		new LiferayCrouton.Builder()
			.withInfoColor(R.color.westeros_green)
			.withAlertColor(R.color.westeros_yellow)
			.locatedIn(R.id.crouton_view_anchor)
			.withHeight(250)
			.withConfiguration(new Configuration.Builder().setInAnimation(R.anim.slide_up).setOutAnimation(R.anim.slide_down).build())
			.build();
	}

	@Override
	public void onClick(final View view) {
		if (view.getId() == R.id.forgot_password_text) {
			goRightCard1();
		}
		else {
			super.onClick(view);
		}
	}

	@Override
	public void onLoginSuccess(User user) {
		toIssues();
	}

	@Override
	public void onLoginFailure(Exception e) {
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {
		_forgotPasswordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_email, 0);
	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {
	}

	@Override
	public void onSignUpSuccess(User user) {
		toIssues();
	}

	@Override
	public void onSignUpFailure(Exception e) {
		EditText viewById = (EditText) _signUpScreenlet.findViewById(R.id.first_name);
		viewById.setBackground(getDrawable(R.drawable.westeros_warning_edit_text_drawable));
		viewById.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_warning_white, 0);
		_signUpScreenlet.findViewById(R.id.first_name_validation).setVisibility(View.VISIBLE);
	}

	@Override
	public void onClickOnTermsAndConditions() {
		goRightCard2();
	}

	@Override
	protected void animateScreenAfterLoad() {
		_cardHistory.offer(Card.BACKGROUND);

		_card1.setY(_card1FoldedPosition);
		_card2.setY(_card2FoldedPosition);
		_card1RestPosition = convertDpToPx(CARD1_REST_POSITION);

		_background.animate().alpha(1);

		toBackground();
	}

	@Override
	protected void goRightCard1() {
		_forgotPasswordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
		super.goRightCard1();
	}

	private void toIssues() {
		_background.animate().alpha(0);

		int maxHeightInDp = convertDpToPx(_maxHeight);
		_card2.animate().y(maxHeightInDp);

		final ViewPropertyAnimator animate = _card1.animate();
		animate.y(maxHeightInDp)
			.setListener(new EndAnimationListener() {
				@Override
				public void onAnimationEnd(Animator animator) {
					animate.setListener(null);
					Intent intent = new Intent(MainActivity.this, IssuesActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(intent);
				}
			});
	}


	private ImageView _background;
	private View _forgotPasswordText;
	private EditText _forgotPasswordField;

	private LoginScreenlet _loginScreenlet;
	private ForgotPasswordScreenlet _forgotPasswordScreenlet;
	private SignUpScreenlet _signUpScreenlet;

}