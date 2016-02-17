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

package com.liferay.mobile.android.v70.layout;

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
public class LayoutService extends BaseService {

	public LayoutService(Session session) {
		super(session);
	}

	public JSONArray getLayouts(long groupId, boolean privateLayout) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);

			_command.put("/layout/get-layouts", _params);
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

	public JSONArray getLayouts(long groupId, boolean privateLayout, long parentLayoutId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parentLayoutId", parentLayoutId);

			_command.put("/layout/get-layouts", _params);
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

	public JSONArray getLayouts(long groupId, boolean privateLayout, long parentLayoutId, boolean incomplete, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parentLayoutId", parentLayoutId);
			_params.put("incomplete", incomplete);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/layout/get-layouts", _params);
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

	public void setLayouts(long groupId, boolean privateLayout, long parentLayoutId, JSONArray layoutIds, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parentLayoutId", parentLayoutId);
			_params.put("layoutIds", checkNull(layoutIds));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layout/set-layouts", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject addLayout(long groupId, boolean privateLayout, long parentLayoutId, JSONObject localeNamesMap, JSONObject localeTitlesMap, JSONObject descriptionMap, JSONObject keywordsMap, JSONObject robotsMap, String type, String typeSettings, boolean hidden, JSONObject friendlyURLMap, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parentLayoutId", parentLayoutId);
			_params.put("localeNamesMap", checkNull(localeNamesMap));
			_params.put("localeTitlesMap", checkNull(localeTitlesMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("keywordsMap", checkNull(keywordsMap));
			_params.put("robotsMap", checkNull(robotsMap));
			_params.put("type", checkNull(type));
			_params.put("typeSettings", checkNull(typeSettings));
			_params.put("hidden", hidden);
			_params.put("friendlyURLMap", checkNull(friendlyURLMap));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layout/add-layout", _params);
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

	public JSONObject addLayout(long groupId, boolean privateLayout, long parentLayoutId, String name, String title, String description, String type, boolean hidden, String friendlyURL, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parentLayoutId", parentLayoutId);
			_params.put("name", checkNull(name));
			_params.put("title", checkNull(title));
			_params.put("description", checkNull(description));
			_params.put("type", checkNull(type));
			_params.put("hidden", hidden);
			_params.put("friendlyURL", checkNull(friendlyURL));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layout/add-layout", _params);
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

	public JSONObject updateLayout(long groupId, boolean privateLayout, long layoutId, long parentLayoutId, JSONObject localeNamesMap, JSONObject localeTitlesMap, JSONObject descriptionMap, JSONObject keywordsMap, JSONObject robotsMap, String type, boolean hidden, JSONObject friendlyURLMap, boolean iconImage, byte[] iconBytes, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("parentLayoutId", parentLayoutId);
			_params.put("localeNamesMap", checkNull(localeNamesMap));
			_params.put("localeTitlesMap", checkNull(localeTitlesMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("keywordsMap", checkNull(keywordsMap));
			_params.put("robotsMap", checkNull(robotsMap));
			_params.put("type", checkNull(type));
			_params.put("hidden", hidden);
			_params.put("friendlyURLMap", checkNull(friendlyURLMap));
			_params.put("iconImage", iconImage);
			_params.put("iconBytes", toString(iconBytes));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layout/update-layout", _params);
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

	public JSONObject updateLayout(long groupId, boolean privateLayout, long layoutId, String typeSettings) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("typeSettings", checkNull(typeSettings));

			_command.put("/layout/update-layout", _params);
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

	public Long getDefaultPlid(long groupId, long scopeGroupId, String portletId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("scopeGroupId", scopeGroupId);
			_params.put("portletId", checkNull(portletId));

			_command.put("/layout/get-default-plid", _params);
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

	public Long getDefaultPlid(long groupId, long scopeGroupId, boolean privateLayout, String portletId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("scopeGroupId", scopeGroupId);
			_params.put("privateLayout", privateLayout);
			_params.put("portletId", checkNull(portletId));

			_command.put("/layout/get-default-plid", _params);
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

	public Integer getLayoutsCount(long groupId, boolean privateLayout, long parentLayoutId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parentLayoutId", parentLayoutId);

			_command.put("/layout/get-layouts-count", _params);
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

	public void schedulePublishToLive(long sourceGroupId, long targetGroupId, boolean privateLayout, JSONArray layoutIds, JSONObject parameterMap, String scope, long startDate, long endDate, String groupName, String cronText, long schedulerStartDate, long schedulerEndDate, String description) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("sourceGroupId", sourceGroupId);
			_params.put("targetGroupId", targetGroupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIds", checkNull(layoutIds));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("scope", checkNull(scope));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("groupName", checkNull(groupName));
			_params.put("cronText", checkNull(cronText));
			_params.put("schedulerStartDate", schedulerStartDate);
			_params.put("schedulerEndDate", schedulerEndDate);
			_params.put("description", checkNull(description));

			_command.put("/layout/schedule-publish-to-live", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void schedulePublishToLive(long sourceGroupId, long targetGroupId, boolean privateLayout, JSONArray layoutIds, JSONObject parameterMap, String groupName, String cronText, long schedulerStartDate, long schedulerEndDate, String description) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("sourceGroupId", sourceGroupId);
			_params.put("targetGroupId", targetGroupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIds", checkNull(layoutIds));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("groupName", checkNull(groupName));
			_params.put("cronText", checkNull(cronText));
			_params.put("schedulerStartDate", schedulerStartDate);
			_params.put("schedulerEndDate", schedulerEndDate);
			_params.put("description", checkNull(description));

			_command.put("/layout/schedule-publish-to-live", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void schedulePublishToLive(long sourceGroupId, long targetGroupId, boolean privateLayout, JSONObject layoutIdMap, JSONObject parameterMap, String scope, long startDate, long endDate, String groupName, String cronText, long schedulerStartDate, long schedulerEndDate, String description) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("sourceGroupId", sourceGroupId);
			_params.put("targetGroupId", targetGroupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIdMap", checkNull(layoutIdMap));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("scope", checkNull(scope));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("groupName", checkNull(groupName));
			_params.put("cronText", checkNull(cronText));
			_params.put("schedulerStartDate", schedulerStartDate);
			_params.put("schedulerEndDate", schedulerEndDate);
			_params.put("description", checkNull(description));

			_command.put("/layout/schedule-publish-to-live", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void schedulePublishToRemote(long sourceGroupId, boolean privateLayout, JSONObject layoutIdMap, JSONObject parameterMap, String remoteAddress, int remotePort, String remotePathContext, boolean secureConnection, long remoteGroupId, boolean remotePrivateLayout, long startDate, long endDate, String groupName, String cronText, long schedulerStartDate, long schedulerEndDate, String description) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("sourceGroupId", sourceGroupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIdMap", checkNull(layoutIdMap));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("remoteAddress", checkNull(remoteAddress));
			_params.put("remotePort", remotePort);
			_params.put("remotePathContext", checkNull(remotePathContext));
			_params.put("secureConnection", secureConnection);
			_params.put("remoteGroupId", remoteGroupId);
			_params.put("remotePrivateLayout", remotePrivateLayout);
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("groupName", checkNull(groupName));
			_params.put("cronText", checkNull(cronText));
			_params.put("schedulerStartDate", schedulerStartDate);
			_params.put("schedulerEndDate", schedulerEndDate);
			_params.put("description", checkNull(description));

			_command.put("/layout/schedule-publish-to-remote", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unschedulePublishToLive(long groupId, String jobName, String groupName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("jobName", checkNull(jobName));
			_params.put("groupName", checkNull(groupName));

			_command.put("/layout/unschedule-publish-to-live", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unschedulePublishToRemote(long groupId, String jobName, String groupName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("jobName", checkNull(jobName));
			_params.put("groupName", checkNull(groupName));

			_command.put("/layout/unschedule-publish-to-remote", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteTempFileEntry(long groupId, String folderName, String fileName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("folderName", checkNull(folderName));
			_params.put("fileName", checkNull(fileName));

			_command.put("/layout/delete-temp-file-entry", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject getLayoutByUuidAndGroupId(String uuid, long groupId, boolean privateLayout) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("uuid", checkNull(uuid));
			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);

			_command.put("/layout/get-layout-by-uuid-and-group-id", _params);
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

	public JSONObject exportPortletInfoAsFile(long plid, long groupId, String portletId, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("groupId", groupId);
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-portlet-info-as-file", _params);
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

	public JSONObject exportPortletInfoAsFile(JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/layout/export-portlet-info-as-file", _params);
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

	public JSONObject exportPortletInfoAsFile(String portletId, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-portlet-info-as-file", _params);
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

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layout/delete-layout", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteLayout(long plid, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/layout/delete-layout", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray exportLayouts(long groupId, boolean privateLayout, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-layouts", _params);
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

	public JSONArray exportLayouts(long groupId, boolean privateLayout, JSONArray layoutIds, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIds", checkNull(layoutIds));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-layouts", _params);
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

	public Long exportLayoutsAsFileInBackground(JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/layout/export-layouts-as-file-in-background", _params);
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

	public Long exportLayoutsAsFileInBackground(long exportImportConfigurationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("exportImportConfigurationId", exportImportConfigurationId);

			_command.put("/layout/export-layouts-as-file-in-background", _params);
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

	public Long exportLayoutsAsFileInBackground(String taskName, long groupId, boolean privateLayout, JSONArray layoutIds, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("taskName", checkNull(taskName));
			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIds", checkNull(layoutIds));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-layouts-as-file-in-background", _params);
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

	public Long exportLayoutsAsFileInBackground(String taskName, long groupId, boolean privateLayout, JSONArray layoutIds, JSONObject parameterMap, long startDate, long endDate, String fileName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("taskName", checkNull(taskName));
			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIds", checkNull(layoutIds));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("fileName", checkNull(fileName));

			_command.put("/layout/export-layouts-as-file-in-background", _params);
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

	public JSONArray exportPortletInfo(long companyId, String portletId, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-portlet-info", _params);
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

	public JSONArray exportPortletInfo(long plid, long groupId, String portletId, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("groupId", groupId);
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-portlet-info", _params);
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

	public Long exportPortletInfoAsFileInBackground(String taskName, long plid, long groupId, String portletId, JSONObject parameterMap, long startDate, long endDate, String fileName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("taskName", checkNull(taskName));
			_params.put("plid", plid);
			_params.put("groupId", groupId);
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("fileName", checkNull(fileName));

			_command.put("/layout/export-portlet-info-as-file-in-background", _params);
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

	public Long exportPortletInfoAsFileInBackground(String taskName, String portletId, JSONObject parameterMap, long startDate, long endDate, String fileName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("taskName", checkNull(taskName));
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("fileName", checkNull(fileName));

			_command.put("/layout/export-portlet-info-as-file-in-background", _params);
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

	public Long importLayoutsInBackground(String taskName, long groupId, boolean privateLayout, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("taskName", checkNull(taskName));
			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/import-layouts-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public void importPortletInfo(String portletId, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/import-portlet-info", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public void importPortletInfo(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/layout/import-portlet-info", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public void importPortletInfo(long plid, long groupId, String portletId, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("groupId", groupId);
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/import-portlet-info", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public Long importPortletInfoInBackground(String taskName, long plid, long groupId, String portletId, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("taskName", checkNull(taskName));
			_params.put("plid", plid);
			_params.put("groupId", groupId);
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/import-portlet-info-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public void importPortletInfoInBackground(String taskName, String portletId, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("taskName", checkNull(taskName));
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/import-portlet-info-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public JSONObject updateIconImage(long plid, byte[] bytes) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("bytes", toString(bytes));

			_command.put("/layout/update-icon-image", _params);
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

	public JSONObject updateName(long groupId, boolean privateLayout, long layoutId, String name, String languageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("name", checkNull(name));
			_params.put("languageId", checkNull(languageId));

			_command.put("/layout/update-name", _params);
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

	public JSONObject updateName(long plid, String name, String languageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("name", checkNull(name));
			_params.put("languageId", checkNull(languageId));

			_command.put("/layout/update-name", _params);
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

	public JSONObject updateParentLayoutId(long groupId, boolean privateLayout, long layoutId, long parentLayoutId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("parentLayoutId", parentLayoutId);

			_command.put("/layout/update-parent-layout-id", _params);
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

	public JSONObject updateParentLayoutId(long plid, long parentPlid) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("parentPlid", parentPlid);

			_command.put("/layout/update-parent-layout-id", _params);
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

	public JSONObject updateParentLayoutIdAndPriority(long plid, long parentPlid, int priority) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("parentPlid", parentPlid);
			_params.put("priority", priority);

			_command.put("/layout/update-parent-layout-id-and-priority", _params);
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

	public JSONObject updatePriority(long groupId, boolean privateLayout, long layoutId, long nextLayoutId, long previousLayoutId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("nextLayoutId", nextLayoutId);
			_params.put("previousLayoutId", previousLayoutId);

			_command.put("/layout/update-priority", _params);
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

	public JSONObject updatePriority(long groupId, boolean privateLayout, long layoutId, int priority) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("priority", priority);

			_command.put("/layout/update-priority", _params);
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

	public JSONObject updatePriority(long plid, int priority) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("priority", priority);

			_command.put("/layout/update-priority", _params);
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

	public JSONObject validateImportLayoutsFile(long groupId, boolean privateLayout, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/validate-import-layouts-file", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject validateImportLayoutsFile(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/layout/validate-import-layouts-file", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject validateImportPortletInfo(long plid, long groupId, String portletId, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("groupId", groupId);
			_params.put("portletId", checkNull(portletId));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/validate-import-portlet-info", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject validateImportPortletInfo(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/layout/validate-import-portlet-info", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONArray getAncestorLayouts(long plid) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);

			_command.put("/layout/get-ancestor-layouts", _params);
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

	public String getLayoutName(long groupId, boolean privateLayout, long layoutId, String languageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("languageId", checkNull(languageId));

			_command.put("/layout/get-layout-name", _params);
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

	public JSONArray getLayoutReferences(long companyId, String portletId, String preferencesKey, String preferencesValue) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("portletId", checkNull(portletId));
			_params.put("preferencesKey", checkNull(preferencesKey));
			_params.put("preferencesValue", checkNull(preferencesValue));

			_command.put("/layout/get-layout-references", _params);
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

	public JSONArray getTempFileNames(long groupId, String folderName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("folderName", checkNull(folderName));

			_command.put("/layout/get-temp-file-names", _params);
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

	public JSONObject exportLayoutsAsFile(long groupId, boolean privateLayout, JSONArray layoutIds, JSONObject parameterMap, long startDate, long endDate) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutIds", checkNull(layoutIds));
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);

			_command.put("/layout/export-layouts-as-file", _params);
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

	public JSONObject exportLayoutsAsFile(JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/layout/export-layouts-as-file", _params);
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

	public void importLayouts(long groupId, boolean privateLayout, JSONObject parameterMap, byte[] bytes) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("bytes", toString(bytes));

			_command.put("/layout/import-layouts", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void importLayouts(long groupId, boolean privateLayout, JSONObject parameterMap, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("parameterMap", checkNull(parameterMap));
			_params.put("file", checkNull(file));

			_command.put("/layout/import-layouts", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public void importLayouts(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/layout/import-layouts", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public JSONObject updateLookAndFeel(long groupId, boolean privateLayout, long layoutId, String themeId, String colorSchemeId, String css, boolean wapTheme) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("privateLayout", privateLayout);
			_params.put("layoutId", layoutId);
			_params.put("themeId", checkNull(themeId));
			_params.put("colorSchemeId", checkNull(colorSchemeId));
			_params.put("css", checkNull(css));
			_params.put("wapTheme", wapTheme);

			_command.put("/layout/update-look-and-feel", _params);
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