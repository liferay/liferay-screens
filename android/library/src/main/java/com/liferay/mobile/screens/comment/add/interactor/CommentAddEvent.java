package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentAddEvent extends BasicEvent {

	public CommentAddEvent(int targetScreenletId, String body, Exception e) {
		super(targetScreenletId, e);
		this.body = body;
	}

	public CommentAddEvent(int targetScreenletId, String body, CommentEntry commentEntry) {
		super(targetScreenletId);
		this.body = body;
		this.commentEntry = commentEntry;
	}

	public CommentEntry getCommentEntry() {
		return commentEntry;
	}

	public String getBody() {
		return body;
	}

	private String body;
	private CommentEntry commentEntry;
}
