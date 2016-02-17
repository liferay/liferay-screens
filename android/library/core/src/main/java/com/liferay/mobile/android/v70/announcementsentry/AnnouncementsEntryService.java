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

package com.liferay.mobile.android.v70.announcementsentry;

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
public class AnnouncementsEntryService extends BaseService {

	public AnnouncementsEntryService(Session session) {
		super(session);
	}

	public JSONObject addEntry(long plid, long classNameId, long classPK, String title, String content, String url, String type, int displayDateMonth, int displayDateDay, int displayDateYear, int displayDateHour, int displayDateMinute, boolean displayImmediately, int expirationDateMonth, int expirationDateDay, int expirationDateYear, int expirationDateHour, int expirationDateMinute, int priority, boolean alert) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("plid", plid);
			_params.put("classNameId", classNameId);
			_params.put("classPK", classPK);
			_params.put("title", checkNull(title));
			_params.put("content", checkNull(content));
			_params.put("url", checkNull(url));
			_params.put("type", checkNull(type));
			_params.put("displayDateMonth", displayDateMonth);
			_params.put("displayDateDay", displayDateDay);
			_params.put("displayDateYear", displayDateYear);
			_params.put("displayDateHour", displayDateHour);
			_params.put("displayDateMinute", displayDateMinute);
			_params.put("displayImmediately", displayImmediately);
			_params.put("expirationDateMonth", expirationDateMonth);
			_params.put("expirationDateDay", expirationDateDay);
			_params.put("expirationDateYear", expirationDateYear);
			_params.put("expirationDateHour", expirationDateHour);
			_params.put("expirationDateMinute", expirationDateMinute);
			_params.put("priority", priority);
			_params.put("alert", alert);

			_command.put("/announcementsentry/add-entry", _params);
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

	public JSONObject getEntry(long entryId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("entryId", entryId);

			_command.put("/announcementsentry/get-entry", _params);
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

	public void deleteEntry(long entryId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("entryId", entryId);

			_command.put("/announcementsentry/delete-entry", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateEntry(long entryId, String title, String content, String url, String type, int displayDateMonth, int displayDateDay, int displayDateYear, int displayDateHour, int displayDateMinute, boolean displayImmediately, int expirationDateMonth, int expirationDateDay, int expirationDateYear, int expirationDateHour, int expirationDateMinute, int priority) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("entryId", entryId);
			_params.put("title", checkNull(title));
			_params.put("content", checkNull(content));
			_params.put("url", checkNull(url));
			_params.put("type", checkNull(type));
			_params.put("displayDateMonth", displayDateMonth);
			_params.put("displayDateDay", displayDateDay);
			_params.put("displayDateYear", displayDateYear);
			_params.put("displayDateHour", displayDateHour);
			_params.put("displayDateMinute", displayDateMinute);
			_params.put("displayImmediately", displayImmediately);
			_params.put("expirationDateMonth", expirationDateMonth);
			_params.put("expirationDateDay", expirationDateDay);
			_params.put("expirationDateYear", expirationDateYear);
			_params.put("expirationDateHour", expirationDateHour);
			_params.put("expirationDateMinute", expirationDateMinute);
			_params.put("priority", priority);

			_command.put("/announcementsentry/update-entry", _params);
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