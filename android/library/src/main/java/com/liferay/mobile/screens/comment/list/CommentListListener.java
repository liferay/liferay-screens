package com.liferay.mobile.screens.comment.list;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentListListener extends BaseListListener<CommentEntry> {

	void onDeleteCommentFailure(CommentEntry commentEntry, Exception e);

	void onDeleteCommentSuccess(CommentEntry commentEntry);

	void onUpdateCommentFailure(CommentEntry commentEntry, Exception e);

	void onUpdateCommentSuccess(CommentEntry commentEntry);
}
