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

package com.liferay.mobile.screens.themes.auth.signup;

import android.content.Context;

import android.util.AttributeSet;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.liferay.mobile.screens.auth.signup.SignUpScreenlet;
import com.liferay.mobile.screens.auth.signup.interactor.SignUpListener;
import com.liferay.mobile.screens.auth.signup.view.SignUpViewModel;
import com.liferay.mobile.screens.themes.R;

import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class SignUpScreenletView extends LinearLayout
	implements SignUpListener, SignUpViewModel, View.OnClickListener {

	public SignUpScreenletView(Context context) {
		this(context, null);
	}

	public SignUpScreenletView(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public SignUpScreenletView(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);
	}

	@Override
	public String getAnonymousApiPassword() {
		return "";
	}

	@Override
	public String getAnonymousApiUserName() {
		return "";
	}

	@Override
	public String getEmailAddress() {
		return _emailAddress.getText().toString();
	}

	@Override
	public String getFirstName() {
		return _firstName.getText().toString();
	}

	@Override
	public String getJobTitle() {
		return "";
	}

	@Override
	public String getLastName() {
		return _lastName.getText().toString();
	}

	@Override
	public String getMiddleName() {
		return "";
	}

	@Override
	public String getPassword() {
		return _password.getText().toString();
	}

	@Override
	public String getScreenName() {
		return "";
	}

	@Override
	public void onClick(View view) {
		SignUpScreenlet signUpScreenlet = (SignUpScreenlet)getParent();

		signUpScreenlet.onUserAction(SignUpScreenlet.SIGN_UP_ACTION);
	}

	@Override
	public void onSignUpFailure(Exception e) {
	}

	@Override
	public void onSignUpSuccess(JSONObject userAttributes) {
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		_firstName = (EditText)findViewById(R.id.first_name);
		_lastName = (EditText)findViewById(R.id.last_name);
		_emailAddress = (EditText)findViewById(R.id.email_address);
		_password = (EditText)findViewById(R.id.password);

		Button signUpButton = (Button)findViewById(R.id.sign_up);
		signUpButton.setOnClickListener(this);
	}

	private EditText _emailAddress;
	private EditText _firstName;
	private EditText _lastName;
	private EditText _password;

}