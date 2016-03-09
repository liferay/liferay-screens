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

package com.liferay.mobile.android.v70.usergroupgrouprole;

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
public class UserGroupGroupRoleService extends BaseService {

	public UserGroupGroupRoleService(Session session) {
		super(session);
	}

	public void deleteUserGroupGroupRoles(long userGroupId, long groupId, JSONArray roleIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupId", userGroupId);
			_params.put("groupId", groupId);
			_params.put("roleIds", checkNull(roleIds));

			_command.put("/usergroupgrouprole/delete-user-group-group-roles", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteUserGroupGroupRoles(JSONArray userGroupIds, long groupId, long roleId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupIds", checkNull(userGroupIds));
			_params.put("groupId", groupId);
			_params.put("roleId", roleId);

			_command.put("/usergroupgrouprole/delete-user-group-group-roles", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addUserGroupGroupRoles(long userGroupId, long groupId, JSONArray roleIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupId", userGroupId);
			_params.put("groupId", groupId);
			_params.put("roleIds", checkNull(roleIds));

			_command.put("/usergroupgrouprole/add-user-group-group-roles", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addUserGroupGroupRoles(JSONArray userGroupIds, long groupId, long roleId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroupIds", checkNull(userGroupIds));
			_params.put("groupId", groupId);
			_params.put("roleId", roleId);

			_command.put("/usergroupgrouprole/add-user-group-group-roles", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}