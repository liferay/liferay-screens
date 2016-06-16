/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.viewsets.westeros.auth.signup;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.viewsets.westeros.R;
import com.liferay.mobile.screens.viewsets.westeros.WesterosSnackbar;

/**
 * @author Silvio Santos
 */
public class SignUpView extends com.liferay.mobile.screens.viewsets.defaultviews.auth.signup.SignUpView {

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
	public void onClick(View view) {
		if (validFields()) {
			SignUpScreenlet signUpScreenlet = getSignUpScreenlet();
			signUpScreenlet.performUserAction();
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		initClickableTermsAndConditions();

		_firstNameValidation = (TextView) findViewById(R.id.first_name_validation);
		_lastNameValidation = (TextView) findViewById(R.id.last_name_validation);
		_lastNameValidation.setText(R.string.last_name_cant_be_empty);
		_emailAddressValidation = (TextView) findViewById(R.id.email_address_validation);
		_emailAddressValidation.setText(R.string.email_address_cant_be_empty);
		_passwordValidation = (TextView) findViewById(R.id.password_validation);
		_passwordValidation.setText(R.string.password_cant_be_empty);
	}

	private SignUpScreenlet getSignUpScreenlet() {
		return (SignUpScreenlet) getScreenlet();
	}

	private boolean validFields() {
		//TODO move to view model and interactor

		CheckBox acceptTerms = (CheckBox) findViewById(R.id.sign_up_checkbox);
		if (!acceptTerms.isChecked()) {
			WesterosSnackbar.showSnackbar(LiferayScreensContext.getActivityFromContext(getContext()),
				"You must accept the terms & conditions", R.color.colorAccent_westeros);
			return false;
		}

		if (!checkField(_firstName, _firstNameValidation)) {
			return false;
		}

		if (!checkField(_lastName, _lastNameValidation)) {
			return false;
		}

		if (!checkField(_emailAddress, _emailAddressValidation)) {
			return false;
		}

		return checkField(_password, _passwordValidation);

	}

	private boolean checkField(EditText field, View validationView) {
		boolean valid = !field.getText().toString().isEmpty();
		changeBackgroundAndIcon(field, valid);
		validationView.setVisibility(valid ? View.GONE : View.VISIBLE);
		return valid;
	}

	private void changeBackgroundAndIcon(EditText editText, boolean valid) {
		editText.setBackgroundResource(valid ? R.drawable.westeros_dark_edit_text_drawable : R.drawable.westeros_warning_edit_text_drawable);
		editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, valid ? 0 : R.drawable.westeros_icon_warning_white, 0);
	}

	private void initClickableTermsAndConditions() {
		TextView textView = (TextView) findViewById(R.id.terms);
		textView.setMovementMethod(LinkMovementMethod.getInstance());

		SpannableStringBuilder ssb = new SpannableStringBuilder("I accept the terms and conditions");

		ssb.setSpan(new ClickableSpan() {
			@Override
			public void onClick(View widget) {
				SignUpScreenlet signUpScreenlet = getSignUpScreenlet();
				signUpScreenlet.performUserAction(SignUpScreenlet.TERMS_AND_CONDITIONS);
			}
		}, 13, ssb.length(), 0);

		ssb.setSpan(new StyleSpan(Typeface.BOLD), 13, ssb.length(), 0);
		ssb.setSpan(new ForegroundColorSpan(
			ContextCompat.getColor(getContext(), android.R.color.white)), 13, ssb.length(), 0);

		textView.setText(ssb, TextView.BufferType.SPANNABLE);
	}

	private TextView _firstNameValidation;
	private TextView _lastNameValidation;
	private TextView _emailAddressValidation;
	private TextView _passwordValidation;
}