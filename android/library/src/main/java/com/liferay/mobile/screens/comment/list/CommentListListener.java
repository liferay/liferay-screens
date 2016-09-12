package com.liferay.mobile.screens.comment.list;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentListListener extends BaseListListener<CommentEntry> {

	void onDeleteCommentSuccess(CommentEntry commentEntry);

	void onUpdateCommentSuccess(CommentEntry commentEntry);
}
