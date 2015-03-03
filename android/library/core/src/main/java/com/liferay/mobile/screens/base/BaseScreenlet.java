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
import com.liferay.mobile.screens.context.LiferayScreensContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Silvio Santos
 */
public abstract class BaseScreenlet<V extends BaseViewModel, I extends Interactor>
	extends FrameLayout {

	public BaseScreenlet(Context context) {
		super(context, null);

		init(context, null);
	}

	public BaseScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes, 0);

		init(context, attributes);
	}

	public BaseScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

		LiferayScreensContext.init(context);

		init(context, attributes);
	}

	public int getScreenletId() {
		if (_screenletId == 0) {
			_screenletId = _generateScreenletId();
		}

		return _screenletId;
	}

	public void performUserAction() {
		I interactor = getInteractor();

		if (interactor != null) {
			onUserAction(null, interactor, null);
		}
	}

	public void performUserAction(String userActionName, Object... args) {
		I interactor = getInteractor(userActionName);

		if (interactor != null) {
			onUserAction(userActionName, interactor, args);
		}
	}

	public I getInteractor() {
		return getInteractor("soleInteractor");
	}

	public I getInteractor(String actionName) {
		I result = _interactors.get(actionName);

		if (result == null) {
			result = prepareInteractor(actionName);
		}

		return result;
	}

	protected I prepareInteractor(String actionName) {
		I result = createInteractor(actionName);

		if (result != null) {
			result.onScreenletAttachted(this);
			_interactors.put(actionName, result);
		}

		return result;
	}

	protected void init(Context context, AttributeSet attributes) {
		_screenletView = createScreenletView(context, attributes);

		addView(_screenletView);
	}

	protected View getScreenletView() {
		return _screenletView;
	}

	protected V getViewModel() {
		return (V)_screenletView;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!isInEditMode()) {
			for (I interactor : _interactors.values()) {
				interactor.onScreenletAttachted(this);
			}
		}

		onScreenletAttached();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		onScreenletDetached();

		if (!isInEditMode()) {
			for (I interactor : _interactors.values()) {
				interactor.onScreenletDetached(this);
			}
		}
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle) inState;
		Parcelable superState = state.getParcelable(_STATE_SUPER);

		super.onRestoreInstanceState(superState);

		// The screenletId is restored only if it was not generated yet. If the
		// screenletId already exists at this point, it means that an interactor
		// is using it, so we cannot restore the previous value. As a side
		// effect, any previous executing task will not deliver the result to
		// the new interactor. To avoid this behavior, only call screenlet
		// methods after onStart() activity/fragment callback. This ensures that
		// onRestoreInstanceState was already called.
		// TODO: Create restore method?

		if (_screenletId == 0) {
			_screenletId = state.getInt(_STATE_SCREENLET_ID);
		}

		for (String actionName : state.getStringArray(_STATE_INTERACTORS)) {
			prepareInteractor(actionName);
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(_STATE_SUPER, superState);
		state.putInt(_STATE_SCREENLET_ID, _screenletId);
		state.putStringArray(_STATE_INTERACTORS, _interactors.keySet().toArray(new String[0]));

		return state;
	}

	protected void onScreenletAttached() {
	}

	protected void onScreenletDetached() {
	}

	protected abstract View createScreenletView(Context context, AttributeSet attributes);

	protected abstract I createInteractor(String actionName);

	protected abstract void onUserAction(String userActionName, I interactor, Object... args);

	private static final String _STATE_SCREENLET_ID = "basescreenlet-screenletId";
	private static final String _STATE_SUPER = "basescreenlet-super";
	private static final String _STATE_INTERACTORS = "basescreenlet-interactors";

	private static int _generateScreenletId() {

		// This implementation is copied from View.generateViewId() method We
		// cannot rely on that method because it's introduced in API Level 17

		while (true) {
			final int result = sNextId.get();
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) newValue = 1;
			if (sNextId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	private static final AtomicInteger sNextId = new AtomicInteger(1);

	private Map<String,I> _interactors = new HashMap<>();
	private int _screenletId;
	private View _screenletView;

}