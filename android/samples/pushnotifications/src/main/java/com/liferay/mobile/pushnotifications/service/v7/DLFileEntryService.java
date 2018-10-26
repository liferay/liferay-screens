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

package com.liferay.mobile.pushnotifications.service.v7;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class DLFileEntryService extends BaseService {

    public DLFileEntryService(Session session) {
        super(session);
    }

    public JSONObject cancelCheckOut(long fileEntryId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);

            _command.put("/dlfileentry/cancel-check-out", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public void checkInFileEntry(long fileEntryId, boolean major, String changeLog, JSONObjectWrapper serviceContext)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("major", major);
            _params.put("changeLog", checkNull(changeLog));
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/check-in-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public void checkInFileEntry(long fileEntryId, String lockUuid, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("lockUuid", checkNull(lockUuid));
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/check-in-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public JSONObject checkOutFileEntry(long fileEntryId, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/check-out-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject checkOutFileEntry(long fileEntryId, String owner, long expirationTime,
        JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("owner", checkNull(owner));
            _params.put("expirationTime", expirationTime);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/check-out-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject copyFileEntry(long groupId, long repositoryId, long fileEntryId, long destFolderId,
        JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("repositoryId", repositoryId);
            _params.put("fileEntryId", fileEntryId);
            _params.put("destFolderId", destFolderId);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/copy-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

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

            _command.put("/dlfileentry/delete-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public void deleteFileEntry(long groupId, long folderId, String title) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("title", checkNull(title));

            _command.put("/dlfileentry/delete-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public void deleteFileVersion(long fileEntryId, String version) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("version", checkNull(version));

            _command.put("/dlfileentry/delete-file-version", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public JSONObject fetchFileEntryByImageId(long imageId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("imageId", imageId);

            _command.put("/dlfileentry/fetch-file-entry-by-image-id", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONArray getFileEntries(long groupId, long folderId, int status, int start, int end, JSONObjectWrapper obc)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("status", status);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.document.library.kernel.model.DLFileEntry>",
                obc);

            _command.put("/dlfileentry/get-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public JSONArray getFileEntries(long groupId, long folderId, int start, int end, JSONObjectWrapper obc)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.document.library.kernel.model.DLFileEntry>",
                obc);

            _command.put("/dlfileentry/get-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public JSONArray getFileEntries(long groupId, long folderId, JSONArray mimeTypes, int start, int end,
        JSONObjectWrapper obc) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("mimeTypes", checkNull(mimeTypes));
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.document.library.kernel.model.DLFileEntry>",
                obc);

            _command.put("/dlfileentry/get-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public JSONArray getFileEntries(long groupId, long folderId, long fileEntryTypeId, int start, int end,
        JSONObjectWrapper obc) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("fileEntryTypeId", fileEntryTypeId);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.document.library.kernel.model.DLFileEntry>",
                obc);

            _command.put("/dlfileentry/get-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public Integer getFileEntriesCount(long groupId, long folderId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);

            _command.put("/dlfileentry/get-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Integer getFileEntriesCount(long groupId, long folderId, int status) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("status", status);

            _command.put("/dlfileentry/get-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Integer getFileEntriesCount(long groupId, long folderId, JSONArray mimeTypes) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("mimeTypes", checkNull(mimeTypes));

            _command.put("/dlfileentry/get-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Integer getFileEntriesCount(long groupId, long folderId, long fileEntryTypeId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("fileEntryTypeId", fileEntryTypeId);

            _command.put("/dlfileentry/get-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public JSONObject getFileEntry(long fileEntryId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);

            _command.put("/dlfileentry/get-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject getFileEntry(long groupId, long folderId, String title) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderId", folderId);
            _params.put("title", checkNull(title));

            _command.put("/dlfileentry/get-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject getFileEntryByUuidAndGroupId(String uuid, long groupId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("uuid", checkNull(uuid));
            _params.put("groupId", groupId);

            _command.put("/dlfileentry/get-file-entry-by-uuid-and-group-id", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject getFileEntryLock(long fileEntryId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);

            _command.put("/dlfileentry/get-file-entry-lock", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public Integer getFoldersFileEntriesCount(long groupId, JSONArray folderIds, int status) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("folderIds", checkNull(folderIds));
            _params.put("status", status);

            _command.put("/dlfileentry/get-folders-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public JSONArray getGroupFileEntries(long groupId, long userId, long rootFolderId, int start, int end,
        JSONObjectWrapper obc) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("userId", userId);
            _params.put("rootFolderId", rootFolderId);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.document.library.kernel.model.DLFileEntry>",
                obc);

            _command.put("/dlfileentry/get-group-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public JSONArray getGroupFileEntries(long groupId, long userId, long rootFolderId, JSONArray mimeTypes, int status,
        int start, int end, JSONObjectWrapper obc) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("userId", userId);
            _params.put("rootFolderId", rootFolderId);
            _params.put("mimeTypes", checkNull(mimeTypes));
            _params.put("status", status);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.document.library.kernel.model.DLFileEntry>",
                obc);

            _command.put("/dlfileentry/get-group-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public JSONArray getGroupFileEntries(long groupId, long userId, long repositoryId, long rootFolderId,
        JSONArray mimeTypes, int status, int start, int end, JSONObjectWrapper obc) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("userId", userId);
            _params.put("repositoryId", repositoryId);
            _params.put("rootFolderId", rootFolderId);
            _params.put("mimeTypes", checkNull(mimeTypes));
            _params.put("status", status);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "obc",
                "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.document.library.kernel.model.DLFileEntry>",
                obc);

            _command.put("/dlfileentry/get-group-file-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public Integer getGroupFileEntriesCount(long groupId, long userId, long rootFolderId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("userId", userId);
            _params.put("rootFolderId", rootFolderId);

            _command.put("/dlfileentry/get-group-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Integer getGroupFileEntriesCount(long groupId, long userId, long rootFolderId, JSONArray mimeTypes,
        int status) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("userId", userId);
            _params.put("rootFolderId", rootFolderId);
            _params.put("mimeTypes", checkNull(mimeTypes));
            _params.put("status", status);

            _command.put("/dlfileentry/get-group-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Integer getGroupFileEntriesCount(long groupId, long userId, long repositoryId, long rootFolderId,
        JSONArray mimeTypes, int status) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("userId", userId);
            _params.put("repositoryId", repositoryId);
            _params.put("rootFolderId", rootFolderId);
            _params.put("mimeTypes", checkNull(mimeTypes));
            _params.put("status", status);

            _command.put("/dlfileentry/get-group-file-entries-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Boolean hasFileEntryLock(long fileEntryId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);

            _command.put("/dlfileentry/has-file-entry-lock", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public Boolean isFileEntryCheckedOut(long fileEntryId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);

            _command.put("/dlfileentry/is-file-entry-checked-out", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public Boolean isKeepFileVersionLabel(long fileEntryId, boolean majorVersion, JSONObjectWrapper serviceContext)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("majorVersion", majorVersion);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/is-keep-file-version-label", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public Boolean isKeepFileVersionLabel(long fileEntryId, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/is-keep-file-version-label", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public JSONObject moveFileEntry(long fileEntryId, long newFolderId, JSONObjectWrapper serviceContext)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("newFolderId", newFolderId);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/move-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject refreshFileEntryLock(String lockUuid, long companyId, long expirationTime) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("lockUuid", checkNull(lockUuid));
            _params.put("companyId", companyId);
            _params.put("expirationTime", expirationTime);

            _command.put("/dlfileentry/refresh-file-entry-lock", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public void revertFileEntry(long fileEntryId, String version, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("version", checkNull(version));
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);

            _command.put("/dlfileentry/revert-file-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public JSONObject search(long groupId, long creatorUserId, int status, int start, int end) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("creatorUserId", creatorUserId);
            _params.put("status", status);
            _params.put("start", start);
            _params.put("end", end);

            _command.put("/dlfileentry/search", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject search(long groupId, long creatorUserId, long folderId, JSONArray mimeTypes, int status,
        int start, int end) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("creatorUserId", creatorUserId);
            _params.put("folderId", folderId);
            _params.put("mimeTypes", checkNull(mimeTypes));
            _params.put("status", status);
            _params.put("start", start);
            _params.put("end", end);

            _command.put("/dlfileentry/search", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject updateStatus(long userId, long fileVersionId, int status, JSONObjectWrapper serviceContext,
        JSONObject workflowContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("userId", userId);
            _params.put("fileVersionId", fileVersionId);
            _params.put("status", status);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.kernel.service.ServiceContext",
                serviceContext);
            _params.put("workflowContext", checkNull(workflowContext));

            _command.put("/dlfileentry/update-status", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public Boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("lockUuid", checkNull(lockUuid));

            _command.put("/dlfileentry/verify-file-entry-check-out", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public Boolean verifyFileEntryLock(long fileEntryId, String lockUuid) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("fileEntryId", fileEntryId);
            _params.put("lockUuid", checkNull(lockUuid));

            _command.put("/dlfileentry/verify-file-entry-lock", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }
}