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

package com.liferay.mobile.screens.base;

import android.content.Context;

import android.util.AttributeSet;

import android.view.View;

import android.widget.FrameLayout;

import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Silvio Santos
 */
public abstract class BaseScreenlet<V extends BaseViewModel, I extends Interactor>
	extends FrameLayout {

	public BaseScreenlet(Context context) {
		this(context, null);
	}

	public BaseScreenlet(Context context, AttributeSet attributes) {
		this(context, attributes, 0);
	}

	public BaseScreenlet(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);

		_screenletView = createScreenletView(context, attributes);

		addView(_screenletView);
	}

	public I getInteractor() {
		return _interactor;
	}

	public void setInteractor(I interactor) {
		_interactor = interactor;
	}

	protected abstract View createScreenletView(
		Context context, AttributeSet attributes);

	protected View getScreenletView() {
		return _screenletView;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		_interactor.onScreenletAttachted(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		_interactor.onScreenletDetached(this);
	}

	protected abstract void onUserAction(int id);

	private I _interactor;
	private View _screenletView;

}