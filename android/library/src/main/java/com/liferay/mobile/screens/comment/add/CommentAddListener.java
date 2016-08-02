package com.liferay.mobile.screens.comment.add;

import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentAddListener {
	void onAddCommentFailure(String body, Exception e);

	void onAddCommentSuccess(CommentEntry commentEntry);
}
