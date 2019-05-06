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

package com.liferay.mobile.screens.viewsets.westeros.auth.login;

import android.content.Context;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.liferay.mobile.screens.viewsets.westeros.R;

/**
 * @author Silvio Santos
 */
public class LoginView extends com.liferay.mobile.screens.viewsets.defaultviews.auth.login.LoginView
    implements View.OnTouchListener {

    public LoginView(Context context) {
        super(context);
    }

    public LoginView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public LoginView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            getPasswordEditText().setTransformationMethod(null);
            getPasswordEditText().setInputType(InputType.TYPE_CLASS_TEXT);

            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            getPasswordEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        }

        return false;
    }

    @Override
    protected void refreshLoginEditTextStyle() {
        if (getBasicAuthMethod() != null) {
            getLoginEditText().setInputType(getBasicAuthMethod().getInputType());
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        final View seePassword = findViewById(R.id.liferay_see_password);
        seePassword.setOnTouchListener(this);
    }
}