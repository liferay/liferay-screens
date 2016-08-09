package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentAddEvent extends BasicEvent {

	public CommentAddEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public CommentAddEvent(int targetScreenletId, CommentEntry commentEntry) {
		super(targetScreenletId);

		this.commentEntry = commentEntry;
	}

	public CommentEntry getCommentEntry() {
		return commentEntry;
	}

	private CommentEntry commentEntry;
}
