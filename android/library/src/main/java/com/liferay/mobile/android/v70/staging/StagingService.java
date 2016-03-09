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

package com.liferay.mobile.android.v70.staging;

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
public class StagingService extends BaseService {

	public StagingService(Session session) {
		super(session);
	}

	public Long createStagingRequest(long groupId, String checksum) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("checksum", checkNull(checksum));

			_command.put("/staging/create-staging-request", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public JSONObject publishStagingRequest(long stagingRequestId, JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("stagingRequestId", stagingRequestId);
			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/staging/publish-staging-request", _params);
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

	public JSONObject publishStagingRequest(long stagingRequestId, boolean privateLayout, JSONObject parameterMap) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("stagingRequestId", stagingRequestId);
			_params.put("privateLayout", privateLayout);
			_params.put("parameterMap", checkNull(parameterMap));

			_command.put("/staging/publish-staging-request", _params);
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

	public void cleanUpStagingRequest(long stagingRequestId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("stagingRequestId", stagingRequestId);

			_command.put("/staging/clean-up-staging-request", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void updateStagingRequest(long stagingRequestId, String fileName, byte[] bytes) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("stagingRequestId", stagingRequestId);
			_params.put("fileName", checkNull(fileName));
			_params.put("bytes", toString(bytes));

			_command.put("/staging/update-staging-request", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject validateStagingRequest(long stagingRequestId, boolean privateLayout, JSONObject parameterMap) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("stagingRequestId", stagingRequestId);
			_params.put("privateLayout", privateLayout);
			_params.put("parameterMap", checkNull(parameterMap));

			_command.put("/staging/validate-staging-request", _params);
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