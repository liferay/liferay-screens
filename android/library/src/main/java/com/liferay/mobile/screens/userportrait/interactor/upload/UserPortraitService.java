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

package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.base.interactor.event.CachedEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitService extends IntentService {

	public static final int PORTRAIT_SIZE = 200;
	public static final int CONNECTION_TIMEOUT = 120000;

	public UserPortraitService() {
		super(UserPortraitService.class.getCanonicalName());
	}

	private static Bitmap rotateImage(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}

	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	private static byte[] decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) throws IOException {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;

		Bitmap bitmap = checkOrientationAndRotate(path, BitmapFactory.decodeFile(path, options));

		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
		return byteArrayBitmapStream.toByteArray();
	}

	private static Bitmap checkOrientationAndRotate(String filePath, Bitmap bitmap) throws IOException {
		ExifInterface ei = new ExifInterface(filePath);
		int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

		switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				return rotateImage(bitmap, 90);
			case ExifInterface.ORIENTATION_ROTATE_180:
				return rotateImage(bitmap, 180);
			default:
				return bitmap;
		}
	}

	@Override
	public void onHandleIntent(Intent intent) {
		uploadFromIntent(intent);
	}

	public void uploadFromIntent(Intent intent) {
		String picturePath = intent.getStringExtra("picturePath");
		long groupId = intent.getLongExtra("groupId", 0L);
		long userId = intent.getLongExtra("userId", 0L);
		Locale locale = (Locale) intent.getSerializableExtra("locale");
		int targetScreenletId = intent.getIntExtra("targetScreenletId", 0);
		String actionName = intent.getStringExtra("actionName");

		try {
			JSONObject jsonObject = uploadUserPortrait(userId, picturePath);
			UserPortraitUploadEvent event = new UserPortraitUploadEvent(picturePath, jsonObject);
			event.setPicturePath(picturePath);
			decorateEvent(event, groupId, userId, locale, targetScreenletId, actionName);
			EventBusUtil.post(event);
		} catch (Exception e) {
			UserPortraitUploadEvent event = new UserPortraitUploadEvent(e);
			event.setPicturePath(picturePath);
			decorateEvent(event, groupId, userId, locale, targetScreenletId, actionName);
			EventBusUtil.post(event);
		}
	}

	public JSONObject uploadUserPortrait(long userId, String picturePath) throws Exception {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setConnectionTimeout(CONNECTION_TIMEOUT);
		UserConnector userService = ServiceProvider.getInstance().getUserConnector(session);
		byte[] decodeSampledBitmapFromResource =
			decodeSampledBitmapFromResource(picturePath, PORTRAIT_SIZE, PORTRAIT_SIZE);
		return userService.updatePortrait(userId, decodeSampledBitmapFromResource);
	}

	private void decorateEvent(CachedEvent event, long groupId, long userId, Locale locale, int targetScreenletId,
		String actionName) {
		event.setGroupId(groupId);
		event.setUserId(userId);
		event.setLocale(locale);
		event.setCachedRequest(false);
		event.setTargetScreenletId(targetScreenletId);
		event.setActionName(actionName);
	}
}