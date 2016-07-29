package com.liferay.mobile.screens.comment.interactor.add;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.interactor.CommentListInteractorListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentAddInteractor extends Interactor<CommentListInteractorListener> {
	void addComment(long groupId, String className, long classPK, String body)
		throws Exception;
}
