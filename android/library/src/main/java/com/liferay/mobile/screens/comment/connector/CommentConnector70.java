package com.liferay.mobile.screens.comment.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.commentmanagerjsonws.CommentmanagerjsonwsService;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentConnector;

public class CommentConnector70 implements CommentConnector {

	private CommentmanagerjsonwsService commentmanagerjsonwsService;

	public CommentConnector70(Session session) {
		commentmanagerjsonwsService = new CommentmanagerjsonwsService(session);
	}

	@Override
	public void deleteComment(long commentId) throws Exception {
		commentmanagerjsonwsService.deleteComment(commentId);
	}
}
