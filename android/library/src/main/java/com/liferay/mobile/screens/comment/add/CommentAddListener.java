package com.liferay.mobile.screens.comment.add;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentAddListener extends BaseCacheListener {

	void onAddCommentSuccess(CommentEntry commentEntry);
}
