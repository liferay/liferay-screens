package com.liferay.mobile.screens.comment.display.interactor.update;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentUpdateInteractor extends Interactor<CommentDisplayInteractorListener> {
	void updateComment(String className, long classPK, long commentId, String newBody)
		throws Exception;
}
