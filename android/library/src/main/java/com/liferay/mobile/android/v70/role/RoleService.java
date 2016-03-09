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

package com.liferay.mobile.android.v70.role;

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
public class RoleService extends BaseService {

	public RoleService(Session session) {
		super(session);
	}

	public JSONArray search(long companyId, String keywords, JSONArray types, JSONObject params, int start, int end, JSONObjectWrapper obc) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("keywords", checkNull(keywords));
			_params.put("types", checkNull(types));
			_params.put("params", checkNull(params));
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "obc", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Role>", obc);

			_command.put("/role/search", _params);
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

	public JSONArray getRoles(int type, String subtype) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("type", type);
			_params.put("subtype", checkNull(subtype));

			_command.put("/role/get-roles", _params);
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

	public JSONArray getRoles(long companyId, JSONArray types) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("types", checkNull(types));

			_command.put("/role/get-roles", _params);
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

	public JSONObject getRole(long companyId, String name) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));

			_command.put("/role/get-role", _params);
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

	public JSONObject getRole(long roleId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);

			_command.put("/role/get-role", _params);
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

	public JSONObject addRole(String className, long classPK, String name, JSONObject titleMap, JSONObject descriptionMap, int type, String subtype, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("name", checkNull(name));
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("type", type);
			_params.put("subtype", checkNull(subtype));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/role/add-role", _params);
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

	public JSONObject fetchRole(long roleId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);

			_command.put("/role/fetch-role", _params);
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

	public Boolean hasUserRoles(long userId, long companyId, JSONArray names, boolean inherited) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("companyId", companyId);
			_params.put("names", checkNull(names));
			_params.put("inherited", inherited);

			_command.put("/role/has-user-roles", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public JSONObject updateRole(long roleId, String name, JSONObject titleMap, JSONObject descriptionMap, String subtype, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("name", checkNull(name));
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("subtype", checkNull(subtype));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/role/update-role", _params);
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

	public Boolean hasUserRole(long userId, long companyId, String name, boolean inherited) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("inherited", inherited);

			_command.put("/role/has-user-role", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getBoolean(0);
	}

	public JSONArray getUserRoles(long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);

			_command.put("/role/get-user-roles", _params);
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

	public Integer searchCount(long companyId, String keywords, JSONArray types, JSONObject params) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("keywords", checkNull(keywords));
			_params.put("types", checkNull(types));
			_params.put("params", checkNull(params));

			_command.put("/role/search-count", _params);
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

	public JSONArray getUserGroupRoles(long userId, long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("groupId", groupId);

			_command.put("/role/get-user-group-roles", _params);
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

	public void deleteRole(long roleId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);

			_command.put("/role/delete-role", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getUserRelatedRoles(long userId, JSONArray groups) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("groups", checkNull(groups));

			_command.put("/role/get-user-related-roles", _params);
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

	public void addUserRoles(long userId, JSONArray roleIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("roleIds", checkNull(roleIds));

			_command.put("/role/add-user-roles", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getUserGroupGroupRoles(long userId, long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("groupId", groupId);

			_command.put("/role/get-user-group-group-roles", _params);
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

	public void unsetUserRoles(long userId, JSONArray roleIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("roleIds", checkNull(roleIds));

			_command.put("/role/unset-user-roles", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getGroupRoles(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/role/get-group-roles", _params);
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