package com.liferay.mobile.screens.comment.list.interactor.delete;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractorListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentDeleteInteractor extends Interactor<CommentListInteractorListener> {
	void deleteComment(long commentId)
		throws Exception;
}
