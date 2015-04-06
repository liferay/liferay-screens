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

package com.liferay.mobile.screens.bankofwesteros.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.liferay.mobile.screens.bankofwesteros.R;

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
	protected void onFinishInflate() {
		super.onFinishInflate();

		initClickableTermsAndConditions();
	}

	@Override
	public void onClick(View view) {
		CheckBox acceptTerms = (CheckBox) findViewById(R.id.sign_up_checkbox);
		if (acceptTerms.isChecked()) {
			SignUpScreenlet signUpScreenlet = (SignUpScreenlet) getParent();
			signUpScreenlet.performUserAction();
		}
		else {
			//TODO move to view model and interactor
			WesterosCrouton.error(getContext(), "You must accept the terms & conditions", null);
		}
	}

	private void initClickableTermsAndConditions() {
		TextView textView = (TextView) findViewById(R.id.terms);
		textView.setMovementMethod(LinkMovementMethod.getInstance());

		SpannableStringBuilder ssb = new SpannableStringBuilder("I accept the terms and conditions");

		ssb.setSpan(new ClickableSpan() {
			@Override
			public void onClick(View widget) {
				SignUpScreenlet signUpScreenlet = (SignUpScreenlet) getParent();
				signUpScreenlet.performUserAction(SignUpScreenlet.TERMS_AND_CONDITIONS);
			}
		}, 13, ssb.length(), 0);

		ssb.setSpan(new StyleSpan(Typeface.BOLD), 13, ssb.length(), 0);
		ssb.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.white)), 13, ssb.length(), 0);

		textView.setText(ssb, TextView.BufferType.SPANNABLE);
	}

}