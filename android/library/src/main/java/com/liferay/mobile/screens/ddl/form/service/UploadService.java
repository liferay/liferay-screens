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
import android.webkit.MimeTypeMap;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.connector.DLAppConnector;
import com.liferay.mobile.screens.ddl.form.interactor.upload.DDLFormDocumentUploadEvent;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UploadService extends IntentService {

    public static final int CONNECTION_TIMEOUT = 120000;

    public UploadService() {
        super(UploadService.class.getCanonicalName());
    }

    private static String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        if (extension != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return null;
    }

    @Override
    public void onHandleIntent(Intent intent) {
        uploadFromIntent(intent);
    }

    public void uploadFromIntent(Intent intent) {
        DocumentField file = intent.getParcelableExtra("file");
        Long userId = intent.getLongExtra("userId", 0);
        Long groupId = intent.getLongExtra("groupId", 0);
        Long repositoryId = intent.getLongExtra("repositoryId", 0);
        Long folderId = intent.getLongExtra("folderId", 0);
        String filePrefix = intent.getStringExtra("filePrefix");
        int targetScreenletId = intent.getIntExtra("targetScreenletId", 0);
        String actionName = intent.getStringExtra("actionName");
        Integer connectionTimeout = intent.getIntExtra("connectionTimeout", CONNECTION_TIMEOUT);

        try {
            JSONObject jsonObject =
                uploadFile(file, userId, groupId, repositoryId, folderId, filePrefix, connectionTimeout);

            DDLFormDocumentUploadEvent event =
                new DDLFormDocumentUploadEvent(file, repositoryId, folderId, filePrefix, connectionTimeout, jsonObject);
            decorateEvent(event, groupId, userId, null, targetScreenletId, actionName);
            EventBusUtil.post(event);
        } catch (Exception e) {
            DDLFormDocumentUploadEvent event = new DDLFormDocumentUploadEvent(e);
            decorateEvent(event, groupId, userId, null, targetScreenletId, actionName);
            event.setDocumentField(file);
            EventBusUtil.post(event);
        }
    }

    public JSONObject uploadFile(DocumentField file, Long userId, Long groupId, Long repositoryId, Long folderId,
        String filePrefix, Integer connectionTimeout) throws Exception {
        String path = file.getCurrentValue().toString();
        String name = path.substring(path.lastIndexOf('/') + 1);
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        Uri uri = Uri.parse(path);

        Session session = SessionContext.createSessionFromCurrentSession();
        session.setConnectionTimeout(connectionTimeout);

        DLAppConnector dlAppConnector = ServiceProvider.getInstance().getDLAppConnector(session);

        JSONObjectWrapper serviceContextWrapper = getJsonObjectWrapper(userId, groupId);

        InputStream inputStream = getContentResolver().openInputStream(uri);

        String fileName = (filePrefix == null ? "" : filePrefix) + date + "_" + name;

        return dlAppConnector.addFileEntry(repositoryId, folderId, name, getMimeType(path), fileName, "", "",
            readBytes(inputStream), serviceContextWrapper);
    }

    public byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

    private JSONObjectWrapper getJsonObjectWrapper(Long userId, Long groupId) throws JSONException {
        JSONObject serviceContextAttributes = new JSONObject();
        serviceContextAttributes.put("userId", userId);
        serviceContextAttributes.put("scopeGroupId", groupId);
        return new JSONObjectWrapper(serviceContextAttributes);
    }

    private void decorateEvent(CacheEvent event, long groupId, long userId, Locale locale, int targetScreenletId,
        String actionName) {
        event.setGroupId(groupId);
        event.setUserId(userId);
        event.setLocale(locale);
        event.setCached(false);
        event.setTargetScreenletId(targetScreenletId);
        event.setActionName(actionName);
    }
}
