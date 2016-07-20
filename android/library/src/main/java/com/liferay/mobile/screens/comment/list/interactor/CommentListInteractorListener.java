package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentListInteractorListener extends BaseListInteractorListener<CommentEntry> {

	void onDeleteCommentFailure(long commentId, Exception e);

	void onDeleteCommentSuccess(long commentId);
}
