/**
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

package com.liferay.mobile.screens.viewsets.defaultviews.auth.signup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.signup.SignUpScreenlet;
import com.liferay.mobile.screens.auth.signup.view.SignUpViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.ModalProgressBar;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Silvio Santos
 */
public class SignUpView extends LinearLayout implements SignUpViewModel, View.OnClickListener {

    protected EditText emailAddress;
    protected EditText firstName;
    protected EditText lastName;
    protected EditText password;
    protected ModalProgressBar progressBar;
    private BaseScreenlet screenlet;

    public SignUpView(Context context) {
        super(context);
    }

    public SignUpView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public SignUpView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public String getEmailAddress() {
        return emailAddress.getText().toString();
    }

    @Override
    public String getFirstName() {
        return firstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return lastName.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public String getMiddleName() {
        return null;
    }

    @Override
    public String getScreenName() {
        return null;
    }

    @Override
    public String getJobTitle() {
        return null;
    }

    @Override
    public void showStartOperation(String actionName) {
        if (BaseScreenlet.DEFAULT_ACTION.equals(actionName)) {
            progressBar.startProgress();
        }
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new AssertionError("Use showFinishOperation(user) instead");
    }

    @Override
    public void showFinishOperation(User user) {
        progressBar.finishProgress();

        LiferayLogger.i("Sign-up successful: " + user.getId());
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        progressBar.finishProgress();

        LiferayLogger.e("Could not sign up", e);
    }

    @Override
    public BaseScreenlet getScreenlet() {
        return screenlet;
    }

    @Override
    public void setScreenlet(BaseScreenlet screenlet) {
        this.screenlet = screenlet;
    }

    @Override
    public void onClick(View view) {

        SignUpScreenlet signUpScreenlet = (SignUpScreenlet) getScreenlet();

        signUpScreenlet.performUserAction();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        firstName = findViewById(R.id.liferay_first_name);
        lastName = findViewById(R.id.liferay_last_name);
        emailAddress = findViewById(R.id.liferay_email_address);
        password = findViewById(R.id.liferay_password);
        progressBar = findViewById(R.id.liferay_progress);

        Button signUpButton = findViewById(R.id.liferay_sign_up_button);
        signUpButton.setOnClickListener(this);
    }
}