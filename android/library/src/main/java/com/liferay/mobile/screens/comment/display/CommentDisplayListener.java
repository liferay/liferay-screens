package com.liferay.mobile.screens.comment.display;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentDisplayListener extends BaseCacheListener {

	void onLoadCommentSuccess(CommentEntry commentEntry);

	void onDeleteCommentSuccess(CommentEntry commentEntry);

	void onUpdateCommentSuccess(CommentEntry commentEntry);
}
