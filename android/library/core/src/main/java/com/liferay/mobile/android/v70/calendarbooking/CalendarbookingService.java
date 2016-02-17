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

package com.liferay.mobile.android.v70.calendarbooking;

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
public class CalendarbookingService extends BaseService {

	public CalendarbookingService(Session session) {
		super(session);
	}

	public JSONArray search(long companyId, JSONArray groupIds, JSONArray calendarIds, JSONArray calendarResourceIds, long parentCalendarBookingId, String keywords, long startTime, long endTime, boolean recurring, JSONArray statuses, int start, int end, JSONObjectWrapper orderByComparator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarIds", checkNull(calendarIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("parentCalendarBookingId", parentCalendarBookingId);
			_params.put("keywords", checkNull(keywords));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("recurring", recurring);
			_params.put("statuses", checkNull(statuses));
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.CalendarBooking>", orderByComparator);

			_command.put("/calendar.calendarbooking/search", _params);
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

	public JSONArray search(long companyId, JSONArray groupIds, JSONArray calendarIds, JSONArray calendarResourceIds, long parentCalendarBookingId, String title, String description, String location, long startTime, long endTime, boolean recurring, JSONArray statuses, boolean andOperator, int start, int end, JSONObjectWrapper orderByComparator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarIds", checkNull(calendarIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("parentCalendarBookingId", parentCalendarBookingId);
			_params.put("title", checkNull(title));
			_params.put("description", checkNull(description));
			_params.put("location", checkNull(location));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("recurring", recurring);
			_params.put("statuses", checkNull(statuses));
			_params.put("andOperator", andOperator);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.calendar.model.CalendarBooking>", orderByComparator);

			_command.put("/calendar.calendarbooking/search", _params);
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

	public Integer searchCount(long companyId, JSONArray groupIds, JSONArray calendarIds, JSONArray calendarResourceIds, long parentCalendarBookingId, String keywords, long startTime, long endTime, boolean recurring, JSONArray statuses) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarIds", checkNull(calendarIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("parentCalendarBookingId", parentCalendarBookingId);
			_params.put("keywords", checkNull(keywords));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("recurring", recurring);
			_params.put("statuses", checkNull(statuses));

			_command.put("/calendar.calendarbooking/search-count", _params);
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

	public Integer searchCount(long companyId, JSONArray groupIds, JSONArray calendarIds, JSONArray calendarResourceIds, long parentCalendarBookingId, String title, String description, String location, long startTime, long endTime, boolean recurring, JSONArray statuses, boolean andOperator) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("groupIds", checkNull(groupIds));
			_params.put("calendarIds", checkNull(calendarIds));
			_params.put("calendarResourceIds", checkNull(calendarResourceIds));
			_params.put("parentCalendarBookingId", parentCalendarBookingId);
			_params.put("title", checkNull(title));
			_params.put("description", checkNull(description));
			_params.put("location", checkNull(location));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("recurring", recurring);
			_params.put("statuses", checkNull(statuses));
			_params.put("andOperator", andOperator);

			_command.put("/calendar.calendarbooking/search-count", _params);
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

	public JSONArray getChildCalendarBookings(long parentCalendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentCalendarBookingId", parentCalendarBookingId);

			_command.put("/calendar.calendarbooking/get-child-calendar-bookings", _params);
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

	public JSONArray getChildCalendarBookings(long parentCalendarBookingId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentCalendarBookingId", parentCalendarBookingId);
			_params.put("status", status);

			_command.put("/calendar.calendarbooking/get-child-calendar-bookings", _params);
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

	public JSONObject getCalendarBooking(long calendarId, long parentCalendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("parentCalendarBookingId", parentCalendarBookingId);

			_command.put("/calendar.calendarbooking/get-calendar-booking", _params);
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

	public JSONObject getCalendarBooking(long calendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);

			_command.put("/calendar.calendarbooking/get-calendar-booking", _params);
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

	public JSONObject addCalendarBooking(long calendarId, JSONArray childCalendarIds, long parentCalendarBookingId, JSONObject titleMap, JSONObject descriptionMap, String location, int startTimeYear, int startTimeMonth, int startTimeDay, int startTimeHour, int startTimeMinute, int endTimeYear, int endTimeMonth, int endTimeDay, int endTimeHour, int endTimeMinute, String timeZoneId, boolean allDay, String recurrence, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("childCalendarIds", checkNull(childCalendarIds));
			_params.put("parentCalendarBookingId", parentCalendarBookingId);
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("startTimeYear", startTimeYear);
			_params.put("startTimeMonth", startTimeMonth);
			_params.put("startTimeDay", startTimeDay);
			_params.put("startTimeHour", startTimeHour);
			_params.put("startTimeMinute", startTimeMinute);
			_params.put("endTimeYear", endTimeYear);
			_params.put("endTimeMonth", endTimeMonth);
			_params.put("endTimeDay", endTimeDay);
			_params.put("endTimeHour", endTimeHour);
			_params.put("endTimeMinute", endTimeMinute);
			_params.put("timeZoneId", checkNull(timeZoneId));
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/add-calendar-booking", _params);
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

	public JSONObject addCalendarBooking(long calendarId, JSONArray childCalendarIds, long parentCalendarBookingId, JSONObject titleMap, JSONObject descriptionMap, String location, long startTime, long endTime, boolean allDay, String recurrence, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("childCalendarIds", checkNull(childCalendarIds));
			_params.put("parentCalendarBookingId", parentCalendarBookingId);
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/add-calendar-booking", _params);
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

	public JSONObject updateCalendarBooking(long calendarBookingId, long calendarId, JSONArray childCalendarIds, JSONObject titleMap, JSONObject descriptionMap, String location, long startTime, long endTime, boolean allDay, String recurrence, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("calendarId", calendarId);
			_params.put("childCalendarIds", checkNull(childCalendarIds));
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/update-calendar-booking", _params);
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

	public JSONObject updateCalendarBooking(long calendarBookingId, long calendarId, JSONObject titleMap, JSONObject descriptionMap, String location, long startTime, long endTime, boolean allDay, String recurrence, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("calendarId", calendarId);
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/update-calendar-booking", _params);
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

	public JSONObject deleteCalendarBooking(long calendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);

			_command.put("/calendar.calendarbooking/delete-calendar-booking", _params);
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

	public void deleteCalendarBookingInstance(long calendarBookingId, int instanceIndex, boolean allFollowing) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("instanceIndex", instanceIndex);
			_params.put("allFollowing", allFollowing);

			_command.put("/calendar.calendarbooking/delete-calendar-booking-instance", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteCalendarBookingInstance(long calendarBookingId, long startTime, boolean allFollowing) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("startTime", startTime);
			_params.put("allFollowing", allFollowing);

			_command.put("/calendar.calendarbooking/delete-calendar-booking-instance", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public String exportCalendarBooking(long calendarBookingId, String type) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("type", checkNull(type));

			_command.put("/calendar.calendarbooking/export-calendar-booking", _params);
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

	public JSONObject fetchCalendarBooking(long calendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);

			_command.put("/calendar.calendarbooking/fetch-calendar-booking", _params);
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

	public JSONObject getCalendarBookingInstance(long calendarBookingId, int instanceIndex) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("instanceIndex", instanceIndex);

			_command.put("/calendar.calendarbooking/get-calendar-booking-instance", _params);
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

	public JSONArray getCalendarBookings(long calendarId, long startTime, long endTime) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);

			_command.put("/calendar.calendarbooking/get-calendar-bookings", _params);
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

	public JSONArray getCalendarBookings(long calendarId, JSONArray statuses) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("statuses", checkNull(statuses));

			_command.put("/calendar.calendarbooking/get-calendar-bookings", _params);
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

	public JSONArray getCalendarBookings(long calendarId, long startTime, long endTime, int max) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("max", max);

			_command.put("/calendar.calendarbooking/get-calendar-bookings", _params);
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

	public JSONObject moveCalendarBookingToTrash(long calendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);

			_command.put("/calendar.calendarbooking/move-calendar-booking-to-trash", _params);
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

	public JSONObject restoreCalendarBookingFromTrash(long calendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);

			_command.put("/calendar.calendarbooking/restore-calendar-booking-from-trash", _params);
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

	public JSONObject updateCalendarBookingInstance(long calendarBookingId, int instanceIndex, long calendarId, JSONArray childCalendarIds, JSONObject titleMap, JSONObject descriptionMap, String location, long startTime, long endTime, boolean allDay, String recurrence, boolean allFollowing, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("instanceIndex", instanceIndex);
			_params.put("calendarId", calendarId);
			_params.put("childCalendarIds", checkNull(childCalendarIds));
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("allFollowing", allFollowing);
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/update-calendar-booking-instance", _params);
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

	public JSONObject updateCalendarBookingInstance(long calendarBookingId, int instanceIndex, long calendarId, JSONObject titleMap, JSONObject descriptionMap, String location, int startTimeYear, int startTimeMonth, int startTimeDay, int startTimeHour, int startTimeMinute, int endTimeYear, int endTimeMonth, int endTimeDay, int endTimeHour, int endTimeMinute, String timeZoneId, boolean allDay, String recurrence, boolean allFollowing, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("instanceIndex", instanceIndex);
			_params.put("calendarId", calendarId);
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("startTimeYear", startTimeYear);
			_params.put("startTimeMonth", startTimeMonth);
			_params.put("startTimeDay", startTimeDay);
			_params.put("startTimeHour", startTimeHour);
			_params.put("startTimeMinute", startTimeMinute);
			_params.put("endTimeYear", endTimeYear);
			_params.put("endTimeMonth", endTimeMonth);
			_params.put("endTimeDay", endTimeDay);
			_params.put("endTimeHour", endTimeHour);
			_params.put("endTimeMinute", endTimeMinute);
			_params.put("timeZoneId", checkNull(timeZoneId));
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("allFollowing", allFollowing);
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/update-calendar-booking-instance", _params);
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

	public JSONObject updateCalendarBookingInstance(long calendarBookingId, int instanceIndex, long calendarId, JSONObject titleMap, JSONObject descriptionMap, String location, long startTime, long endTime, boolean allDay, String recurrence, boolean allFollowing, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("instanceIndex", instanceIndex);
			_params.put("calendarId", calendarId);
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("allFollowing", allFollowing);
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/update-calendar-booking-instance", _params);
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

	public String getCalendarBookingsRss(long calendarId, long startTime, long endTime, int max, String type, double version, String displayStyle, JSONObjectWrapper themeDisplay) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarId", calendarId);
			_params.put("startTime", startTime);
			_params.put("endTime", endTime);
			_params.put("max", max);
			_params.put("type", checkNull(type));
			_params.put("version", version);
			_params.put("displayStyle", checkNull(displayStyle));
			mangleWrapper(_params, "themeDisplay", "com.liferay.portal.theme.ThemeDisplay", themeDisplay);

			_command.put("/calendar.calendarbooking/get-calendar-bookings-rss", _params);
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

	public JSONObject getNewStartTimeAndDurationCalendarBooking(long calendarBookingId, long offset, long duration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("offset", offset);
			_params.put("duration", duration);

			_command.put("/calendar.calendarbooking/get-new-start-time-and-duration-calendar-booking", _params);
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

	public Boolean hasChildCalendarBookings(long parentCalendarBookingId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("parentCalendarBookingId", parentCalendarBookingId);

			_command.put("/calendar.calendarbooking/has-child-calendar-bookings", _params);
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

	public void invokeTransition(long calendarBookingId, int status, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("status", status);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/invoke-transition", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateOffsetAndDuration(long calendarBookingId, long calendarId, JSONObject titleMap, JSONObject descriptionMap, String location, long offset, long duration, boolean allDay, String recurrence, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("calendarId", calendarId);
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("offset", offset);
			_params.put("duration", duration);
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/update-offset-and-duration", _params);
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

	public JSONObject updateOffsetAndDuration(long calendarBookingId, long calendarId, JSONArray childCalendarIds, JSONObject titleMap, JSONObject descriptionMap, String location, long offset, long duration, boolean allDay, String recurrence, long firstReminder, String firstReminderType, long secondReminder, String secondReminderType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("calendarBookingId", calendarBookingId);
			_params.put("calendarId", calendarId);
			_params.put("childCalendarIds", checkNull(childCalendarIds));
			_params.put("titleMap", checkNull(titleMap));
			_params.put("descriptionMap", checkNull(descriptionMap));
			_params.put("location", checkNull(location));
			_params.put("offset", offset);
			_params.put("duration", duration);
			_params.put("allDay", allDay);
			_params.put("recurrence", checkNull(recurrence));
			_params.put("firstReminder", firstReminder);
			_params.put("firstReminderType", checkNull(firstReminderType));
			_params.put("secondReminder", secondReminder);
			_params.put("secondReminderType", checkNull(secondReminderType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/calendar.calendarbooking/update-offset-and-duration", _params);
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