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

package com.liferay.mobile.android.v70.calendar;

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
public class CalendarService extends BaseService {

	public CalendarService(Session session) {
		super(session);
	}

	public JSONArray search(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String keywords, boolean andOperator, int start, int end, JSONObjectWrapper orderByComparator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("keywords", checkNull(keywords));
			_params.put("andOperator", andOperator);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar>", orderByComparator);

			_command.put("/calendar.calendar/search", _params);
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

	public JSONArray search(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String keywords, boolean andOperator, int start, int end, JSONObjectWrapper orderByComparator, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("keywords", checkNull(keywords));
			_params.put("andOperator", andOperator);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar>", orderByComparator);
			_params.put("actionId", checkNull(actionId));

			_command.put("/calendar.calendar/search", _params);
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

	public JSONArray search(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String name, String description, boolean andOperator, int start, int end, JSONObjectWrapper orderByComparator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("andOperator", andOperator);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar>", orderByComparator);

			_command.put("/calendar.calendar/search", _params);
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

	public JSONArray search(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String name, String description, boolean andOperator, int start, int end, JSONObjectWrapper orderByComparator, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("andOperator", andOperator);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.Calendar>", orderByComparator);
			_params.put("actionId", checkNull(actionId));

			_command.put("/calendar.calendar/search", _params);
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

	public JSONObject getCalendar(long calendarId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);

			_command.put("/calendar.calendar/get-calendar", _params);
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

	public Integer searchCount(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String keywords, boolean andOperator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("keywords", checkNull(keywords));
			_params.put("andOperator", andOperator);

			_command.put("/calendar.calendar/search-count", _params);
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

	public Integer searchCount(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String keywords, boolean andOperator, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("keywords", checkNull(keywords));
			_params.put("andOperator", andOperator);
			_params.put("actionId", checkNull(actionId));

			_command.put("/calendar.calendar/search-count", _params);
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

	public Integer searchCount(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String name, String description, boolean andOperator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("andOperator", andOperator);

			_command.put("/calendar.calendar/search-count", _params);
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

	public Integer searchCount(long companyId, JSONArray groupIds, JSONArray calendarResourceIds, String name, String description, boolean andOperator, String actionId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("andOperator", andOperator);
			_params.put("actionId", checkNull(actionId));

			_command.put("/calendar.calendar/search-count", _params);
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

	public JSONObject addCalendar(long groupId, long calendarResourceId, JSONObject nameMap, JSONObject descriptionMap, String timeZoneId, int color, boolean defaultCalendar, boolean enableComments, boolean enableRatings, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("calendarResourceId", calendarResourceId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("timeZoneId", checkNull(timeZoneId));
			_params.put("color", color);
			_params.put("defaultCalendar", defaultCalendar);
			_params.put("enableComments", enableComments);
			_params.put("enableRatings", enableRatings);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendar/add-calendar", _params);
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

	public JSONObject updateCalendar(long calendarId, JSONObject nameMap, JSONObject descriptionMap, String timeZoneId, int color, boolean defaultCalendar, boolean enableComments, boolean enableRatings, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("timeZoneId", checkNull(timeZoneId));
			_params.put("color", color);
			_params.put("defaultCalendar", defaultCalendar);
			_params.put("enableComments", enableComments);
			_params.put("enableRatings", enableRatings);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendar/update-calendar", _params);
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

	public JSONObject updateCalendar(long calendarId, JSONObject nameMap, JSONObject descriptionMap, int color, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("nameMap", checkNull(nameMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("color", color);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendar/update-calendar", _params);
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

	public JSONArray getCalendarResourceCalendars(long groupId, long calendarResourceId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("calendarResourceId", calendarResourceId);

			_command.put("/calendar.calendar/get-calendar-resource-calendars", _params);
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

	public JSONArray getCalendarResourceCalendars(long groupId, long calendarResourceId, boolean defaultCalendar) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("calendarResourceId", calendarResourceId);
			_params.put("defaultCalendar", defaultCalendar);

			_command.put("/calendar.calendar/get-calendar-resource-calendars", _params);
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

	public JSONObject deleteCalendar(long calendarId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);

			_command.put("/calendar.calendar/delete-calendar", _params);
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

	public String exportCalendar(long calendarId, String type) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("type", checkNull(type));

			_command.put("/calendar.calendar/export-calendar", _params);
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

	public JSONObject fetchCalendar(long calendarId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);

			_command.put("/calendar.calendar/fetch-calendar", _params);
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

	public void importCalendar(long calendarId, String data, String type) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("data", checkNull(data));
			_params.put("type", checkNull(type));

			_command.put("/calendar.calendar/import-calendar", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateColor(long calendarId, int color, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("color", color);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendar/update-color", _params);
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

	public Boolean isManageableFromGroup(long calendarId, long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("groupId", groupId);

			_command.put("/calendar.calendar/is-manageable-from-group", _params);
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

}