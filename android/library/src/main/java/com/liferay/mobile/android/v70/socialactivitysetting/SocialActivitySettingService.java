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

package com.liferay.mobile.android.v70.socialactivitysetting;

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
public class SocialActivitySettingService extends BaseService {

	public SocialActivitySettingService(Session session) {
		super(session);
	}

	public JSONArray getActivitySettings(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/socialactivitysetting/get-activity-settings", _params);
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

	public void updateActivitySetting(long groupId, String className, boolean enabled) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("enabled", enabled);

			_command.put("/socialactivitysetting/update-activity-setting", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void updateActivitySetting(long groupId, String className, int activityType, JSONObjectWrapper activityCounterDefinition) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("activityType", activityType);
			mangleWrapper(_params, "activityCounterDefinition", "com.liferay.portlet.social.model.SocialActivityCounterDefinition", activityCounterDefinition);

			_command.put("/socialactivitysetting/update-activity-setting", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void updateActivitySettings(long groupId, String className, int activityType, JSONArray activityCounterDefinitions) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("activityType", activityType);
			_params.put("activityCounterDefinitions", checkNull(activityCounterDefinitions));

			_command.put("/socialactivitysetting/update-activity-settings", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getJsonActivityDefinitions(long groupId, String className) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));

			_command.put("/socialactivitysetting/get-json-activity-definitions", _params);
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

	public JSONObject getActivityDefinition(long groupId, String className, int activityType) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("activityType", activityType);

			_command.put("/socialactivitysetting/get-activity-definition", _params);
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

	public JSONArray getActivityDefinitions(long groupId, String className) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));

			_command.put("/socialactivitysetting/get-activity-definitions", _params);
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

}