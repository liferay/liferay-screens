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

	public DocumentRemoteFile(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);

		uuid = jsonObject.getString("uuid");
		version = jsonObject.optInt("version");
		groupId = jsonObject.getInt("groupId");

		// this is empty if we're retrieving the record
		title = jsonObject.optString("title");
	}

	@Override
	public String toData() {
		return "{\"groupId\":" + groupId + ", " + "\"uuid\":\"" + uuid + "\", " + "\"version\":" + version + "}";
	}

	@Override
	public String toString() {
		return title.isEmpty() ? "File in server" : title;
	}

	@Override
	public boolean isValid() {
		return uuid != null;
	}
}