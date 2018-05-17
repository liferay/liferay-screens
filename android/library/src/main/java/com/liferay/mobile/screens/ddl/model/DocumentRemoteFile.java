package com.liferay.mobile.screens.ddl.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DocumentRemoteFile extends DocumentFile {

	private long groupId;
	private String uuid;
	private int version;
	private String title;

	private String url;

	public DocumentRemoteFile(String json) throws JSONException {
		if (json.startsWith("http")) {
			url = json;

		} else {

			JSONObject jsonObject = new JSONObject(json);
			uuid = jsonObject.getString("uuid");
			version = jsonObject.getInt("version");
			groupId = jsonObject.getInt("groupId");

			// this is empty if we're retrieving the record
			title = jsonObject.optString("title");
		}
	}

	@Override
	public String toData() {
		if (url != null) {
			return url;
		}
		return "{\"groupId\":" + groupId + ", " + "\"uuid\":\"" + uuid + "\", " + "\"version\":" + version + "}";
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
}