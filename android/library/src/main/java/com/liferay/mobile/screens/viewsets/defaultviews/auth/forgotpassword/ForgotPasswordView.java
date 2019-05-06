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

package com.liferay.mobile.screens.viewsets.defaultviews.auth.forgotpassword;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.auth.forgotpassword.view.ForgotPasswordViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.ModalProgressBar;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Jose Manuel Navarro
 */
public class ForgotPasswordView extends LinearLayout implements ForgotPasswordViewModel, View.OnClickListener {

    protected EditText loginEditText;
    protected ModalProgressBar progressBar;
    private BasicAuthMethod basicAuthMethod;
    private BaseScreenlet screenlet;

    public ForgotPasswordView(Context context) {
        super(context);
    }

    public ForgotPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForgotPasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public BasicAuthMethod getBasicAuthMethod() {
        return basicAuthMethod;
    }

    public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
        this.basicAuthMethod = basicAuthMethod;

        refreshLoginEditTextStyle();
    }

    @Override
    public String getLogin() {
        return loginEditText.getText().toString();
    }

    @Override
    public void showStartOperation(String actionName) {
        progressBar.startProgress();
    }

    @Override
    public void showFinishOperation(String actionName) {
        throw new AssertionError("Use showFinishOperation(passwordSent) instead");
    }

    @Override
    public void showFinishOperation(boolean passwordSent) {
        progressBar.finishProgress();

        String msg = getResources().getString(R.string.password_sent) + " " + getResources().getString(
            R.string.check_your_inbox);

        LiferayLogger.i(msg);
    }

    @Override
    public void showFailedOperation(String actionName, Exception e) {
        progressBar.finishProgress();

        LiferayLogger.e("Could not send password", e);
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
        ForgotPasswordScreenlet screenlet = (ForgotPasswordScreenlet) getScreenlet();

        screenlet.performUserAction();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        loginEditText = findViewById(R.id.liferay_forgot_login);
        progressBar = findViewById(R.id.liferay_progress);

        Button requestButton = findViewById(R.id.liferay_forgot_button);
        requestButton.setOnClickListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        refreshLoginEditTextStyle();
    }

    protected void refreshLoginEditTextStyle() {
        if (basicAuthMethod != null) {
            loginEditText.setInputType(basicAuthMethod.getInputType());
        }
        loginEditText.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(getContext(), getLoginEditTextDrawableId()), null, null, null);
        loginEditText.setHint(getLoginEditTextLabel());
    }

    protected int getLoginEditTextLabel() {
        if (BasicAuthMethod.SCREEN_NAME.equals(basicAuthMethod)) {
            return R.string.screen_name;
        } else if (BasicAuthMethod.USER_ID.equals(basicAuthMethod)) {
            return R.string.user_id;
        }
        return R.string.email_address;
    }

    protected int getLoginEditTextDrawableId() {
        if (BasicAuthMethod.USER_ID.equals(basicAuthMethod)) {
            return R.drawable.default_user_icon;
        } else if (BasicAuthMethod.EMAIL.equals(basicAuthMethod)) {
            return R.drawable.default_mail_icon;
        }

        return R.drawable.default_user_icon;
    }

    protected EditText getLoginEditText() {
        return loginEditText;
    }
}