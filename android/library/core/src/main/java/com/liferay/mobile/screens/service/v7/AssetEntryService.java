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

package com.liferay.mobile.screens.service.v7;

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
public class AssetEntryService extends BaseService {

	public AssetEntryService(Session session) {
		super(session);
	}

	public JSONArray getEntries(JSONObjectWrapper entryQuery) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "entryQuery", "com.liferay.portlet.asset.service.persistence.AssetEntryQuery", entryQuery);

			_command.put("/assetentry/get-entries", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public JSONObject getEntry(long entryId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("entryId", entryId);

			_command.put("/assetentry/get-entry", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateEntry(long groupId, long createDate, long modifiedDate, String className, long classPK, String classUuid, long classTypeId, JSONArray categoryIds, JSONArray tagNames, boolean visible, long startDate, long endDate, long expirationDate, String mimeType, String title, String description, String summary, String url, String layoutUuid, int height, int width, int priority, boolean sync) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("createDate", createDate);
			_params.put("modifiedDate", modifiedDate);
			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("classUuid", checkNull(classUuid));
			_params.put("classTypeId", classTypeId);
			_params.put("categoryIds", checkNull(categoryIds));
			_params.put("tagNames", checkNull(tagNames));
			_params.put("visible", visible);
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("expirationDate", expirationDate);
			_params.put("mimeType", checkNull(mimeType));
			_params.put("title", checkNull(title));
			_params.put("description", checkNull(description));
			_params.put("summary", checkNull(summary));
			_params.put("url", checkNull(url));
			_params.put("layoutUuid", checkNull(layoutUuid));
			_params.put("height", height);
			_params.put("width", width);
			_params.put("priority", priority);
			_params.put("sync", sync);

			_command.put("/assetentry/update-entry", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateEntry(long groupId, long createDate, long modifiedDate, String className, long classPK, String classUuid, long classTypeId, JSONArray categoryIds, JSONArray tagNames, boolean visible, long startDate, long endDate, long expirationDate, String mimeType, String title, String description, String summary, String url, String layoutUuid, int height, int width, double priority) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("createDate", createDate);
			_params.put("modifiedDate", modifiedDate);
			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("classUuid", checkNull(classUuid));
			_params.put("classTypeId", classTypeId);
			_params.put("categoryIds", checkNull(categoryIds));
			_params.put("tagNames", checkNull(tagNames));
			_params.put("visible", visible);
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("expirationDate", expirationDate);
			_params.put("mimeType", checkNull(mimeType));
			_params.put("title", checkNull(title));
			_params.put("description", checkNull(description));
			_params.put("summary", checkNull(summary));
			_params.put("url", checkNull(url));
			_params.put("layoutUuid", checkNull(layoutUuid));
			_params.put("height", height);
			_params.put("width", width);
			_params.put("priority", priority);

			_command.put("/assetentry/update-entry", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject fetchEntry(long entryId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("entryId", entryId);

			_command.put("/assetentry/fetch-entry", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public Integer getEntriesCount(JSONObjectWrapper entryQuery) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "entryQuery", "com.liferay.portlet.asset.service.persistence.AssetEntryQuery", entryQuery);

			_command.put("/assetentry/get-entries-count", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getInt(0);
	}

	public JSONArray getCompanyEntries(long companyId, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/assetentry/get-company-entries", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public Integer getCompanyEntriesCount(long companyId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);

			_command.put("/assetentry/get-company-entries-count", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getInt(0);
	}

	public JSONObject incrementViewCounter(String className, long classPK) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);

			_command.put("/assetentry/increment-view-counter", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

}