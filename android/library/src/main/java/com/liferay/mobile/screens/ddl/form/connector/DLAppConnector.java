package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.http.file.UploadData;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface DLAppConnector {
    JSONObject addFileEntry(Long repositoryId, Long folderId, String name, String mimeType, String fileName, String s,
        String s1, byte[] bytes, JSONObjectWrapper serviceContextWrapper) throws Exception;

    JSONObject addFileEntry(Long repositoryId, Long folderId, String name, String mimeType, String fileName, String s,
        String s1, UploadData bytes, JSONObjectWrapper serviceContextWrapper) throws Exception;

    JSONArray getFileEntries(long repositoryId, long folderId, JSONArray mimeTypes, int startRow, int endRow,
        JSONObjectWrapper comparatorJSONWrapper) throws Exception;

    Integer getFileEntriesCount(long repositoryId, long folderId, JSONArray mimeTypes) throws Exception;

    void deleteFileEntry(long fileEntryId) throws Exception;

    JSONObject getFileEntry(long groupId, long folderId, String title) throws Exception;
}
