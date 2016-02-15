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

package com.liferay.mobile.android.v70.journalfeed;

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
public class JournalFeedService extends BaseService {

	public JournalFeedService(Session session) {
		super(session);
	}

	public JSONObject getFeed(long feedId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("feedId", feedId);

			_command.put("/journal.journalfeed/get-feed", _params);
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

	public JSONObject getFeed(long groupId, String feedId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("feedId", checkNull(feedId));

			_command.put("/journal.journalfeed/get-feed", _params);
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

	public void deleteFeed(long groupId, String feedId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("feedId", checkNull(feedId));

			_command.put("/journal.journalfeed/delete-feed", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteFeed(long feedId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("feedId", feedId);

			_command.put("/journal.journalfeed/delete-feed", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateFeed(long groupId, String feedId, String name, String description, String ddmStructureKey, String ddmTemplateKey, String ddmRendererTemplateKey, int delta, String orderByCol, String orderByType, String targetLayoutFriendlyUrl, String targetPortletId, String contentField, String feedType, double feedVersion, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("feedId", checkNull(feedId));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("ddmStructureKey", checkNull(ddmStructureKey));
			_params.put("ddmTemplateKey", checkNull(ddmTemplateKey));
			_params.put("ddmRendererTemplateKey", checkNull(ddmRendererTemplateKey));
			_params.put("delta", delta);
			_params.put("orderByCol", checkNull(orderByCol));
			_params.put("orderByType", checkNull(orderByType));
			_params.put("targetLayoutFriendlyUrl", checkNull(targetLayoutFriendlyUrl));
			_params.put("targetPortletId", checkNull(targetPortletId));
			_params.put("contentField", checkNull(contentField));
			_params.put("feedType", checkNull(feedType));
			_params.put("feedVersion", feedVersion);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/journal.journalfeed/update-feed", _params);
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

	public JSONObject addFeed(long groupId, String feedId, boolean autoFeedId, String name, String description, String ddmStructureKey, String ddmTemplateKey, String ddmRendererTemplateKey, int delta, String orderByCol, String orderByType, String targetLayoutFriendlyUrl, String targetPortletId, String contentField, String feedType, double feedVersion, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("feedId", checkNull(feedId));
			_params.put("autoFeedId", autoFeedId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("ddmStructureKey", checkNull(ddmStructureKey));
			_params.put("ddmTemplateKey", checkNull(ddmTemplateKey));
			_params.put("ddmRendererTemplateKey", checkNull(ddmRendererTemplateKey));
			_params.put("delta", delta);
			_params.put("orderByCol", checkNull(orderByCol));
			_params.put("orderByType", checkNull(orderByType));
			_params.put("targetLayoutFriendlyUrl", checkNull(targetLayoutFriendlyUrl));
			_params.put("targetPortletId", checkNull(targetPortletId));
			_params.put("contentField", checkNull(contentField));
			_params.put("feedType", checkNull(feedType));
			_params.put("feedVersion", feedVersion);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/journal.journalfeed/add-feed", _params);
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