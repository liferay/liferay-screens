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

package com.liferay.mobile.android.v70.mbthread;

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
public class MBThreadService extends BaseService {

	public MBThreadService(Session session) {
		super(session);
	}

	public JSONArray getThreads(long groupId, long categoryId, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbthread/get-threads", _params);
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

	public JSONObject search(long groupId, long creatorUserId, long startDate, long endDate, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("creatorUserId", creatorUserId);
			_params.put("startDate", startDate);
			_params.put("endDate", endDate);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbthread/search", _params);
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

	public JSONObject search(long groupId, long creatorUserId, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("creatorUserId", creatorUserId);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbthread/search", _params);
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

	public void deleteThread(long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("threadId", threadId);

			_command.put("/mbthread/delete-thread", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public Integer getGroupThreadsCount(long groupId, long userId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("status", status);

			_command.put("/mbthread/get-group-threads-count", _params);
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

	public Integer getGroupThreadsCount(long groupId, long userId, long modifiedDate, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("modifiedDate", modifiedDate);
			_params.put("status", status);

			_command.put("/mbthread/get-group-threads-count", _params);
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

	public Integer getGroupThreadsCount(long groupId, long userId, int status, boolean subscribed, boolean includeAnonymous) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("status", status);
			_params.put("subscribed", subscribed);
			_params.put("includeAnonymous", includeAnonymous);

			_command.put("/mbthread/get-group-threads-count", _params);
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

	public Integer getGroupThreadsCount(long groupId, long userId, int status, boolean subscribed) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("status", status);
			_params.put("subscribed", subscribed);

			_command.put("/mbthread/get-group-threads-count", _params);
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

	public JSONArray getGroupThreads(long groupId, long userId, int status, boolean subscribed, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("status", status);
			_params.put("subscribed", subscribed);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbthread/get-group-threads", _params);
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

	public JSONArray getGroupThreads(long groupId, long userId, long modifiedDate, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("modifiedDate", modifiedDate);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbthread/get-group-threads", _params);
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

	public JSONArray getGroupThreads(long groupId, long userId, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbthread/get-group-threads", _params);
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

	public JSONArray getGroupThreads(long groupId, long userId, int status, boolean subscribed, boolean includeAnonymous, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("status", status);
			_params.put("subscribed", subscribed);
			_params.put("includeAnonymous", includeAnonymous);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbthread/get-group-threads", _params);
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

	public Integer getThreadsCount(long groupId, long categoryId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("status", status);

			_command.put("/mbthread/get-threads-count", _params);
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

	public JSONObject moveThread(long categoryId, long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("categoryId", categoryId);
			_params.put("threadId", threadId);

			_command.put("/mbthread/move-thread", _params);
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

	public JSONObject moveThreadFromTrash(long categoryId, long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("categoryId", categoryId);
			_params.put("threadId", threadId);

			_command.put("/mbthread/move-thread-from-trash", _params);
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

	public JSONObject moveThreadToTrash(long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("threadId", threadId);

			_command.put("/mbthread/move-thread-to-trash", _params);
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

	public void restoreThreadFromTrash(long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("threadId", threadId);

			_command.put("/mbthread/restore-thread-from-trash", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject splitThread(long messageId, String subject, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);
			_params.put("subject", checkNull(subject));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/mbthread/split-thread", _params);
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

	public JSONObject lockThread(long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("threadId", threadId);

			_command.put("/mbthread/lock-thread", _params);
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

	public void unlockThread(long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("threadId", threadId);

			_command.put("/mbthread/unlock-thread", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}