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

package com.liferay.mobile.screens.westerosemployees.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import com.liferay.mobile.screens.westerosemployees.R;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;
import com.liferay.mobile.screens.viewsets.westeros.auth.signup.SignUpListener;
import com.liferay.mobile.screens.viewsets.westeros.auth.signup.SignUpScreenlet;
import com.liferay.mobile.screens.westerosemployees.Views.Card;
import com.liferay.mobile.screens.westerosemployees.utils.CardState;

public class MainActivity extends DeckActivity implements LoginListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setTransparentMenuBar();

		findViews();
	}

	private void findViews() {
		loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(this);
	}

	private void setTransparentMenuBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			setStatusBar();
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void setStatusBar() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		getWindow().setStatusBarColor(getResources().getColor(R.color.background_gray_westeros));
	}

	@Override
	public void onLoginSuccess(User user) {
		toNextActivity();
	}

	private void toNextActivity() {
		findViewById(R.id.background).animate().alpha(0f).withEndAction(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(MainActivity.this, UserActivity.class));
			}
		});
		for(Card card: cards) {
			card.setState(CardState.HIDDEN);
		}
	}

	@Override
	public void onLoginFailure(Exception e) {
		WesterosSnackbar.showSnackbar(this, "Login failed!", R.color.colorAccent_westeros);
	}

	private LoginScreenlet loginScreenlet;
}