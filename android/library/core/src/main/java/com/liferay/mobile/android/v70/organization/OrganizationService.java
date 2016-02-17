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

package com.liferay.mobile.android.v70.organization;

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
public class OrganizationService extends BaseService {

	public OrganizationService(Session session) {
		super(session);
	}

	public JSONArray getOrganizations(long companyId, long parentOrganizationId, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("parentOrganizationId", parentOrganizationId);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/organization/get-organizations", _params);
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

	public JSONArray getOrganizations(long companyId, long parentOrganizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("parentOrganizationId", parentOrganizationId);

			_command.put("/organization/get-organizations", _params);
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

	public JSONObject addOrganization(long parentOrganizationId, String name, String type, long regionId, long countryId, long statusId, String comments, boolean site, JSONArray addresses, JSONArray emailAddresses, JSONArray orgLabors, JSONArray phones, JSONArray websites, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentOrganizationId", parentOrganizationId);
			_params.put("name", checkNull(name));
			_params.put("type", checkNull(type));
			_params.put("regionId", regionId);
			_params.put("countryId", countryId);
			_params.put("statusId", statusId);
			_params.put("comments", checkNull(comments));
			_params.put("site", site);
			_params.put("addresses", checkNull(addresses));
			_params.put("emailAddresses", checkNull(emailAddresses));
			_params.put("orgLabors", checkNull(orgLabors));
			_params.put("phones", checkNull(phones));
			_params.put("websites", checkNull(websites));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/organization/add-organization", _params);
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

	public JSONObject addOrganization(long parentOrganizationId, String name, String type, long regionId, long countryId, long statusId, String comments, boolean site, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentOrganizationId", parentOrganizationId);
			_params.put("name", checkNull(name));
			_params.put("type", checkNull(type));
			_params.put("regionId", regionId);
			_params.put("countryId", countryId);
			_params.put("statusId", statusId);
			_params.put("comments", checkNull(comments));
			_params.put("site", site);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/organization/add-organization", _params);
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

	public JSONObject updateOrganization(long organizationId, long parentOrganizationId, String name, String type, long regionId, long countryId, long statusId, String comments, boolean logo, byte[] logoBytes, boolean site, JSONArray addresses, JSONArray emailAddresses, JSONArray orgLabors, JSONArray phones, JSONArray websites, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);
			_params.put("parentOrganizationId", parentOrganizationId);
			_params.put("name", checkNull(name));
			_params.put("type", checkNull(type));
			_params.put("regionId", regionId);
			_params.put("countryId", countryId);
			_params.put("statusId", statusId);
			_params.put("comments", checkNull(comments));
			_params.put("logo", logo);
			_params.put("logoBytes", toString(logoBytes));
			_params.put("site", site);
			_params.put("addresses", checkNull(addresses));
			_params.put("emailAddresses", checkNull(emailAddresses));
			_params.put("orgLabors", checkNull(orgLabors));
			_params.put("phones", checkNull(phones));
			_params.put("websites", checkNull(websites));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/organization/update-organization", _params);
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

	public JSONObject updateOrganization(long organizationId, long parentOrganizationId, String name, String type, long regionId, long countryId, long statusId, String comments, boolean site, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);
			_params.put("parentOrganizationId", parentOrganizationId);
			_params.put("name", checkNull(name));
			_params.put("type", checkNull(type));
			_params.put("regionId", regionId);
			_params.put("countryId", countryId);
			_params.put("statusId", statusId);
			_params.put("comments", checkNull(comments));
			_params.put("site", site);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/organization/update-organization", _params);
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

	public JSONObject updateOrganization(long organizationId, long parentOrganizationId, String name, String type, long regionId, long countryId, long statusId, String comments, boolean site, JSONArray addresses, JSONArray emailAddresses, JSONArray orgLabors, JSONArray phones, JSONArray websites, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);
			_params.put("parentOrganizationId", parentOrganizationId);
			_params.put("name", checkNull(name));
			_params.put("type", checkNull(type));
			_params.put("regionId", regionId);
			_params.put("countryId", countryId);
			_params.put("statusId", statusId);
			_params.put("comments", checkNull(comments));
			_params.put("site", site);
			_params.put("addresses", checkNull(addresses));
			_params.put("emailAddresses", checkNull(emailAddresses));
			_params.put("orgLabors", checkNull(orgLabors));
			_params.put("phones", checkNull(phones));
			_params.put("websites", checkNull(websites));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/organization/update-organization", _params);
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

	public JSONObject fetchOrganization(long organizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);

			_command.put("/organization/fetch-organization", _params);
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

	public Long getOrganizationId(long companyId, String name) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("name", checkNull(name));

			_command.put("/organization/get-organization-id", _params);
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

	public JSONArray getUserOrganizations(long userId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("userId", userId);

			_command.put("/organization/get-user-organizations", _params);
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

	public JSONObject getOrganization(long organizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);

			_command.put("/organization/get-organization", _params);
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

	public void addGroupOrganizations(long groupId, JSONArray organizationIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("organizationIds", checkNull(organizationIds));

			_command.put("/organization/add-group-organizations", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void addPasswordPolicyOrganizations(long passwordPolicyId, JSONArray organizationIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("passwordPolicyId", passwordPolicyId);
			_params.put("organizationIds", checkNull(organizationIds));

			_command.put("/organization/add-password-policy-organizations", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteOrganization(long organizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);

			_command.put("/organization/delete-organization", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public Integer getOrganizationsCount(long companyId, long parentOrganizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("parentOrganizationId", parentOrganizationId);

			_command.put("/organization/get-organizations-count", _params);
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

	public void setGroupOrganizations(long groupId, JSONArray organizationIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("organizationIds", checkNull(organizationIds));

			_command.put("/organization/set-group-organizations", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetGroupOrganizations(long groupId, JSONArray organizationIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("organizationIds", checkNull(organizationIds));

			_command.put("/organization/unset-group-organizations", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsetPasswordPolicyOrganizations(long passwordPolicyId, JSONArray organizationIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("passwordPolicyId", passwordPolicyId);
			_params.put("organizationIds", checkNull(organizationIds));

			_command.put("/organization/unset-password-policy-organizations", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteLogo(long organizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);

			_command.put("/organization/delete-logo", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}