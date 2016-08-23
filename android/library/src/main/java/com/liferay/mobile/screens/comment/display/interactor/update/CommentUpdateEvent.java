package com.liferay.mobile.screens.comment.display.interactor.update;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentUpdateEvent extends BasicEvent {

	public CommentUpdateEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public CommentUpdateEvent(int targetScreenletId, CommentEntry commentEntry) {
		super(targetScreenletId);

		this.commentEntry = commentEntry;
	}

	public CommentEntry getCommentEntry() {
		return commentEntry;
	}

	private CommentEntry commentEntry;
}
