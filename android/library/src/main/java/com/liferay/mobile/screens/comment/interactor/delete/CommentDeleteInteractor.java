package com.liferay.mobile.screens.comment.interactor.delete;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.interactor.CommentListInteractorListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentDeleteInteractor extends Interactor<CommentListInteractorListener> {
	void deleteComment(long commentId)
		throws Exception;
}
