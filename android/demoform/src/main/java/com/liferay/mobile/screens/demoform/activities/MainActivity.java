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
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.demoform.R;
import com.liferay.mobile.screens.demoform.utils.CardState;
import com.liferay.mobile.screens.demoform.views.Deck;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;
import com.tbruyelle.rxpermissions.RxPermissions;
import rx.functions.Action1;

public class MainActivity extends WesterosActivity implements LoginListener {

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

		new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Action1<Boolean>() {
			@Override
			public void call(Boolean conceded) {

			}
		});
	}

	private void findViews() {
		LoginScreenlet loginScreenlet = (LoginScreenlet) findViewById(R.id.login_screenlet);
		deck = (Deck) findViewById(R.id.deck);

		loginScreenlet.setListener(this);
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
		WesterosSnackbar.showSnackbar(this, "Login failed!", R.color.colorAccent_westeros);
	}
}