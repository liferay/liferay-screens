package com.liferay.mobile.screens.comment.interactor.update;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.interactor.CommentListInteractorListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentUpdateInteractor extends Interactor<CommentListInteractorListener> {
	void updateComment(String className, long classPK, long commentId, String newBody)
		throws Exception;
}
