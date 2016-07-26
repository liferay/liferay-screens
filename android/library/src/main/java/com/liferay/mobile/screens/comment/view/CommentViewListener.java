package com.liferay.mobile.screens.comment.view;

/**
 * @author Alejandro Hernández
 */
public interface CommentViewListener {
	void onEditButtonClicked(long commentId, String newBody);

	void onDeleteButtonClicked(long commentId);
}
