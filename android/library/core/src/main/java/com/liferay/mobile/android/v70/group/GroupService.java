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

package com.liferay.mobile.android.v70.group;

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
public class GroupService extends BaseService {

	public GroupService(Session session) {
		super(session);
	}

	public JSONArray search(long companyId, String name, String description, JSONArray params, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("params", checkNull(params));
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/group/search", _params);
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

	public JSONArray search(long companyId, JSONArray classNameIds, String name, String description, JSONObject params, boolean andOperator, int start, int end, JSONObjectWrapper obc) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("classNameIds", checkNull(classNameIds));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("params", checkNull(params));
			_params.put("andOperator", andOperator);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "obc", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group>", obc);

			_command.put("/group/search", _params);
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

	public JSONArray search(long companyId, JSONArray classNameIds, String keywords, JSONObject params, int start, int end, JSONObjectWrapper obc) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("classNameIds", checkNull(classNameIds));
			_params.put("keywords", checkNull(keywords));
			_params.put("params", checkNull(params));
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "obc", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.Group>", obc);

			_command.put("/group/search", _params);
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

	public JSONArray getGroups(long companyId, long parentGroupId, boolean site) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("parentGroupId", parentGroupId);
			_params.put("site", site);

			_command.put("/group/get-groups", _params);
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

	public JSONObject addGroup(long parentGroupId, long liveGroupId, String name, String description, int type, boolean manualMembership, int membershipRestriction, String friendlyURL, boolean site, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentGroupId", parentGroupId);
			_params.put("liveGroupId", liveGroupId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("type", type);
			_params.put("manualMembership", manualMembership);
			_params.put("membershipRestriction", membershipRestriction);
			_params.put("friendlyURL", checkNull(friendlyURL));
			_params.put("site", site);
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/group/add-group", _params);
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

	public JSONObject addGroup(long parentGroupId, long liveGroupId, JSONObject nameMap, JSONObject descriptionMap, int type, boolean manualMembership, int membershipRestriction, String friendlyURL, boolean site, boolean inheritContent, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentGroupId", parentGroupId);
			_params.put("liveGroupId", liveGroupId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("type", type);
			_params.put("manualMembership", manualMembership);
			_params.put("membershipRestriction", membershipRestriction);
			_params.put("friendlyURL", checkNull(friendlyURL));
			_params.put("site", site);
			_params.put("inheritContent", inheritContent);
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/group/add-group", _params);
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

	public JSONObject addGroup(long parentGroupId, long liveGroupId, JSONObject nameMap, JSONObject descriptionMap, int type, boolean manualMembership, int membershipRestriction, String friendlyURL, boolean site, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentGroupId", parentGroupId);
			_params.put("liveGroupId", liveGroupId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("type", type);
			_params.put("manualMembership", manualMembership);
			_params.put("membershipRestriction", membershipRestriction);
			_params.put("friendlyURL", checkNull(friendlyURL));
			_params.put("site", site);
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/group/add-group", _params);
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

	public JSONObject getGroup(long companyId, String groupKey) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupKey", checkNull(groupKey));

			_command.put("/group/get-group", _params);
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

	public JSONObject getGroup(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/group/get-group", _params);
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

	public JSONObject getUserGroup(long companyId, long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("userId", userId);

			_command.put("/group/get-user-group", _params);
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

	public JSONObject getCompanyGroup(long companyId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);

			_command.put("/group/get-company-group", _params);
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

	public JSONObject updateGroup(long groupId, String typeSettings) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("typeSettings", checkNull(typeSettings));

			_command.put("/group/update-group", _params);
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

	public JSONObject updateGroup(long groupId, long parentGroupId, JSONObject nameMap, JSONObject descriptionMap, int type, boolean manualMembership, int membershipRestriction, String friendlyURL, boolean inheritContent, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("parentGroupId", parentGroupId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("type", type);
			_params.put("manualMembership", manualMembership);
			_params.put("membershipRestriction", membershipRestriction);
			_params.put("friendlyURL", checkNull(friendlyURL));
			_params.put("inheritContent", inheritContent);
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/group/update-group", _params);
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

	public JSONObject updateGroup(long groupId, long parentGroupId, String name, String description, int type, boolean manualMembership, int membershipRestriction, String friendlyURL, boolean inheritContent, boolean active, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("parentGroupId", parentGroupId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("type", type);
			_params.put("manualMembership", manualMembership);
			_params.put("membershipRestriction", membershipRestriction);
			_params.put("friendlyURL", checkNull(friendlyURL));
			_params.put("inheritContent", inheritContent);
			_params.put("active", active);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/group/update-group", _params);
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

	public JSONArray getUserSitesGroups() throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_command.put("/group/get-user-sites-groups", _params);
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

	public JSONArray getUserSitesGroups(long userId, JSONArray classNames, int max) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("classNames", checkNull(classNames));
			_params.put("max", max);

			_command.put("/group/get-user-sites-groups", _params);
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

	public JSONArray getUserSitesGroups(JSONArray classNames, int max) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("classNames", checkNull(classNames));
			_params.put("max", max);

			_command.put("/group/get-user-sites-groups", _params);
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

	public Integer searchCount(long companyId, String name, String description, JSONArray params) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("params", checkNull(params));

			_command.put("/group/search-count", _params);
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

	public void disableStaging(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/group/disable-staging", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public Boolean hasUserGroup(long userId, long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("groupId", groupId);

			_command.put("/group/has-user-group", _params);
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

	public void checkRemoteStagingGroup(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/group/check-remote-staging-group", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addRoleGroups(long roleId, JSONArray groupIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("groupIds", checkNull(groupIds));

			_command.put("/group/add-role-groups", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void enableStaging(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/group/enable-staging", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getOrganizationsGroups(JSONArray organizations) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizations", checkNull(organizations));

			_command.put("/group/get-organizations-groups", _params);
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

	public JSONArray getUserGroupsGroups(JSONArray userGroups) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userGroups", checkNull(userGroups));

			_command.put("/group/get-user-groups-groups", _params);
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

	public JSONArray getUserOrganizationsGroups(long userId, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/group/get-user-organizations-groups", _params);
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

	public void setRoleGroups(long roleId, JSONArray groupIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("groupIds", checkNull(groupIds));

			_command.put("/group/set-role-groups", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetRoleGroups(long roleId, JSONArray groupIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("roleId", roleId);
			_params.put("groupIds", checkNull(groupIds));

			_command.put("/group/unset-role-groups", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateFriendlyUrl(long groupId, String friendlyURL) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("friendlyURL", checkNull(friendlyURL));

			_command.put("/group/update-friendly-url", _params);
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

	public String getGroupDisplayUrl(long groupId, boolean privateLayout, boolean secureConnection) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("secureConnection", secureConnection);

			_command.put("/group/get-group-display-url", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getString(0);
	}

	public JSONArray getManageableSiteGroups(JSONArray portlets, int max) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("portlets", checkNull(portlets));
			_params.put("max", max);

			_command.put("/group/get-manageable-site-groups", _params);
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

	public Integer getUserSitesGroupsCount() throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_command.put("/group/get-user-sites-groups-count", _params);
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

	public void updateStagedPortlets(long groupId, JSONObject stagedPortletIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("stagedPortletIds", checkNull(stagedPortletIds));

			_command.put("/group/update-staged-portlets", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteGroup(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/group/delete-group", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}