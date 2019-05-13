package com.liferay.mobile.screens.ddl.model;

import com.liferay.mobile.screens.util.AndroidUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DocumentRemoteFile extends DocumentFile {

    private String contentUrl;
    private String encodingFormat;
    private long fileEntryId;
    private long groupId;
    private long sizeInBytes;
    private String title;
    private String url;
    private String uuid;
    private String version;

    public DocumentRemoteFile(String json) throws JSONException {
        if (json.startsWith("http")) {
            url = json;
        } else {
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.length() > 0) {
                fillObject(jsonObject);
            }
        }
    }

    @Override
    public String toData() {
        if (url != null) {
            return url;
        }

        try {
            JSONObject jsonObject = new JSONObject();

            putIfExists("contentUrl", contentUrl, jsonObject);
            putIfExists("encodingFormat", encodingFormat, jsonObject);
            putIfExists("fileEntryId", fileEntryId, jsonObject);
            putIfExists("groupId", groupId, jsonObject);
            putIfExists("sizeInBytes", sizeInBytes, jsonObject);
            putIfExists("title", title, jsonObject);
            putIfExists("uuid", uuid, jsonObject);
            putIfExists("version", version, jsonObject);

            return jsonObject.toString();
        } catch (JSONException ex) {
            return null;
        }
    }

    @Override
    public String toString() {
        if (url != null) {
            return url;
        }

        return title.isEmpty() ? "File in server" : title;
    }

    @Override
    public boolean isValid() {
        return url != null || uuid != null;
    }

    @Override
    public String getFileName() {
        if (title != null) {
            return title;
        } else if (url != null) {
            return AndroidUtil.getFileNameFromPath(url);
        }

        return "";
    }

    private void fillObject(JSONObject jsonObject) {
        contentUrl = jsonObject.optString("contentUrl");
        encodingFormat = jsonObject.optString("encodingFormat");
        fileEntryId = jsonObject.optLong("id", 0);
        groupId = jsonObject.optInt("groupId", jsonObject.optInt("siteId"));
        sizeInBytes = jsonObject.optLong("sizeInBytes", 0);
        title = jsonObject.optString("title");
        uuid = jsonObject.optString("uuid");
        version = jsonObject.optString("version");
    }

    private void putIfExists(String key, String value, JSONObject jsonObject) throws JSONException {
        if (!EMPTY_STRING.equals(value)) {
            jsonObject.put(key, value);
        }
    }

    private void putIfExists(String key, long value, JSONObject jsonObject) throws JSONException {
        if (value > 0) {
            jsonObject.put(key, value);
        }
    }

    private static final String EMPTY_STRING = "";
}