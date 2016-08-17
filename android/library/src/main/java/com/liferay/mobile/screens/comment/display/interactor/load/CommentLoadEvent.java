package com.liferay.mobile.screens.comment.display.interactor.load;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentLoadEvent extends BasicThreadEvent {

	public CommentLoadEvent(CommentEntry commentEntry) {
		this.commentEntry = commentEntry;
	}

	public CommentEntry getCommentEntry() {
		return commentEntry;
	}

	private CommentEntry commentEntry;
}
