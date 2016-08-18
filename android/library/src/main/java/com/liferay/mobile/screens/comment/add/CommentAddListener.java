package com.liferay.mobile.screens.comment.add;

import com.liferay.mobile.screens.base.thread.listener.OfflineListenerNew;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentAddListener extends OfflineListenerNew {

	void onAddCommentSuccess(CommentEntry commentEntry);
}
