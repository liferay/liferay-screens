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
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.viewsets.R;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultTheme;
import com.liferay.mobile.screens.viewsets.defaultviews.auth.login.LoginDefaultView;

/**
 * @author Silvio Santos
 */
public class LoginMaterialView extends LoginDefaultView implements View.OnTouchListener {

	public LoginMaterialView(Context context) {
		super(context);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public LoginMaterialView(Context context, AttributeSet attributes) {
		super(context, attributes);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	public LoginMaterialView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		DefaultTheme.initIfThemeNotPresent(context);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.login) {
			changeColorOfImageView(_drawableLogin, _drawablePassword);
		}
		else {
			changeColorOfImageView(_drawablePassword, _drawableLogin);
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onAttachedToWindow() {
		if (AuthMethod.USER_ID.equals(getAuthMethod())) {
			getLoginEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
			_drawableLogin.setImageResource(R.drawable.ic_account_box);
		}
		else if (AuthMethod.EMAIL.equals(getAuthMethod())) {
			getLoginEditText().setInputType(InputType.TYPE_CLASS_TEXT);
			_drawableLogin.setImageResource(R.drawable.ic_email);
		}
		else {
			getLoginEditText().setInputType(InputType.TYPE_CLASS_TEXT);
			_drawableLogin.setImageResource(R.drawable.ic_account_box);
		}
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

	private void changeColorOfImageView(ImageView viewToPrimaryColor, ImageView viewToSecondaryText) {
		viewToPrimaryColor.setColorFilter(getResources().getColor(R.color.material_primary));
		viewToSecondaryText.setColorFilter(getResources().getColor(R.color.material_secondary_text));
	}

	private ImageView _drawableLogin;
	private ImageView _drawablePassword;


}