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

package com.liferay.mobile.android.v70.wikinode;

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
public class WikiNodeService extends BaseService {

	public WikiNodeService(Session session) {
		super(session);
	}

	public JSONObject getNode(long nodeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nodeId", nodeId);

			_command.put("/wiki.wikinode/get-node", _params);
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

	public JSONObject getNode(long groupId, String name) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("name", checkNull(name));

			_command.put("/wiki.wikinode/get-node", _params);
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

	public JSONObject addNode(String name, String description, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/wiki.wikinode/add-node", _params);
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

	public JSONObject updateNode(long nodeId, String name, String description, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nodeId", nodeId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/wiki.wikinode/update-node", _params);
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

	public JSONArray getNodes(long groupId, int status, int start, int end, JSONObjectWrapper obc) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "obc", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.wiki.model.WikiNode>", obc);

			_command.put("/wiki.wikinode/get-nodes", _params);
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

	public JSONArray getNodes(long groupId, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/wiki.wikinode/get-nodes", _params);
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

	public JSONArray getNodes(long groupId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("status", status);

			_command.put("/wiki.wikinode/get-nodes", _params);
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

	public JSONArray getNodes(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/wiki.wikinode/get-nodes", _params);
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

	public JSONArray getNodes(long groupId, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/wiki.wikinode/get-nodes", _params);
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

	public void deleteNode(long nodeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nodeId", nodeId);

			_command.put("/wiki.wikinode/delete-node", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public Integer getNodesCount(long groupId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("status", status);

			_command.put("/wiki.wikinode/get-nodes-count", _params);
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

	public Integer getNodesCount(long groupId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);

			_command.put("/wiki.wikinode/get-nodes-count", _params);
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

	public JSONObject moveNodeToTrash(long nodeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nodeId", nodeId);

			_command.put("/wiki.wikinode/move-node-to-trash", _params);
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

	public void restoreNodeFromTrash(long nodeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nodeId", nodeId);

			_command.put("/wiki.wikinode/restore-node-from-trash", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void subscribeNode(long nodeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nodeId", nodeId);

			_command.put("/wiki.wikinode/subscribe-node", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsubscribeNode(long nodeId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("nodeId", nodeId);

			_command.put("/wiki.wikinode/unsubscribe-node", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}