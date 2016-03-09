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

package com.liferay.mobile.android.v70.mdrrulegroupinstance;

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
public class MDRRuleGroupInstanceService extends BaseService {

	public MDRRuleGroupInstanceService(Session session) {
		super(session);
	}

	public JSONObject addRuleGroupInstance(long groupId, String className, long classPK, long ruleGroupId, int priority, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("ruleGroupId", ruleGroupId);
			_params.put("priority", priority);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/mdr.mdrrulegroupinstance/add-rule-group-instance", _params);
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

	public JSONObject addRuleGroupInstance(long groupId, String className, long classPK, long ruleGroupId, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("ruleGroupId", ruleGroupId);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/mdr.mdrrulegroupinstance/add-rule-group-instance", _params);
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

	public void deleteRuleGroupInstance(long ruleGroupInstanceId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("ruleGroupInstanceId", ruleGroupInstanceId);

			_command.put("/mdr.mdrrulegroupinstance/delete-rule-group-instance", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getRuleGroupInstances(String className, long classPK, int start, int end, JSONObjectWrapper orderByComparator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.mobile.device.rules.model.MDRRuleGroupInstance>", orderByComparator);

			_command.put("/mdr.mdrrulegroupinstance/get-rule-group-instances", _params);
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

	public Integer getRuleGroupInstancesCount(String className, long classPK) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);

			_command.put("/mdr.mdrrulegroupinstance/get-rule-group-instances-count", _params);
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

	public JSONObject updateRuleGroupInstance(long ruleGroupInstanceId, int priority) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("ruleGroupInstanceId", ruleGroupInstanceId);
			_params.put("priority", priority);

			_command.put("/mdr.mdrrulegroupinstance/update-rule-group-instance", _params);
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