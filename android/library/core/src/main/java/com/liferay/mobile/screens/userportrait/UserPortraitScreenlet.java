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

package com.liferay.mobile.screens.userportrait;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.userportrait.interactor.BaseUserPortraitInteractor;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadInteractor;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadInteractorImpl;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractor;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractorImpl;
import com.liferay.mobile.screens.userportrait.view.UserPortraitViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitScreenlet
	extends BaseScreenlet<UserPortraitViewModel, BaseUserPortraitInteractor>
	implements UserPortraitInteractorListener {

	public UserPortraitScreenlet(Context context) {
		super(context);
	}

	public UserPortraitScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public UserPortraitScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public static final String UPLOAD_PORTRAIT = "UPLOAD_PORTRAIT";
	public static final String LOAD_PORTRAIT = "LOAD_PORTRAIT";

	public void load() {
		performUserAction(LOAD_PORTRAIT);
	}

	public void updatePortraitImage() {

		//FIXME dialog?
		//FIXME progress?

		Intent galleryIntent = new Intent(Intent.ACTION_PICK,
			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		((Activity) getContext()).startActivityForResult(galleryIntent, 1);
	}

	public void upload(Intent data) {
		String picturePath = getPath(data);

		performUserAction(UPLOAD_PORTRAIT, picturePath);
	}

	private String getPath(Intent data) {
		Uri selectedImage = data.getData();

		String[] filePathColumn = {MediaStore.Images.Media.DATA,};

		Cursor cursor = getContext().getContentResolver().query(selectedImage,
			filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return picturePath;
	}

	@Override
	public void onStartUserPortraitLoadRequest() {
		getViewModel().showStartOperation(null);
	}

	@Override
	public Bitmap onEndUserPortraitLoadRequest(Bitmap bitmap) {
		Bitmap finalImage = bitmap;

		if (_listener != null) {
			finalImage = _listener.onUserPortraitLoadReceived(this, bitmap);

			if (finalImage == null) {
				finalImage = bitmap;
			}
		}

		getViewModel().showFinishOperation(finalImage);

		return finalImage;
	}

	@Override
	public void onUserPortraitLoadFailure(Exception e) {
		if (_listener != null) {
			_listener.onUserPortraitLoadFailure(this, e);
		}

		getViewModel().showFailedOperation(null, e);
	}

	@Override
	public void onUserPortraitUploaded(boolean male, long portraitId, String uuid) {
		if (_listener != null) {
			_listener.onUserPortraitUploaded(this);
		}
	}

	@Override
	public void onUserPortraitUploadFailure(Exception e) {
		if (_listener != null) {
			_listener.onUserPortraitLoadFailure(this, e);
		}
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
				LiferayLogger.e("Error loading portrait", e);
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
		_userId = typedArray.getInt(R.styleable.UserPortraitScreenlet_userId, (int) SessionContext.getLoggedUser().getId());

		int layoutId = typedArray.getResourceId(
			R.styleable.UserPortraitScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected BaseUserPortraitInteractor createInteractor(String actionName) {
		if (UPLOAD_PORTRAIT.equals(actionName)) {
			return new UserPortraitUploadInteractorImpl(getScreenletId());
		}
		else {
			return new UserPortraitLoadInteractorImpl(getScreenletId());
		}
	}

	@Override
	protected void onUserAction(
		String userActionName, BaseUserPortraitInteractor interactor, Object... args) {

		try {

			if (UPLOAD_PORTRAIT.equals(userActionName)) {
				UserPortraitUploadInteractor userPortraitInteractor = (UserPortraitUploadInteractor) getInteractor(userActionName);
				userPortraitInteractor.upload((String) args[0]);
			}
			else {
				UserPortraitLoadInteractor userPortraitLoadInteractor = (UserPortraitLoadInteractor) getInteractor(userActionName);
				if (_userId != 0) {
					userPortraitLoadInteractor.load(_userId);
				}
				else {
					userPortraitLoadInteractor.load(_male, _portraitId, _uuid);
				}
			}
		}
		catch (Exception e) {
			onUserPortraitLoadFailure(e);
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