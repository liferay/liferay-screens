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

package com.liferay.mobile.android.v70.resourceblock;

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
public class ResourceBlockService extends BaseService {

	public ResourceBlockService(Session session) {
		super(session);
	}

	public void addCompanyScopePermission(long scopeGroupId, long companyId, String name, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("scopeGroupId", scopeGroupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourceblock/add-company-scope-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void setIndividualScopePermissions(long companyId, long groupId, String name, long primKey, JSONObject roleIdsToActionIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));
			_params.put("primKey", primKey);
			_params.put("roleIdsToActionIds", checkNull(roleIdsToActionIds));

			_command.put("/resourceblock/set-individual-scope-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void setIndividualScopePermissions(long companyId, long groupId, String name, long primKey, long roleId, JSONArray actionIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));
			_params.put("primKey", primKey);
			_params.put("roleId", roleId);
			_params.put("actionIds", checkNull(actionIds));

			_command.put("/resourceblock/set-individual-scope-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addGroupScopePermission(long scopeGroupId, long companyId, long groupId, String name, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("scopeGroupId", scopeGroupId);
			_params.put("companyId", companyId);
			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourceblock/add-group-scope-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addIndividualScopePermission(long companyId, long groupId, String name, long primKey, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));
			_params.put("primKey", primKey);
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourceblock/add-individual-scope-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void removeAllGroupScopePermissions(long scopeGroupId, long companyId, String name, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("scopeGroupId", scopeGroupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourceblock/remove-all-group-scope-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void removeCompanyScopePermission(long scopeGroupId, long companyId, String name, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("scopeGroupId", scopeGroupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourceblock/remove-company-scope-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void removeGroupScopePermission(long scopeGroupId, long companyId, long groupId, String name, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("scopeGroupId", scopeGroupId);
			_params.put("companyId", companyId);
			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourceblock/remove-group-scope-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void removeIndividualScopePermission(long companyId, long groupId, String name, long primKey, long roleId, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));
			_params.put("primKey", primKey);
			_params.put("roleId", roleId);
			_params.put("actionId", checkNull(actionId));

			_command.put("/resourceblock/remove-individual-scope-permission", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void setCompanyScopePermissions(long scopeGroupId, long companyId, String name, long roleId, JSONArray actionIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("scopeGroupId", scopeGroupId);
			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("roleId", roleId);
			_params.put("actionIds", checkNull(actionIds));

			_command.put("/resourceblock/set-company-scope-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void setGroupScopePermissions(long scopeGroupId, long companyId, long groupId, String name, long roleId, JSONArray actionIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("scopeGroupId", scopeGroupId);
			_params.put("companyId", companyId);
			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));
			_params.put("roleId", roleId);
			_params.put("actionIds", checkNull(actionIds));

			_command.put("/resourceblock/set-group-scope-permissions", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}