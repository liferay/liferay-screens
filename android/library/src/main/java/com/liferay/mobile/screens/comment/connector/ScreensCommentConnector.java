package com.liferay.mobile.screens.comment.connector;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ScreensCommentConnector {
    JSONObject addComment(String className, long classPK, String body) throws Exception;

    JSONArray getComments(String className, long classPK, int startRow, int endRow) throws Exception;

    Integer getCommentsCount(String className, long classPK) throws Exception;

    JSONObject getComment(long commentId) throws Exception;

    JSONObject updateComment(long commentId, String newBody) throws Exception;
}
