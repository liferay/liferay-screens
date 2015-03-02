package com.liferay.mobile.screens.ddl.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author Javier Gamarra
 */
public class RemoteFile extends DocumentFile implements Serializable {

	public RemoteFile(String name) {
		try {
			JSONObject jsonObject = new JSONObject(name);
			_uuid = jsonObject.getString("uuid");
			_version = jsonObject.getInt("version");
			_groupId = jsonObject.getInt("groupId");
			_name = jsonObject.getString("title");
		}
		catch (JSONException e) {
		}
	}

	@Override
	public String toData() {
		return "{groupId:" + _groupId + ",uuid:" + _uuid + "," +
				"version:" + _version + "}";
	}

	@Override
	public String toString() {
		return _name == null ? "File in server" : _name;
	}

	private Integer _groupId;
	private String _uuid;
	private Integer _version;
}