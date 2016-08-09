package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.add.CommentAddListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentAddInteractor extends Interactor<CommentAddListener> {

	void addComment(long groupId, String className, long classPK, String body) throws Exception;
}
