package com.liferay.mobile.screens.ddl.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DocumentRemoteFile extends DocumentFile {

	public DocumentRemoteFile(String json) throws JSONException {
		JSONObject jsonObject = new JSONObject(json);

		_uuid = jsonObject.getString("uuid");
		_version = jsonObject.getInt("version");
		_groupId = jsonObject.getInt("groupId");

		// this is empty if we're retrieving the record
		_title = jsonObject.optString("title");
	}

	@Override
	public String toData() {
		return "{\"groupId\":" + _groupId + ", " +
				"\"uuid\":\"" + _uuid + "\", " +
				"\"version\":" + _version + "}";
	}

	@Override
	public String toString() {
		return _title.isEmpty() ? "File in server" : _title;
	}

	@Override
	public boolean isValid() {
		return _uuid != null;
	}

	private long _groupId;
	private String _uuid;
	private int _version;
	private String _title;
}