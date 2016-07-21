package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Alejandro Hernández
 */
public interface CommentDeleteInteractor extends Interactor<CommentListInteractorListener> {
	void deleteComment(long commentId)
		throws Exception;
}
