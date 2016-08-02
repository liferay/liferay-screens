package com.liferay.mobile.screens.comment.display.interactor.load;

/**
 * @author Alejandro Hernández
 */
public interface CommentLoadInteractor {
	void loadComment(long groupId, long commentId) throws Exception;
}
