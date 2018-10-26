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

package com.liferay.mobile.screens.service.v70;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class CommentmanagerjsonwsService extends BaseService {

    public CommentmanagerjsonwsService(Session session) {
        super(session);
    }

    public Long addComment(long groupId, String className, long classPK, String body) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("className", checkNull(className));
            _params.put("classPK", classPK);
            _params.put("body", checkNull(body));

            _command.put("/comment.commentmanagerjsonws/add-comment", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getLong(0);
    }

    public void deleteComment(long commentId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("commentId", commentId);

            _command.put("/comment.commentmanagerjsonws/delete-comment", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public JSONArray getComments(long commentId, int start, int end) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("commentId", commentId);
            _params.put("start", start);
            _params.put("end", end);

            _command.put("/comment.commentmanagerjsonws/get-comments", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public JSONArray getComments(long groupId, String className, long classPK, int start, int end) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("className", checkNull(className));
            _params.put("classPK", classPK);
            _params.put("start", start);
            _params.put("end", end);

            _command.put("/comment.commentmanagerjsonws/get-comments", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public Integer getCommentsCount(long groupId, String className, long classPK) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("className", checkNull(className));
            _params.put("classPK", classPK);

            _command.put("/comment.commentmanagerjsonws/get-comments-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Boolean hasDiscussion(long groupId, String className, long classPK) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("className", checkNull(className));
            _params.put("classPK", classPK);

            _command.put("/comment.commentmanagerjsonws/has-discussion", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public void subscribeDiscussion(long groupId, String className, long classPK) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("className", checkNull(className));
            _params.put("classPK", classPK);

            _command.put("/comment.commentmanagerjsonws/subscribe-discussion", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public void unsubscribeDiscussion(long groupId, String className, long classPK) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("className", checkNull(className));
            _params.put("classPK", classPK);

            _command.put("/comment.commentmanagerjsonws/unsubscribe-discussion", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public Long updateComment(String className, long classPK, long commentId, String subject, String body)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("className", checkNull(className));
            _params.put("classPK", classPK);
            _params.put("commentId", commentId);
            _params.put("subject", checkNull(subject));
            _params.put("body", checkNull(body));

            _command.put("/comment.commentmanagerjsonws/update-comment", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getLong(0);
    }
}