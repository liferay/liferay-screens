package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.list.interactor.CommentListInteractorListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentListInteractor extends Interactor<CommentListInteractorListener> {

	void loadRows(long groupId, String className, long classPK, int startRow, int endRow)
		throws Exception;
}
