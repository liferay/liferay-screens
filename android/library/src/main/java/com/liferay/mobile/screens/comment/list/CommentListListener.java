package com.liferay.mobile.screens.comment.list;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentListListener extends BaseListListener<CommentEntry> {
	void onDeleteCommentFailure(long commentId, Exception e);

	void onDeleteCommentSuccess(long commentId);
}
