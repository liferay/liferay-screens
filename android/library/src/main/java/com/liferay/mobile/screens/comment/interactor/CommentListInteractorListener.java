package com.liferay.mobile.screens.comment.interactor;

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentListInteractorListener extends BaseListInteractorListener<CommentEntry> {

	void onDeleteCommentFailure(long commentId, Exception e);

	void onDeleteCommentSuccess(long commentId);

	void onUpdateCommentSuccess(long commentId);

	void onUpdateCommentFailure(long commentId, Exception e);
}
