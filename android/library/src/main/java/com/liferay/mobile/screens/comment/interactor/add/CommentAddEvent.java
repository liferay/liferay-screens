package com.liferay.mobile.screens.comment.interactor.add;

import com.liferay.mobile.screens.base.interactor.BasicEvent;

/**
 * @author Alejandro Hernández
 */
public class CommentAddEvent extends BasicEvent {
	public CommentAddEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public CommentAddEvent(int targetScreenletId, long commentId) {
		super(targetScreenletId);

		_commentId = commentId;
	}

	public long getCommentId() {
		return _commentId;
	}

	private long _commentId;
}
