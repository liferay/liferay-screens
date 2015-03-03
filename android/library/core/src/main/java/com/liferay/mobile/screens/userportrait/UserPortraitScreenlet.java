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

package com.liferay.mobile.screens.userportrait;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractor;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorImpl;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;
import com.liferay.mobile.screens.userportrait.view.UserPortraitViewModel;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitScreenlet
	extends BaseScreenlet<UserPortraitViewModel, UserPortraitInteractor>
	implements UserPortraitInteractorListener {

	public UserPortraitScreenlet(Context context) {
		super(context, null);
	}

	public UserPortraitScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes, 0);
	}

	public UserPortraitScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public void load() {
		performUserAction();
	}

	@Override
	public void onStartUserPortraitRequest() {
		getViewModel().showStartOperation(null);
	}

	@Override
	public Bitmap onEndUserPortraitRequest(Bitmap bitmap) {
		Bitmap finalImage = bitmap;

		if (_listener != null) {
			finalImage = _listener.onUserPortraitReceived(this, bitmap);

			if (finalImage == null) {
				finalImage = bitmap;
			}
		}

		getViewModel().showFinishOperation(finalImage);

		return finalImage;
	}

	@Override
	public void onUserPortraitFailure(Exception e) {
		if (_listener != null) {
			_listener.onUserPortraitFailure(this, e);
		}

		getViewModel().showFailedOperation(null, e);
	}

	public void setListener(UserPortraitListener listener) {
		_listener = listener;
	}

	public boolean isMale() {
		return _male;
	}

	public void setMale(boolean male) {
		_male = male;
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	protected void autoLoad() {
		if (((_portraitId != 0) && (_uuid != null)) || (_userId != 0)) {
			try {
				load();
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.UserPortraitScreenlet, 0, 0);

		_autoLoad = typedArray.getBoolean(R.styleable.UserPortraitScreenlet_autoLoad, true);
		_male = typedArray.getBoolean(R.styleable.UserPortraitScreenlet_male, true);
		_portraitId = typedArray.getInt(R.styleable.UserPortraitScreenlet_portraitId, 0);
		_uuid = typedArray.getString(R.styleable.UserPortraitScreenlet_uuid);
		_userId = typedArray.getInt(R.styleable.UserPortraitScreenlet_userId, 0);

		int layoutId = typedArray.getResourceId(R.styleable.UserPortraitScreenlet_layoutId, 0);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	@Override
	protected UserPortraitInteractor createInteractor(String actionName) {
		return new UserPortraitInteractorImpl(getScreenletId());
	}

	@Override
	protected void onUserAction(
		String userActionName, UserPortraitInteractor interactor, Object... args) {

		try {
			if (_userId != 0) {
				getInteractor().load(_userId);
			}
			else {
				getInteractor().load(_male, _portraitId, _uuid);
			}
		}
		catch (Exception e) {
			onUserPortraitFailure(e);
		}
	}

	@Override
	protected void onScreenletAttached() {
		if (_autoLoad) {
			autoLoad();
		}
	}

	private boolean _autoLoad;
	private UserPortraitListener _listener;
	private boolean _male;
	private long _portraitId;
	private String _uuid;
	private long _userId;

}