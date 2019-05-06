package com.liferay.mobile.screens.ddl.form.connector;

import com.liferay.mobile.android.http.file.UploadData;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.DLAppService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DLAppConnector70 implements DLAppConnector {
    private final DLAppService dlAppService;

    public DLAppConnector70(Session session) {
        dlAppService = new DLAppService(session);
    }

    @Override
    public JSONObject addFileEntry(Long repositoryId, Long folderId, String name, String mimeType, String fileName,
        String s, String s1, byte[] bytes, JSONObjectWrapper serviceContextWrapper) throws Exception {
        return dlAppService.addFileEntry(repositoryId, folderId, name, mimeType, fileName, s, s1, bytes,
            serviceContextWrapper);
    }

    @Override
    public JSONObject addFileEntry(Long repositoryId, Long folderId, String name, String mimeType, String fileName,
        String s, String s1, UploadData uploadData, JSONObjectWrapper serviceContextWrapper) throws Exception {
        return dlAppService.addFileEntry(repositoryId, folderId, name, mimeType, fileName, s, s1, uploadData,
            serviceContextWrapper);
    }

    @Override
    public JSONArray getFileEntries(long repositoryId, long folderId, JSONArray mimeTypes, int startRow, int endRow,
        JSONObjectWrapper comparatorJSONWrapper) throws Exception {
        return dlAppService.getFileEntries(repositoryId, folderId, mimeTypes, startRow, endRow, comparatorJSONWrapper);
    }

    @Override
    public Integer getFileEntriesCount(long repositoryId, long folderId, JSONArray mimeTypes) throws Exception {
        return dlAppService.getFileEntriesCount(repositoryId, folderId, mimeTypes);
    }

    @Override
    public void deleteFileEntry(long fileEntryId) throws Exception {
        dlAppService.deleteFileEntry(fileEntryId);
    }

    @Override
    public JSONObject getFileEntry(long groupId, long folderId, String title) throws Exception {
        return dlAppService.getFileEntry(groupId, folderId, title);
    }
}
