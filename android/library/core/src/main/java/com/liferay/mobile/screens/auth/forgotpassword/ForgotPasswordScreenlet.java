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

package com.liferay.mobile.screens.auth.forgotpassword;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.AuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.interactor.ForgotPasswordInteractor;
import com.liferay.mobile.screens.auth.forgotpassword.interactor.ForgotPasswordInteractorImpl;
import com.liferay.mobile.screens.auth.forgotpassword.view.ForgotPasswordViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.LiferayServerContext;

/**
 * @author Silvio Santos
 */
public class ForgotPasswordScreenlet
	extends BaseScreenlet<ForgotPasswordViewModel, ForgotPasswordInteractor>
	implements ForgotPasswordListener {

	public static final String REQUEST_ACTION = "forgotPassword";

	public ForgotPasswordScreenlet(Context context) {
		this(context, null);
	}

	public ForgotPasswordScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public ForgotPasswordScreenlet(
			Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);

		setInteractor(new ForgotPasswordInteractorImpl(getScreenletId()));
	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {
		ForgotPasswordListener listenerView =
			(ForgotPasswordListener)getScreenletView();

		listenerView.onForgotPasswordRequestFailure(e);

		if (_listener != null) {
			_listener.onForgotPasswordRequestFailure(e);
		}
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {
		ForgotPasswordListener listenerView =
			(ForgotPasswordListener)getScreenletView();

		listenerView.onForgotPasswordRequestSuccess(passwordSent);

		if (_listener != null) {
			_listener.onForgotPasswordRequestSuccess(passwordSent);
		}
	}

	@Override
	public void onUserAction(String userActionName) {
		ForgotPasswordViewModel viewModel =
			(ForgotPasswordViewModel)getScreenletView();

		String login = viewModel.getLogin();
		AuthMethod method = viewModel.getAuthMethod();

		try {
			getInteractor().requestPassword(
				_companyId, login, method, _anonymousApiUserName,
				_anonymousApiPassword);
		}
		catch (Exception e) {
			onForgotPasswordRequestFailure(e);
		}
	}

	public void setListener(ForgotPasswordListener listener) {
		_listener = listener;
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.ForgotPasswordScreenlet, 0, 0);

		_companyId = typedArray.getInt(
			R.styleable.ForgotPasswordScreenlet_companyId,
			(int) LiferayServerContext.getCompanyId());

		_anonymousApiUserName = typedArray.getString(
			R.styleable.ForgotPasswordScreenlet_anonymousApiUserName);

		_anonymousApiPassword = typedArray.getString(
			R.styleable.ForgotPasswordScreenlet_anonymousApiPassword);

		int layoutId = typedArray.getResourceId(
			R.styleable.ForgotPasswordScreenlet_layoutId, 0);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		int authMethod = typedArray.getInt(
			R.styleable.ForgotPasswordScreenlet_authMethod, 0);

		ForgotPasswordViewModel viewModel = (ForgotPasswordViewModel)view;
		viewModel.setAuthMethod(AuthMethod.getValue(authMethod));

		typedArray.recycle();

		return view;
	}

	private String _anonymousApiUserName;
	private String _anonymousApiPassword;
	private long _companyId;
	private ForgotPasswordListener _listener;

}