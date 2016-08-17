package com.liferay.mobile.screens.comment.display;

import com.liferay.mobile.screens.cache.CacheListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentDisplayListener extends CacheListener {

	void onLoadCommentFailure(long commentId, Exception e);

	void onLoadCommentSuccess(CommentEntry commentEntry);

	void onDeleteCommentFailure(CommentEntry commentEntry, Exception e);

	void onDeleteCommentSuccess(CommentEntry commentEntry);

	void onUpdateCommentFailure(CommentEntry commentEntry, Exception e);

	void onUpdateCommentSuccess(CommentEntry commentEntry);
}
