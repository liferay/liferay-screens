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

package com.liferay.mobile.android.v70.mbmessage;

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
public class MBMessageService extends BaseService {

	public MBMessageService(Session session) {
		super(session);
	}

	public JSONObject getMessage(long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);

			_command.put("/mbmessage/get-message", _params);
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

	public JSONArray getThreadMessages(long groupId, long categoryId, long threadId, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("threadId", threadId);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbmessage/get-thread-messages", _params);
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

	public JSONObject getMessageDisplay(long messageId, int status, boolean includePrevAndNext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);
			_params.put("status", status);
			_params.put("includePrevAndNext", includePrevAndNext);

			_command.put("/mbmessage/get-message-display", _params);
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

	public JSONObject getMessageDisplay(long messageId, int status, String threadView, boolean includePrevAndNext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);
			_params.put("status", status);
			_params.put("threadView", checkNull(threadView));
			_params.put("includePrevAndNext", includePrevAndNext);

			_command.put("/mbmessage/get-message-display", _params);
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

	public void addMessageAttachment(long messageId, String fileName, UploadData file, String mimeType) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);
			_params.put("fileName", checkNull(fileName));
			_params.put("file", checkNull(file));
			_params.put("mimeType", checkNull(mimeType));

			_command.put("/mbmessage/add-message-attachment", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public JSONObject addMessage(long categoryId, String subject, String body, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("categoryId", categoryId);
			_params.put("subject", checkNull(subject));
			_params.put("body", checkNull(body));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/mbmessage/add-message", _params);
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

	public JSONObject addMessage(long groupId, long categoryId, String subject, String body, String format, String fileName, UploadData file, boolean anonymous, double priority, boolean allowPingbacks, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("subject", checkNull(subject));
			_params.put("body", checkNull(body));
			_params.put("format", checkNull(format));
			_params.put("fileName", checkNull(fileName));
			_params.put("file", checkNull(file));
			_params.put("anonymous", anonymous);
			_params.put("priority", priority);
			_params.put("allowPingbacks", allowPingbacks);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/mbmessage/add-message", _params);
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

	public void deleteDiscussionMessage(long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);

			_command.put("/mbmessage/delete-discussion-message", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteDiscussionMessage(long groupId, String className, long classPK, String permissionClassName, long permissionClassPK, long permissionOwnerId, long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("permissionClassName", checkNull(permissionClassName));
			_params.put("permissionClassPK", permissionClassPK);
			_params.put("permissionOwnerId", permissionOwnerId);
			_params.put("messageId", messageId);

			_command.put("/mbmessage/delete-discussion-message", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteMessage(long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);

			_command.put("/mbmessage/delete-message", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteMessageAttachment(long messageId, String fileName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);
			_params.put("fileName", checkNull(fileName));

			_command.put("/mbmessage/delete-message-attachment", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteMessageAttachments(long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);

			_command.put("/mbmessage/delete-message-attachments", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void emptyMessageAttachments(long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);

			_command.put("/mbmessage/empty-message-attachments", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONArray getCategoryMessages(long groupId, long categoryId, int status, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("status", status);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/mbmessage/get-category-messages", _params);
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

	public Integer getGroupMessagesCount(long groupId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("status", status);

			_command.put("/mbmessage/get-group-messages-count", _params);
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

	public Integer getThreadMessagesCount(long groupId, long categoryId, long threadId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("threadId", threadId);
			_params.put("status", status);

			_command.put("/mbmessage/get-thread-messages-count", _params);
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

	public void restoreMessageAttachmentFromTrash(long messageId, String fileName) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);
			_params.put("fileName", checkNull(fileName));

			_command.put("/mbmessage/restore-message-attachment-from-trash", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void subscribeMessage(long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);

			_command.put("/mbmessage/subscribe-message", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void unsubscribeMessage(long messageId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);

			_command.put("/mbmessage/unsubscribe-message", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void updateAnswer(long messageId, boolean answer, boolean cascade) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("messageId", messageId);
			_params.put("answer", answer);
			_params.put("cascade", cascade);

			_command.put("/mbmessage/update-answer", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateDiscussionMessage(String className, long classPK, long messageId, String subject, String body, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("messageId", messageId);
			_params.put("subject", checkNull(subject));
			_params.put("body", checkNull(body));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/mbmessage/update-discussion-message", _params);
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

	public String getCategoryMessagesRss(long groupId, long categoryId, int status, int max, String type, double version, String displayStyle, String feedURL, String entryURL, JSONObjectWrapper themeDisplay) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("status", status);
			_params.put("max", max);
			_params.put("type", checkNull(type));
			_params.put("version", version);
			_params.put("displayStyle", checkNull(displayStyle));
			_params.put("feedURL", checkNull(feedURL));
			_params.put("entryURL", checkNull(entryURL));
			mangleWrapper(_params, "themeDisplay", "com.liferay.portal.theme.ThemeDisplay", themeDisplay);

			_command.put("/mbmessage/get-category-messages-rss", _params);
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

	public String getCompanyMessagesRss(long companyId, int status, int max, String type, double version, String displayStyle, String feedURL, String entryURL, JSONObjectWrapper themeDisplay) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("status", status);
			_params.put("max", max);
			_params.put("type", checkNull(type));
			_params.put("version", version);
			_params.put("displayStyle", checkNull(displayStyle));
			_params.put("feedURL", checkNull(feedURL));
			_params.put("entryURL", checkNull(entryURL));
			mangleWrapper(_params, "themeDisplay", "com.liferay.portal.theme.ThemeDisplay", themeDisplay);

			_command.put("/mbmessage/get-company-messages-rss", _params);
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

	public String getGroupMessagesRss(long groupId, long userId, int status, int max, String type, double version, String displayStyle, String feedURL, String entryURL, JSONObjectWrapper themeDisplay) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("userId", userId);
			_params.put("status", status);
			_params.put("max", max);
			_params.put("type", checkNull(type));
			_params.put("version", version);
			_params.put("displayStyle", checkNull(displayStyle));
			_params.put("feedURL", checkNull(feedURL));
			_params.put("entryURL", checkNull(entryURL));
			mangleWrapper(_params, "themeDisplay", "com.liferay.portal.theme.ThemeDisplay", themeDisplay);

			_command.put("/mbmessage/get-group-messages-rss", _params);
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

	public String getGroupMessagesRss(long groupId, int status, int max, String type, double version, String displayStyle, String feedURL, String entryURL, JSONObjectWrapper themeDisplay) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("status", status);
			_params.put("max", max);
			_params.put("type", checkNull(type));
			_params.put("version", version);
			_params.put("displayStyle", checkNull(displayStyle));
			_params.put("feedURL", checkNull(feedURL));
			_params.put("entryURL", checkNull(entryURL));
			mangleWrapper(_params, "themeDisplay", "com.liferay.portal.theme.ThemeDisplay", themeDisplay);

			_command.put("/mbmessage/get-group-messages-rss", _params);
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

	public Integer getThreadAnswersCount(long groupId, long categoryId, long threadId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("threadId", threadId);

			_command.put("/mbmessage/get-thread-answers-count", _params);
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

	public String getThreadMessagesRss(long threadId, int status, int max, String type, double version, String displayStyle, String feedURL, String entryURL, JSONObjectWrapper themeDisplay) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("threadId", threadId);
			_params.put("status", status);
			_params.put("max", max);
			_params.put("type", checkNull(type));
			_params.put("version", version);
			_params.put("displayStyle", checkNull(displayStyle));
			_params.put("feedURL", checkNull(feedURL));
			_params.put("entryURL", checkNull(entryURL));
			mangleWrapper(_params, "themeDisplay", "com.liferay.portal.theme.ThemeDisplay", themeDisplay);

			_command.put("/mbmessage/get-thread-messages-rss", _params);
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

	public JSONObject addDiscussionMessage(long groupId, String className, long classPK, long threadId, long parentMessageId, String subject, String body, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("threadId", threadId);
			_params.put("parentMessageId", parentMessageId);
			_params.put("subject", checkNull(subject));
			_params.put("body", checkNull(body));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/mbmessage/add-discussion-message", _params);
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

	public Integer getCategoryMessagesCount(long groupId, long categoryId, int status) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("status", status);

			_command.put("/mbmessage/get-category-messages-count", _params);
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

}