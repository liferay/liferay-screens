/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.base.interactor.CustomInteractorListener;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.base.interactor.listener.CacheListener;
import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.LiferayLocale;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.util.ThemeUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Silvio Santos
 */
public abstract class BaseScreenlet<V extends BaseViewModel, I extends Interactor> extends FrameLayout
	implements CacheListener {

	public static final String DEFAULT_ACTION = "default_action";
	public static final String STATE_CACHE_POLICY = "STATE_CACHE_POLICY";
	public static final String STATE_GROUP_ID = "STATE_GROUP_ID";
	public static final String STATE_USER_ID = "STATE_USER_ID";
	public static final String STATE_LOCALE = "STATE_LOCALE";
	protected static final String STATE_SUPER = "basescreenlet-super";
	private static final String STATE_SCREENLET_ID = "basescreenlet-screenletId";
	private static final String STATE_INTERACTORS = "basescreenlet-interactors";
	private static final AtomicInteger NEXT_ID = new AtomicInteger(1);
	private final Map<String, I> interactors = new HashMap<>();
	protected CachePolicy cachePolicy;
	protected long groupId;
	protected long userId;
	protected Locale locale;
	protected CacheListener cacheListener;
	private int screenletId;
	private View screenletView;
	private CustomInteractorListener customInteractorListener;

	public BaseScreenlet(Context context) {
		super(context);

		//init(context, null);
	}

	public BaseScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context, attrs);
	}

	public BaseScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init(context, attrs);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public BaseScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

		init(context, attrs);
	}

	private static int generateScreenletId() {

		// This implementation is copied from View.generateViewId() method We
		// cannot rely on that method because it's introduced in API Level 17

		while (true) {
			final int result = NEXT_ID.get();
			int newValue = result + 1;
			if (newValue > 0x00FFFFFF) {
				newValue = 1;
			}
			if (NEXT_ID.compareAndSet(result, newValue)) {
				return result;
			}
		}
	}

	/**
	 * If the screenlet doesn't have {@link #screenletId}, this method generates and returns it.
	 *
	 * @return {@link #screenletId}
	 */
	public int getScreenletId() {
		if (screenletId == 0) {
			screenletId = generateScreenletId();
		}

		return screenletId;
	}

	/**
	 * Starts the default user action.
	 */
	public void performUserAction() {
		I interactor = getInteractor();

		if (interactor != null) {
			getViewModel().showStartOperation(DEFAULT_ACTION);
			onUserAction(DEFAULT_ACTION, interactor);
		}
	}

	/**
	 * Starts the user action depending on the `userActionName` parameter.
	 *
	 * @param args several arguments to use in the action.
	 */
	public void performUserAction(String userActionName, Object... args) {
		I interactor = getInteractor(userActionName);

		if (interactor != null) {
			getViewModel().showStartOperation(userActionName);
			onUserAction(userActionName, interactor, args);
		}
	}

	/**
	 * Returns the interactor based on the default action.
	 */
	public I getInteractor() {
		return getInteractor(DEFAULT_ACTION);
	}

	/**
	 * Returns the interactor based on the `actionName` parameter.
	 *
	 * @return interactor.
	 */
	public I getInteractor(String actionName) {
		I result = interactors.get(actionName);

		if (result == null) {
			result = prepareInteractor(actionName);
		}

		return result;
	}

	/**
	 * This method creates and decorate the interactor depending on the `actionName`.
	 *
	 * @return result interactor.
	 */
	protected I prepareInteractor(String actionName) {

		I result = customInteractorListener == null ? createInteractor(actionName)
			: (I) customInteractorListener.createInteractor(actionName);

		if (result != null) {
			decorateInteractor(actionName, result);
			result.onScreenletAttached(this);
			interactors.put(actionName, result);
		}
		return result;
	}

	/**
	 * Decorates the interactor if it's instance of {@link BaseInteractor}.
	 * Also, if the previous condition is true, prepares the interactor if it's
	 * instance of {@link BaseCacheReadInteractor}.
	 *
	 * @param result interactor.
	 */
	protected void decorateInteractor(String actionName, Interactor result) {
		if (result instanceof BaseInteractor) {
			BaseInteractor baseInteractor = (BaseInteractor) result;
			baseInteractor.setTargetScreenletId(getScreenletId());
			baseInteractor.setActionName(actionName);

			if (baseInteractor instanceof BaseCacheReadInteractor) {
				BaseCacheReadInteractor baseCacheReadInteractor = (BaseCacheReadInteractor) baseInteractor;
				baseCacheReadInteractor.setCachePolicy(getCachePolicy());
				baseCacheReadInteractor.setGroupId(getGroupId());
				baseCacheReadInteractor.setUserId(getUserId());
				baseCacheReadInteractor.setLocale(getLocale());
			}
		}
	}

	/**
	 * Initializes the screenlet with the given attributes or the default ones.
	 */
	protected void init(Context context, AttributeSet attributes) {
		LiferayScreensContext.init(context);

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.CacheScreenlet, 0, 0);

		groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.CacheScreenlet_groupId),
			LiferayServerContext.getGroupId());

		Long userAttribute = castToLong(typedArray.getString(R.styleable.CacheScreenlet_userId));
		Long userId = SessionContext.getUserId();
		this.userId = (userAttribute == 0 ? (userId == null ? 0 : userId) : userAttribute);

		String localeAttribute = typedArray.getString(R.styleable.CacheScreenlet_locale);
		locale = localeAttribute == null ? new Locale(LiferayLocale.getDefaultSupportedLocale())
			: new Locale(localeAttribute);

		Integer cachePolicyAttribute =
			typedArray.getInteger(R.styleable.CacheScreenlet_cachePolicy, CachePolicy.REMOTE_ONLY.ordinal());
		cachePolicy = CachePolicy.values()[cachePolicyAttribute];

		assignView(createScreenletView(context, attributes));
	}

	/**
	 * Inflates and assigns the view through the given `layoutId`. This method is used usually when
	 * we are creating screenlets dynamically and we have to associate a layout.
	 */
	public void render(int layoutId) {
		LiferayScreensContext.init(getContext());

		assignView(LayoutInflater.from(getContext()).inflate(layoutId, null));
	}

	/**
	 * Add the screenlet screenletView.
	 */
	public void assignView(View screenletView) {
		if (!isInEditMode()) {
			setViewModel(screenletView);
			getViewModel().setScreenlet(this);
			addView(screenletView);
		}
	}

	/**
	 * Returns the screenlet view.
	 */
	protected V getViewModel() {
		return (V) screenletView;
	}

	public void setViewModel(View viewModel) {
		this.screenletView = viewModel;
	}

	/**
	 * Searches the default layout id through screenlet name.
	 * If there is no layout, returns zero.
	 */
	protected int getDefaultLayoutId() {
		try {
			Context ctx = getContext().getApplicationContext();
			String packageName = ctx.getPackageName();

			// First, get the identifier of the string key
			String layoutNameKeyName = getClass().getSimpleName() + "_" + ThemeUtil.getLayoutTheme(ctx);
			int layoutNameKeyId = ctx.getResources().getIdentifier(layoutNameKeyName, "string", packageName);

			if (layoutNameKeyId == 0) {
				layoutNameKeyId =
					ctx.getResources().getIdentifier(getClass().getSimpleName() + "_default", "string", packageName);
			}

			// Second, get the identifier of the layout specified in key layoutNameKeyId

			String layoutName = ctx.getString(layoutNameKeyId);
			return ctx.getResources().getIdentifier(layoutName, "layout", packageName);
		} catch (Exception e) {
			// We don't want to crash if the user creates a custom screenlet without adding a
			// default layout to it
			return 0;
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		if (!isInEditMode()) {
			for (I interactor : interactors.values()) {
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
			for (I interactor : interactors.values()) {
				interactor.onScreenletDetached(this);
			}
		}
	}

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle state = (Bundle) inState;
		Parcelable superState = state.getParcelable(STATE_SUPER);

		super.onRestoreInstanceState(superState);

		cachePolicy = CachePolicy.values()[state.getInt(STATE_CACHE_POLICY)];
		groupId = state.getLong(STATE_GROUP_ID);
		userId = state.getLong(STATE_USER_ID);
		locale = (Locale) state.getSerializable(STATE_LOCALE);

		// The screenletId is restored only if it was not generated yet. If the
		// screenletId already exists at this point, it means that an interactor
		// is using it, so we cannot restore the previous value. As a side
		// effect, any previous executing task will not deliver the result to
		// the new interactor. To avoid this behavior, only call screenlet
		// methods after onStart() activity/fragment callback. This ensures that
		// onRestoreInstanceState was already called.
		// TODO: Create restore method?

		if (screenletId == 0) {
			screenletId = state.getInt(STATE_SCREENLET_ID);
		}

		String[] stateInteractors = state.getStringArray(STATE_INTERACTORS);
		if (stateInteractors != null) {
			for (String actionName : stateInteractors) {
				prepareInteractor(actionName);
			}
		}
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();

		Bundle state = new Bundle();
		state.putParcelable(STATE_SUPER, superState);
		state.putInt(STATE_SCREENLET_ID, screenletId);
		state.putStringArray(STATE_INTERACTORS, interactors.keySet().toArray(new String[interactors.size()]));
		state.putInt(STATE_CACHE_POLICY, cachePolicy.ordinal());
		state.putLong(STATE_GROUP_ID, groupId);
		state.putLong(STATE_USER_ID, userId);
		state.putSerializable(STATE_LOCALE, locale);

		return state;
	}

	/**
	 * Calls when the screenlet has been attached to the view.
	 */
	protected void onScreenletAttached() {
	}

	/**
	 * Calls when the screenlet has been detached to the view.
	 */
	protected void onScreenletDetached() {
	}

	/**
	 * Creates screenlet view based on its different attributes. If there is some non optional
	 * attribute that it doesn't have value, it takes the default one.
	 *
	 * @param attributes screenlet attributes.
	 * @return screenlet view.
	 */
	protected abstract View createScreenletView(Context context, AttributeSet attributes);

	/**
	 * Creates a new listener object depending on the `actionName` parameter.
	 *
	 * @return interactor.
	 */
	protected abstract I createInteractor(String actionName);

	/**
	 * Starts an interactor action depending on `userActionName` parameter.
	 * Also, you can pass several arguments to use in the different actions.
	 */
	protected abstract void onUserAction(String userActionName, I interactor, Object... args);

	/**
	 * Cast a value to long or use zero by default.
	 * This method is used in {@link #createScreenletView(Context, AttributeSet)}
	 * method for taking the screenlet attributes.
	 *
	 * @param value value to cast.
	 * @return value casted or zero.
	 */
	protected long castToLong(String value) {
		return castToLongOrUseDefault(value, 0);
	}

	/**
	 * Cast a value to long or use default.
	 * This method is used in {@link #createScreenletView(Context, AttributeSet)}
	 * method for taking the screenlet attributes.
	 *
	 * @param value value to cast.
	 * @return value casted or default one.
	 */
	protected long castToLongOrUseDefault(String value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			LiferayLogger.e("You have supplied a string and we expected a long number", e);
			throw e;
		}
	}

	private String applyTheme(String themeName) {
		if (themeName != null && themeName.contains("_theme")) {
			return themeName.substring(0, themeName.indexOf("_theme"));
		}
		return null;
	}

	@Override
	public void loadingFromCache(boolean success) {
		if (cacheListener != null) {
			cacheListener.loadingFromCache(success);
		}
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		if (cacheListener != null) {
			cacheListener.retrievingOnline(triedInCache, e);
		}
	}

	@Override
	public void storingToCache(Object object) {
		if (cacheListener != null) {
			cacheListener.storingToCache(object);
		}
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public CachePolicy getCachePolicy() {
		return cachePolicy;
	}

	public void setCachePolicy(CachePolicy cachePolicy) {
		this.cachePolicy = cachePolicy;
	}

	public CacheListener getCacheListener() {
		return cacheListener;
	}

	public void setCacheListener(CacheListener cacheListener) {
		this.cacheListener = cacheListener;
	}

	public void setCustomInteractorListener(CustomInteractorListener customInteractorListener) {
		this.customInteractorListener = customInteractorListener;
	}
}