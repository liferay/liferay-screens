package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.list.CommentListListener;

/**
 * @author Alejandro Hernández
 */
public interface CommentListInteractor extends Interactor<CommentListListener> {

	void loadRows(long groupId, String className, long classPK,
		long commentId, int startRow, int endRow)
		throws Exception;

}
