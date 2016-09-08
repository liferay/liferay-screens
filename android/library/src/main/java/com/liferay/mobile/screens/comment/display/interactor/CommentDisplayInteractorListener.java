package com.liferay.mobile.screens.comment.display.interactor;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentDisplayInteractorListener extends BaseCacheListener {

	void onLoadCommentSuccess(CommentEntry commentEntry);

	void onDeleteCommentSuccess();

	void onUpdateCommentSuccess(CommentEntry commentEntry);
}
