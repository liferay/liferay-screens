package com.liferay.mobile.screens.comment.display.interactor.delete;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentDeleteInteractor extends Interactor<CommentDisplayInteractorListener> {

	void deleteComment(long commentId) throws Exception;
}
