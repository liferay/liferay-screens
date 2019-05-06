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
import android.widget.TextView;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.bankofwesteros.R;
import com.liferay.mobile.screens.bankofwesteros.utils.EndAnimationListener;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;
import com.liferay.mobile.screens.viewsets.westeros.auth.signup.SignUpListener;
import com.liferay.mobile.screens.viewsets.westeros.auth.signup.SignUpScreenlet;

public class MainActivity extends CardActivity
    implements View.OnClickListener, LoginListener, ForgotPasswordListener, SignUpListener {

    public static final int CARD1_REST_POSITION = 100;
    private ImageView background;
    private EditText forgotPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        background = findViewById(R.id.background);
        background.setOnClickListener(this);

        //TODO move to the screenlet?
        View forgotPasswordText = findViewById(R.id.liferay_forgot_link);
        forgotPasswordText.setOnClickListener(this);
        forgotPasswordField = findViewById(R.id.liferay_forgot_login);

        LoginScreenlet loginScreenlet = findViewById(R.id.login_screenlet);
        loginScreenlet.setListener(this);

        ((TextView) findViewById(R.id.liferay_login)).setText(getResources().getString(R.string.liferay_login));
        ((TextView) findViewById(R.id.liferay_password)).setText(getResources().getString(R.string.liferay_password));

        ForgotPasswordScreenlet forgotPasswordScreenlet = findViewById(R.id.forgot_password_screenlet);
        forgotPasswordScreenlet.setListener(this);

        SignUpScreenlet signUpScreenlet = findViewById(R.id.signup_screenlet);
        signUpScreenlet.setListener(this);
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.liferay_forgot_link) {
            goRightCard1();
        } else {
            super.onClick(view);
        }
    }

    @Override
    public void onLoginSuccess(User user) {
        toIssues();
    }

    @Override
    public void onLoginFailure(Exception e) {
        WesterosSnackbar.showSnackbar(this, "Login failed!", R.color.colorAccent_westeros);
    }

    @Override
    public void onAuthenticationBrowserShown() {

    }

    @Override
    public void onForgotPasswordRequestSuccess(boolean passwordSent) {
        forgotPasswordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.circle_email, 0);
        WesterosSnackbar.showSnackbar(this, "Password requested!", R.color.green_westeros);
    }

    @Override
    public void onForgotPasswordRequestFailure(Exception e) {
        WesterosSnackbar.showSnackbar(this, "Failed to request password", R.color.colorAccent_westeros);
    }

    @Override
    public void onSignUpSuccess(User user) {
        toIssues();
    }

    @Override
    public void onSignUpFailure(Exception e) {
        WesterosSnackbar.showSnackbar(this, "Sign up failed!", R.color.colorAccent_westeros);
    }

    @Override
    public void onClickOnTermsAndConditions() {
        goRightCard2();
    }

    @Override
    protected void animateScreenAfterLoad() {
        card1.setY(card1FoldedPosition);
        card2.setY(card2FoldedPosition);
        card1RestPosition = convertDpToPx(CARD1_REST_POSITION);

        background.animate().alpha(1);

        toBackground();
    }

    @Override
    protected void goRightCard1() {
        forgotPasswordField.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        super.goRightCard1();
    }

    private void toIssues() {
        background.animate().alpha(0);

        int maxHeightInDp = convertDpToPx(maxHeight);
        card2.animate().y(maxHeightInDp);

        final ViewPropertyAnimator animate = card1.animate();
        animate.y(maxHeightInDp).setListener(new EndAnimationListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                animate.setListener(null);
                Intent intent = new Intent(MainActivity.this, IssuesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }
}