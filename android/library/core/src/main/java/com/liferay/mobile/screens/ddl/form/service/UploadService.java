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

package com.liferay.mobile.screens.ddl.form.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.dlapp.DLAppService;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormFileEvent;
import com.liferay.mobile.screens.ddl.model.FileField;
import com.liferay.mobile.screens.util.EventBusUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Javier Gamarra
 */
public class UploadService extends IntentService {

	public UploadService() {
		super(UploadService.class.getCanonicalName());
	}

	@Override
	public void onHandleIntent(Intent intent) {
		uploadFromIntent(intent);
	}

	private void uploadFromIntent(Intent intent) {
		FileField file = intent.getParcelableExtra("file");
		Long userId = intent.getLongExtra("userId", 0);
		Long groupId = intent.getLongExtra("groupId", 0);
		Long repositoryId = intent.getLongExtra("repositoryId", 0);
		Long folderId = intent.getLongExtra("folderId", 0);
		int targetScreenletId = intent.getIntExtra("screenletId", 0);

		String path = file.getCurrentValue().getName();
		String name = path.substring(path.lastIndexOf("/") + 1);
		String date = new SimpleDateFormat("yyyyMMddHHmmdss").format(new Date());

		InputStream is = null;
		try {

			is = new FileInputStream(new File(path));
			Session session = SessionContext.createSessionFromCurrentSession();
			DLAppService service = new DLAppService(session);
			JSONObjectWrapper serviceContextWrapper = getJsonObjectWrapper(userId, groupId);
			JSONObject jsonObject = service.addFileEntry(repositoryId, folderId, name, getMimeType(path), date + "_" + name, "", "", getBytes(is), serviceContextWrapper);

			EventBusUtil.post(new DDLFormFileEvent(targetScreenletId, jsonObject, file));

		} catch (Exception e) {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
				}
			}
			EventBusUtil.post(new DDLFormFileEvent(targetScreenletId, e, file));
		}
	}

	private JSONObjectWrapper getJsonObjectWrapper(Long userId, Long groupId) throws JSONException {
		JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", userId);
		serviceContextAttributes.put("scopeGroupId", groupId);
		return new JSONObjectWrapper(serviceContextAttributes);
	}

	private byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			byteBuffer.write(buffer, 0, len);
		}
		return byteBuffer.toByteArray();
	}

	private static String getMimeType(String path) {
		String extension = MimeTypeMap.getFileExtensionFromUrl(path);
		if (extension != null) {
			return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		}
		return null;
	}

}