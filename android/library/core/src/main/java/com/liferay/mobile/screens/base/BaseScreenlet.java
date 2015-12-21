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

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.liferay.mobile.screens.base.interactor.CustomInteractorListener;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Silvio Santos
 */
public abstract class BaseScreenlet<V extends BaseViewModel, I extends Interactor>
	extends FrameLayout {

	public BaseScreenlet(Context context) {
		super(context);

		init(context, null);
	}

	public BaseScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);

		init(context, attributes);
	}

	public BaseScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);

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
			onUserAction(null, interactor);
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

	public void setCustomInteractorListener(CustomInteractorListener customInteractorListener) {
		_customInteractorListener = customInteractorListener;
	}

	protected I prepareInteractor(String actionName) {

		I result = _customInteractorListener == null ?
			createInteractor(actionName) :
			(I) _customInteractorListener.createInteractor(actionName);

		if (result != null) {
			result.onScreenletAttached(this);
			_interactors.put(actionName, result);
		}
		return result;
	}

	protected void init(Context context, AttributeSet attributes) {
		LiferayScreensContext.init(context);

		_screenletView = createScreenletView(context, attributes);

		getViewModel().setScreenlet(this);

		addView(_screenletView);
	}

	protected V getViewModel() {
		return (V) _screenletView;
	}

	protected int getDefaultLayoutId() {
		try {
			Context ctx = getContext().getApplicationContext();
			String packageName = ctx.getPackageName();

			// first, get the identifier of the string key
			String layoutNameKeyName = getClass().getSimpleName() + "_" + getLayoutTheme();
			int layoutNameKeyId = ctx.getResources().getIdentifier(
				layoutNameKeyName, "string", packageName);

			// second, get the identifier of the layout specified in key layoutNameKeyId

			String layoutName = ctx.getString(layoutNameKeyId);
			return ctx.getResources().getIdentifier(layoutName, "layout", packageName);
		}
		catch (Exception e) {
			//We don't want to crash if the user creates a custom screenlet without adding a
			// default layout to it
			return 0;
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!isInEditMode()) {
			for (I interactor : _interactors.values()) {
				interactor.onScreenletAttached(this);
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
		state.putStringArray(
			_STATE_INTERACTORS, _interactors.keySet().toArray(new String[_interactors.size()]));

		return state;
	}

	protected void onScreenletAttached() {
	}

	protected void onScreenletDetached() {
	}

	protected abstract View createScreenletView(Context context, AttributeSet attributes);

	protected abstract I createInteractor(String actionName);

	protected abstract void onUserAction(String userActionName, I interactor, Object... args);

	protected long castToLong(String value) {
		return castToLongOrUseDefault(value, 0);
	}

	protected long castToLongOrUseDefault(String value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Long.valueOf(value);
		}
		catch (NumberFormatException e) {
			LiferayLogger.e("You have supplied a string and we expected a long number", e);
			throw e;
		}
	}

	@NonNull
	protected String getLayoutTheme() {
		String result = applyTheme(getActivityTheme());

		if (result == null) {
			result = applyTheme(getApplicationTheme());
		}
		return result == null ? "default" : result;
	}

	private String getActivityTheme() {
		try {
			ComponentName componentName = LiferayScreensContext.getActivityFromContext(getContext()).getComponentName();
			int activityThemeId = getContext().getPackageManager().getActivityInfo(componentName, 0).theme;
			return getResources().getResourceEntryName(activityThemeId);
		}
		catch (Exception e) {
			LiferayLogger.d("Screens theme not found");
		}
		return null;
	}

	private String getApplicationTheme() {
		try {
			Context ctx = getContext().getApplicationContext();
			String packageName = ctx.getPackageName();
			PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
			int applicationThemeId = packageInfo.applicationInfo.theme;
			return getResources().getResourceEntryName(applicationThemeId);
		}
		catch (Exception e) {
			LiferayLogger.d("Screens theme not found");
		}
		return null;
	}

	private String applyTheme(String themeName) {
		if (themeName != null && themeName.contains("_theme")) {
			return themeName.substring(0, themeName.indexOf("_theme"));
		}
		return null;
	}

	private static int _generateScreenletId() {

		// This implementation is copied from View.generateViewId() method We
		// cannot rely on that method because it's introduced in API Level 17

		while (true) {
			final int result = sNextId.get();
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) {
				newValue = 1;
			}
			if (sNextId.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	private static final String _STATE_SCREENLET_ID = "basescreenlet-screenletId";
	private static final String _STATE_SUPER = "basescreenlet-super";
	private static final String _STATE_INTERACTORS = "basescreenlet-interactors";
	private static final AtomicInteger sNextId = new AtomicInteger(1);

	private Map<String, I> _interactors = new HashMap<>();
	private int _screenletId;
	private View _screenletView;

	private CustomInteractorListener _customInteractorListener;

}