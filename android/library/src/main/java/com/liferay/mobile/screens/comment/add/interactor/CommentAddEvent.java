package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentAddEvent extends BasicThreadEvent {

	public CommentAddEvent(String body, CommentEntry commentEntry) {
		this.body = body;
		this.commentEntry = commentEntry;
	}

	public CommentAddEvent(String body, Exception e) {
		super(e);
		this.body = body;
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
