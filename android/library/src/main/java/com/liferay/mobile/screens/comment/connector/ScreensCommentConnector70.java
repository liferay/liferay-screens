package com.liferay.mobile.screens.comment.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.ScreenscommentService;
import org.json.JSONArray;
import org.json.JSONObject;

public class ScreensCommentConnector70 implements ScreensCommentConnector {

    private ScreenscommentService screenscommentService;

    public ScreensCommentConnector70(Session session) {
        screenscommentService = new ScreenscommentService(session);
    }

    @Override
    public JSONObject addComment(String className, long classPK, String body) throws Exception {
        return screenscommentService.addComment(className, classPK, body);
    }

    @Override
    public JSONArray getComments(String className, long classPK, int startRow, int endRow) throws Exception {
        return screenscommentService.getComments(className, classPK, startRow, endRow);
    }

    @Override
    public Integer getCommentsCount(String className, long classPK) throws Exception {
        return screenscommentService.getCommentsCount(className, classPK);
    }

    @Override
    public JSONObject getComment(long commentId) throws Exception {
        return screenscommentService.getComment(commentId);
    }

    @Override
    public JSONObject updateComment(long commentId, String newBody) throws Exception {
        return screenscommentService.updateComment(commentId, newBody);
    }
}
