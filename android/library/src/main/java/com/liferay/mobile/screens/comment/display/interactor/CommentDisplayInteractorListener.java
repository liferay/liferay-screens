package com.liferay.mobile.screens.comment.display.interactor;

import com.liferay.mobile.screens.cache.CacheListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentDisplayInteractorListener extends CacheListener {

	void onLoadCommentFailure(Exception e);

	void onLoadCommentSuccess(CommentEntry commentEntry);

	void onDeleteCommentFailure(Exception e);

	void onDeleteCommentSuccess();

	void onUpdateCommentFailure(Exception e);

	void onUpdateCommentSuccess(CommentEntry commentEntry);
}
