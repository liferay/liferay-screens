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
import android.webkit.MimeTypeMap;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.dlapp.DLAppService;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormDocumentEvent;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.util.EventBusUtil;

import org.apache.http.entity.mime.content.InputStreamBody;
import org.json.JSONException;
import org.json.JSONObject;

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
		DocumentField file = intent.getParcelableExtra("file");
		Long userId = intent.getLongExtra("userId", 0);
		Long groupId = intent.getLongExtra("groupId", 0);
		Long repositoryId = intent.getLongExtra("repositoryId", 0);
		Long folderId = intent.getLongExtra("folderId", 0);
		String filePrefix = intent.getStringExtra("filePrefix");
		int targetScreenletId = intent.getIntExtra("screenletId", 0);

		InputStream is = null;
		try {
			String path = file.getCurrentValue();
			String name = path.substring(path.lastIndexOf("/") + 1);
			String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

			is = new FileInputStream(new File(path));
			InputStreamBody inputStreamBody = new InputStreamBody(is, name);

			Session session = SessionContext.createSessionFromCurrentSession();
			DLAppService service = new DLAppService(session);

			JSONObjectWrapper serviceContextWrapper = getJsonObjectWrapper(userId, groupId);

			String fileName = (filePrefix == null ? "" : filePrefix) + date + "_" + name;

			JSONObject jsonObject = service.addFileEntry(repositoryId, folderId, name,
					getMimeType(path), fileName, "", "", inputStreamBody, serviceContextWrapper);

			EventBusUtil.post(new DDLFormDocumentEvent(targetScreenletId, jsonObject, file));

		} catch (Exception e) {
			EventBusUtil.post(new DDLFormDocumentEvent(targetScreenletId, e, file));
		} finally {
			tryToCloseInputStream(is);
		}
	}

	private JSONObjectWrapper getJsonObjectWrapper(Long userId, Long groupId) throws JSONException {
		JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", userId);
		serviceContextAttributes.put("scopeGroupId", groupId);
		return new JSONObjectWrapper(serviceContextAttributes);
	}

	private static String getMimeType(String path) {
		String extension = MimeTypeMap.getFileExtensionFromUrl(path);
		if (extension != null) {
			return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
		}
		return null;
	}

	private void tryToCloseInputStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
	}
}