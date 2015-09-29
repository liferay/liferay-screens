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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.userportrait.interactor.BaseUserPortraitInteractor;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadInteractor;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadInteractorImpl;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractor;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitUploadInteractorImpl;
import com.liferay.mobile.screens.userportrait.view.UserPortraitViewModel;
import com.liferay.mobile.screens.util.FileUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.io.File;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public class UserPortraitScreenlet
	extends BaseScreenlet<UserPortraitViewModel, BaseUserPortraitInteractor>
	implements UserPortraitInteractorListener {

	public static final String UPLOAD_PORTRAIT = "UPLOAD_PORTRAIT";
	public static final String LOAD_PORTRAIT = "LOAD_PORTRAIT";
	public static final int SELECT_IMAGE_FROM_GALLERY = 0;
	public static final int TAKE_PICTURE_WITH_CAMERA = 1;

	public UserPortraitScreenlet(Context context) {
		super(context);
	}

	public UserPortraitScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public UserPortraitScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	public void load() {
		performUserAction(LOAD_PORTRAIT);
	}

	public void upload(int requestCode, Intent onActivityResultData) {
		try {
			String picturePath = "";
			if (requestCode == SELECT_IMAGE_FROM_GALLERY) {
				picturePath = FileUtil.getPath(getContext(), onActivityResultData.getData());
			}
			else if (requestCode == TAKE_PICTURE_WITH_CAMERA) {
				picturePath = _filePath;
			}
			performUserAction(UPLOAD_PORTRAIT, picturePath);
		}
		catch (IllegalArgumentException e) {
			onUserPortraitUploadFailure(e);
		}
	}

	public void openCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imageFile = FileUtil.createImageFile();
		setFilePath(imageFile.getPath());
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		((Activity) getContext()).startActivityForResult(cameraIntent, UserPortraitScreenlet.TAKE_PICTURE_WITH_CAMERA);
	}

	public void openGallery() {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK,
			MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		((Activity) getContext()).startActivityForResult(
			galleryIntent, UserPortraitScreenlet.SELECT_IMAGE_FROM_GALLERY);
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

		getViewModel().showFailedOperation(LOAD_PORTRAIT, e);
	}

	@Override
	public void onUserPortraitUploaded(Long uuid) {
		if (_listener != null) {
			_listener.onUserPortraitUploaded(this);
		}

		getViewModel().showFinishOperation(UPLOAD_PORTRAIT);

		try {
			((UserPortraitLoadInteractor) getInteractor(LOAD_PORTRAIT)).load(uuid);
		}
		catch (Exception e) {
			LiferayLogger.e("Error reloading user portrait", e);
		}
	}

	@Override
	public void onUserPortraitUploadFailure(Exception e) {
		if (_listener != null) {
			_listener.onUserPortraitLoadFailure(this, e);
		}

		getViewModel().showFailedOperation(UPLOAD_PORTRAIT, e);
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

	public boolean getEditable() {
		return _editable;
	}

	public void setEditable(boolean editable) {
		_editable = editable;
	}

	public String getFilePath() {
		return _filePath;
	}

	public void setFilePath(String filePath) {
		_filePath = filePath;
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
		_editable = typedArray.getBoolean(R.styleable.UserPortraitScreenlet_editable, false);

		int cachePolicy = typedArray.getInt(R.styleable.UserPortraitScreenlet_cachePolicy,
			CachePolicy.ONLINE_ONLY.ordinal());
		_cachePolicy = CachePolicy.values()[cachePolicy];

		int offlinePolicy = typedArray.getInt(R.styleable.UserPortraitScreenlet_offlinePolicy,
			OfflinePolicy.NO_OFFLINE.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		_userId = castToLongOrUseDefault(typedArray.getString(R.styleable.UserPortraitScreenlet_userId), 0L);

		if (SessionContext.hasSession() && _portraitId == 0 && _uuid == null && _userId == 0) {
			_userId = SessionContext.getLoggedUser().getId();
		}

		int layoutId = typedArray.getResourceId(
			R.styleable.UserPortraitScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected BaseUserPortraitInteractor createInteractor(String actionName) {
		if (UPLOAD_PORTRAIT.equals(actionName)) {
			return new UserPortraitUploadInteractorImpl(getScreenletId(), _offlinePolicy);
		}
		else {
			return new UserPortraitLoadInteractorImpl(getScreenletId(), _cachePolicy);
		}
	}

	@Override
	protected void onUserAction(
		String userActionName, BaseUserPortraitInteractor interactor, Object... args) {

		try {
			if (UPLOAD_PORTRAIT.equals(userActionName)) {
				UserPortraitUploadInteractor userPortraitInteractor = (UserPortraitUploadInteractor) getInteractor(userActionName);
				String path = (String) args[0];
				if (_userId != 0) {
					userPortraitInteractor.upload(_userId, path);
				}
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

	@Override
	protected void onRestoreInstanceState(Parcelable inState) {
		Bundle bundle = (Bundle) inState;
		super.onRestoreInstanceState(bundle.getParcelable(_STATE_SUPER));
		_filePath = bundle.getString(_STATE_FILE_PATH);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(_STATE_SUPER, super.onSaveInstanceState());
		bundle.putString(_STATE_FILE_PATH, _filePath);
		return bundle;
	}

	private static final String _STATE_SUPER = "userportrait-super";
	private static final String _STATE_FILE_PATH = "userportrait-filePath";

	private String _filePath;
	private boolean _autoLoad;
	private UserPortraitListener _listener;
	private boolean _male;
	private long _portraitId;
	private String _uuid;
	private long _userId;
	private boolean _editable;
	private CachePolicy _cachePolicy;
	private OfflinePolicy _offlinePolicy;
}