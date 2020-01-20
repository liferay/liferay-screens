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

package com.liferay.mobile.screens.viewsets.material.auth.forgotpassword;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.viewsets.R;

/**
 * @author Silvio Santos
 */
public class ForgotPasswordView
    extends com.liferay.mobile.screens.viewsets.defaultviews.auth.forgotpassword.ForgotPasswordView {

    protected ImageView drawableLogin;

    public ForgotPasswordView(Context context) {
        super(context);
    }

    public ForgotPasswordView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public ForgotPasswordView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        drawableLogin = findViewById(R.id.drawable_login);
        drawableLogin.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary_material));
    }

    @Override
    protected void refreshLoginEditTextStyle() {
        getLoginEditText().setInputType(getBasicAuthMethod().getInputType());
        drawableLogin.setImageResource(getLoginEditTextDrawableId());
    }

    @Override
    protected int getLoginEditTextDrawableId() {
        if (BasicAuthMethod.USER_ID.equals(getBasicAuthMethod())) {
            return R.drawable.material_account_box;
        } else if (BasicAuthMethod.EMAIL.equals(getBasicAuthMethod())) {
            return R.drawable.material_email;
        }

        return R.drawable.material_account_box;
    }
}