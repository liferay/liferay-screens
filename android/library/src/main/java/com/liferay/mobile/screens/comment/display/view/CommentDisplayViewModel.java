package com.liferay.mobile.screens.comment.display.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentDisplayViewModel extends BaseViewModel {
	void refreshView();
	void setEditable(boolean editable);
	void setCommentEntry(CommentEntry commentEntry);
}
