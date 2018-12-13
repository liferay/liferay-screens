package com.liferay.mobile.screens.ddl.model;

import com.liferay.mobile.screens.ddl.exception.EmptyDocumentRemoteFileException;
import com.liferay.mobile.screens.util.AndroidUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DocumentRemoteFile extends DocumentFile {

    private long groupId;
    private String uuid;
    private String version;
    private String title;

    private String url;

    public DocumentRemoteFile(String json) throws EmptyDocumentRemoteFileException, JSONException {
        if (json.startsWith("http")) {
            url = json;
        } else {
            JSONObject jsonObject = new JSONObject(json);

            if (!jsonObject.keys().hasNext()) {
                throw new EmptyDocumentRemoteFileException();
            }

            uuid = jsonObject.optString("uuid");
            version = jsonObject.optString("version");
            groupId = jsonObject.optInt("groupId");

            // this is empty if we're retrieving the record
            title = jsonObject.optString("title");
        }
    }

    public DocumentRemoteFile(String url, String title) {
        this.url = url;
        this.title = title;
    }

    @Override
    public String toData() {
        if (url != null) {
            return url;
        }

        try {
            JSONObject jsonObject = new JSONObject();

            if (groupId > 0) {
                jsonObject.put("groupId", groupId);
            }

            if (!EMPTY_STRING.equals(uuid)) {
                jsonObject.put("uuid", uuid);
            }

            if (!EMPTY_STRING.equals(title)) {
                jsonObject.put("title", title);
            }

            if (!EMPTY_STRING.equals(version)) {
                jsonObject.put("version", version);
            }

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

    private static final String EMPTY_STRING = "";
}