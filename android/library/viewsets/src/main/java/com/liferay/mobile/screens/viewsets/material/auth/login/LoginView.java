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

package com.liferay.mobile.screens.viewsets.material.auth.login;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;

/**
 * @author Silvio Santos
 */
public class LoginView
	extends com.liferay.mobile.screens.viewsets.defaultviews.auth.login.LoginView
	implements View.OnTouchListener {

	public LoginView(Context context) {
		super(context);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public LoginView(Context context, AttributeSet attributes) {
		super(context, attributes);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public LoginView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		ImageView primary = (v.getId() == R.id.liferay_login) ? _drawableLogin : _drawablePassword;
		ImageView secondary = (v.getId() == R.id.liferay_login) ? _drawablePassword : _drawableLogin;

		changeColorOfImageView(primary, secondary);

		return super.onTouchEvent(event);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		getLoginEditText().setOnTouchListener(this);
		getPasswordEditText().setOnTouchListener(this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_drawableLogin = (ImageView) findViewById(R.id.drawable_login);
		_drawablePassword = (ImageView) findViewById(R.id.drawable_password);

		changeColorOfImageView(_drawableLogin, _drawablePassword);
	}

	@Override
	protected void refreshLoginEditTextStyle() {
		getLoginEditText().setInputType(getAuthMethod().getInputType());
		_drawableLogin.setImageResource(getLoginEditTextDrawableId());
	}

	@Override
	protected int getLoginEditTextDrawableId() {
		if (AuthMethod.USER_ID.equals(getAuthMethod())) {
			return R.drawable.material_account_box;
		}
		else if (AuthMethod.EMAIL.equals(getAuthMethod())) {
			return R.drawable.material_email;
		}

		return R.drawable.material_account_box;
	}

	private void changeColorOfImageView(
		ImageView viewToPrimaryColor, ImageView viewToSecondaryText) {

		Resources res = getResources();

		viewToPrimaryColor.setColorFilter(res.getColor(R.color.material_primary));
		viewToSecondaryText.setColorFilter(res.getColor(R.color.material_secondary_text));
	}

	private ImageView _drawableLogin;
	private ImageView _drawablePassword;

}