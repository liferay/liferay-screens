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

package com.liferay.mobile.screens.demoform.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.utils.CardState;
import com.liferay.mobile.screens.demoform.views.Deck;
import com.tbruyelle.rxpermissions.RxPermissions;

public class MainActivity extends WesterosActivity implements LoginListener, ForgotPasswordListener {

	private Deck deck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViews();

		//Load stored credentials
		//SessionContext.loadStoredCredentials(CredentialsStorageBuilder.StorageType.AUTO);

		//Move to next activity if user is logged in
		if (SessionContext.isLoggedIn()) {
			//Cache.resync();
			//toNextActivity();
		}

		new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(conceded -> {

		});
	}

	private void findViews() {
		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		loginScreenlet.setListener(this);

		ForgotPasswordScreenlet forgotPasswordScreenlet =
			(ForgotPasswordScreenlet) findViewById(R.id.forgot_password_screenlet);
		forgotPasswordScreenlet.setListener(this);

		deck = (Deck) findViewById(R.id.deck);
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

		deck.setCardsState(CardState.HIDDEN);
	}

	@Override
	public void onLoginFailure(Exception e) {
		showSnackBar("Login failed!");
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {
		showSnackBar("Email sent to change the password");
	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {
		showSnackBar("Error sending the password");
	}

	private void showSnackBar(String message) {
		Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
		snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
		snackbar.show();
	}
}