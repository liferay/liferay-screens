package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadEvent extends BasicEvent {

	public CommentLoadEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public CommentLoadEvent(int targetScreenletId, CommentEntry commentEntry) {
		super(targetScreenletId);

		this.commentEntry = commentEntry;
	}

	public CommentEntry getCommentEntry() {
		return commentEntry;
	}

	private CommentEntry commentEntry;
}
