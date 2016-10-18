package com.liferay.mobile.screens.comment.display.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentDisplayViewModel extends BaseViewModel {

	void showFinishOperation(String loadCommentAction, boolean editable);

	void showFinishOperation(String loadCommentAction, boolean editable, CommentEntry commentEntry);
}
