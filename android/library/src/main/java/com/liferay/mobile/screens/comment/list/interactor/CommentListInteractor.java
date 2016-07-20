package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Alejandro Hernández
 */
public interface CommentListInteractor extends Interactor<CommentListInteractorListener> {

	void loadRows(long groupId, String className, long classPK,
		long commentId, int startRow, int endRow)
		throws Exception;

}
