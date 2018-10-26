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

package com.liferay.mobile.screens.westerosemployees_hybrid.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;
import com.liferay.mobile.screens.westerosemployees_hybrid.R;
import com.liferay.mobile.screens.westerosemployees_hybrid.utils.CardState;
import com.liferay.mobile.screens.westerosemployees_hybrid.views.Deck;

public class MainActivity extends WesterosActivity implements LoginListener, ForgotPasswordListener {

    private Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViews();

        //Load stored credentials
        SessionContext.loadStoredCredentials(CredentialsStorageBuilder.StorageType.AUTO);

        //Move to next activity if user is logged in
        if (SessionContext.isLoggedIn()) {
            Cache.resync();
            toNextActivity();
        }
    }

    private void findViews() {
        deck = findViewById(R.id.deck);

        LoginScreenlet loginScreenlet = findViewById(R.id.login_screenlet);
        loginScreenlet.setListener(this);

        ForgotPasswordScreenlet forgotPasswordScreenlet = findViewById(R.id.forgot_password_screenlet);
        forgotPasswordScreenlet.setListener(this);
    }

    @Override
    public void onLoginSuccess(User user) {
        toNextActivity();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void toNextActivity() {
        View viewById = findViewById(R.id.background);
        viewById.animate().alpha(0f).withEndAction(new Runnable() {
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

    @Override
    public void onAuthenticationBrowserShown() {

    }

    @Override
    public void onForgotPasswordRequestFailure(Exception e) {
        WesterosSnackbar.showSnackbar(this, "Request password failed", R.color.colorAccent_westeros);
    }

    @Override
    public void onForgotPasswordRequestSuccess(boolean passwordSent) {
        WesterosSnackbar.showSnackbar(this, "Request password succeed!", R.color.green_westeros);
    }
}
