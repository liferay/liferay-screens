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

package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 */
public class LoginActivity extends ThemeActivity implements LoginListener, View.OnClickListener {

    private LoginScreenlet loginScreenlet;
    private LoginScreenlet loginScreenletRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginScreenlet = findViewById(R.id.login_screenlet);
        loginScreenletRedirect = findViewById(R.id.login_screenlet_redirect);

        loginScreenlet.setListener(this);
        loginScreenletRedirect.setListener(this);

        setDefaultValues();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        loginScreenlet.resumeOAuth2RedirectFlow(data);
    }

    @Override
    public void onLoginSuccess(User user) {
        info(getString(R.string.login_success_info));
    }

    @Override
    public void onLoginFailure(Exception e) {
        error(getString(R.string.login_screenlet_error), e);
    }

    @Override
    public void onAuthenticationBrowserShown() {

    }

    @Override
    public void onClick(View v) {
        int tag = Integer.parseInt(v.getTag().toString());
        AuthenticationType type = AuthenticationType.values()[tag];

        configureLoginWithAuthType(type);
    }

    private void configureLoginWithAuthType(AuthenticationType type) {

        boolean isRedirect = AuthenticationType.OAUTH2REDIRECT.equals(type);

        loginScreenlet.setVisibility(isRedirect ? View.GONE : View.VISIBLE);
        loginScreenletRedirect.setVisibility(isRedirect ? View.VISIBLE : View.GONE);

        switch (type) {
            case COOKIE:
                loginScreenlet.setCookieExpirationTime(20);
                loginScreenlet.setShouldHandleCookieExpiration(true);
            case OAUTH2REDIRECT:
                loginScreenletRedirect.setOauth2ClientId(getString(R.string.client_id_redirect));
                loginScreenletRedirect.setOauth2RedirectUrl(getString(R.string.redirect_uri));
            case OAUTH2USERNAMEANDPASSWORD:
                loginScreenlet.setOauth2ClientId(getString(R.string.client_id));
                loginScreenlet.setOauth2ClientSecret(getString(R.string.client_secret));
        }

        loginScreenlet.setAuthenticationType(type);
    }

    private void setDefaultValues() {
        EditText login = loginScreenlet.findViewById(R.id.liferay_login);
        login.setText(getString(R.string.liferay_default_user_name));

        EditText password = loginScreenlet.findViewById(R.id.liferay_password);
        password.setText(getString(R.string.liferay_default_password));
    }
}
