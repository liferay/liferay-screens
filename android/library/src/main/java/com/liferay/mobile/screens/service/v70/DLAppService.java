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

package com.liferay.mobile.screens.service.v70;

import com.liferay.mobile.android.http.file.UploadData;
import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class DLAppService extends BaseService {

    public DLAppService(Session session) {
        super(session);
    }

    public JSONObject addFileEntry(long repositoryId, long folderId, String sourceFileName, String mimeType,
        String title, String description, String changeLog, byte[] bytes, JSONObjectWrapper serviceContext)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("repositoryId", repositoryId);
            _params.put("folderId", folderId);
            _params.put("sourceFileName", checkNull(sourceFileName));
            _params.put("mimeType", checkNull(mimeType));
            _params.put("title", checkNull(title));
            _params.put("description", checkNull(description));
            _params.put("changeLog", checkNull(changeLog));
            _params.put("bytes", toString(bytes));
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlapp/add-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject addFileEntry(long repositoryId, long folderId, String sourceFileName, String mimeType,
        String title, String description, String changeLog, UploadData file, JSONObjectWrapper serviceContext)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("repositoryId", repositoryId);
            _params.put("folderId", folderId);
            _params.put("sourceFileName", checkNull(sourceFileName));
            _params.put("mimeType", checkNull(mimeType));
            _params.put("title", checkNull(title));
            _params.put("description", checkNull(description));
            _params.put("changeLog", checkNull(changeLog));
            _params.put("file", checkNull(file));
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlapp/add-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.upload(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public void deleteFileEntry(long fileEntryId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);

            _command.put("/dlapp/delete-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public JSONArray getFileEntries(long repositoryId, long folderId, JSONArray mimeTypes, int start, int end,
        JSONObjectWrapper obc) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("repositoryId", repositoryId);
            _params.put("folderId", folderId);
            _params.put("mimeTypes", checkNull(mimeTypes));
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.repository.model.FileEntry>",
                obc);

            _command.put("/dlapp/get-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public Integer getFileEntriesCount(long repositoryId, long folderId, JSONArray mimeTypes) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("repositoryId", repositoryId);
            _params.put("folderId", folderId);
            _params.put("mimeTypes", checkNull(mimeTypes));

            _command.put("/dlapp/get-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public JSONObject getFileEntry(long groupId, long folderId, String title) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("title", checkNull(title));

            _command.put("/dlapp/get-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }
}