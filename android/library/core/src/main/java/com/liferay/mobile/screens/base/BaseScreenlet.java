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

import android.os.Bundle;
import android.os.Parcelable;

import android.util.AttributeSet;

import android.view.View;

import android.widget.FrameLayout;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.view.BaseViewModel;

import java.util.concurrent.atomic.AtomicInteger;

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

	public int getScreenletId() {
		if (_screenletId == 0) {
			_screenletId = _generateScreenletId();
		}

		return _screenletId;
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

		onScreenletAttached();

		_interactor.onScreenletAttachted(this);
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		_interactor.onScreenletDetached(this);

		onScreenletDetached();
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = ((Bundle)inState);
		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		_screenletId = state.getInt(_STATE_SCREENLET_ID);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(_STATE_SUPER, superState);
		state.putInt(_STATE_SCREENLET_ID, _screenletId);

		return state;
	}

	protected void onScreenletAttached() {
	}

	protected void onScreenletDetached() {
	}

	protected abstract void onUserAction(String userActionName);

	private static int _generateScreenletId() {

		// This implementation is copied from View.generateViewId() method We
		// cannot rely on that method because it's introduced in API Level 17

		while (true) {
			final int result = sNextScreenletId.get();
			int newValue = result + 1;

			if (sNextScreenletId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	private static final String _STATE_SCREENLET_ID = "screenletId";

	private static final String _STATE_SUPER = "super";

	private static final AtomicInteger sNextScreenletId = new AtomicInteger(1);

	private I _interactor;
	private int _screenletId;
	private View _screenletView;

}