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

package com.liferay.mobile.android.v70.layoutprototype;

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
public class LayoutPrototypeService extends BaseService {

	public LayoutPrototypeService(Session session) {
		super(session);
	}

	public JSONArray search(long companyId, boolean active, JSONObjectWrapper obc) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("active", active);
			mangleWrapper(_params, "obc", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.LayoutPrototype>", obc);

			_command.put("/layoutprototype/search", _params);
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

	public JSONObject addLayoutPrototype(JSONObject nameMap, JSONObject descriptionMap, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layoutprototype/add-layout-prototype", _params);
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

	public JSONObject addLayoutPrototype(JSONObject nameMap, String description, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nameMap", checkNull(nameMap));
			_params.put("description", checkNull(description));
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layoutprototype/add-layout-prototype", _params);
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

	public JSONObject updateLayoutPrototype(long layoutPrototypeId, JSONObject nameMap, JSONObject descriptionMap, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("layoutPrototypeId", layoutPrototypeId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layoutprototype/update-layout-prototype", _params);
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

	public JSONObject updateLayoutPrototype(long layoutPrototypeId, JSONObject nameMap, String description, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("layoutPrototypeId", layoutPrototypeId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("description", checkNull(description));
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layoutprototype/update-layout-prototype", _params);
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

	public JSONObject getLayoutPrototype(long layoutPrototypeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("layoutPrototypeId", layoutPrototypeId);

			_command.put("/layoutprototype/get-layout-prototype", _params);
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

	public JSONObject fetchLayoutPrototype(long layoutPrototypeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("layoutPrototypeId", layoutPrototypeId);

			_command.put("/layoutprototype/fetch-layout-prototype", _params);
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

	public void deleteLayoutPrototype(long layoutPrototypeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("layoutPrototypeId", layoutPrototypeId);

			_command.put("/layoutprototype/delete-layout-prototype", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}