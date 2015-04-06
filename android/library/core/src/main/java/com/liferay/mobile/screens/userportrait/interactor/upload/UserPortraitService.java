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

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.user.UserService;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.LiferayLogger;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Javier Gamarra
 */
public class UserPortraitService extends IntentService {

	public UserPortraitService() {
		super(UserPortraitService.class.getCanonicalName());
	}

	@Override
	public void onHandleIntent(Intent intent) {
		uploadFromIntent(intent);
	}

	private void uploadFromIntent(Intent intent) {

		int targetScreenletId = intent.getIntExtra("screenletId", 0);
		String picturePath = intent.getStringExtra("picturePath");

		try {
			Session sessionFromCurrentSession = SessionContext.createSessionFromCurrentSession();
			UserService userService = new UserService(sessionFromCurrentSession);
			JSONObject jsonObject = userService.updatePortrait(SessionContext.getLoggedUser().getId(), readContentIntoByteArray(new File(picturePath)));

			EventBusUtil.post(new UserPortraitUploadEvent(targetScreenletId, jsonObject));
		}
		catch (Exception e) {
			EventBusUtil.post(new UserPortraitUploadEvent(targetScreenletId, e));
		}
	}


	private static byte[] readContentIntoByteArray(File file) {
		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) file.length()];
		try {
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		}
		catch (Exception e) {
			LiferayLogger.e("Error reading image bytes", e);
		}
		finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			}
			catch (IOException e) {
				LiferayLogger.e("Error closing stream", e);
			}
		}
		return bFile;
	}


}