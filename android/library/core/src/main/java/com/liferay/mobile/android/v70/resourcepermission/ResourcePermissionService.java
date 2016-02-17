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

package com.liferay.mobile.android.v70.resourcepermission;

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
public class ResourcePermissionService extends BaseService {

	public ResourcePermissionService(Session session) {
		super(session);
	}

	public void addResourcePermission(long groupId, long companyId, String name, int scope, String primKey, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("scope", scope);
			_params.put("primKey", checkNull(primKey));
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourcepermission/add-resource-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void removeResourcePermission(long groupId, long companyId, String name, int scope, String primKey, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("scope", scope);
			_params.put("primKey", checkNull(primKey));
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourcepermission/remove-resource-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void removeResourcePermissions(long groupId, long companyId, String name, int scope, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("scope", scope);
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourcepermission/remove-resource-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void setIndividualResourcePermissions(long groupId, long companyId, String name, String primKey, long roleId, JSONArray actionIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("primKey", checkNull(primKey));
			_params.put("roleId", roleId);
			_params.put("actionIds", checkNull(actionIds));

			_command.put("/resourcepermission/set-individual-resource-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void setIndividualResourcePermissions(long groupId, long companyId, String name, String primKey, JSONObject roleIdsToActionIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("primKey", checkNull(primKey));
			_params.put("roleIdsToActionIds", checkNull(roleIdsToActionIds));

			_command.put("/resourcepermission/set-individual-resource-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}