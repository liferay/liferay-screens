package com.liferay.mobile.screens.bookmark;

/**
 * @author Javier Gamarra
 */
public interface AddBookmarkListener {
	void onAddBookmarkFailure(Exception exception);

	void onAddBookmarkSuccess();
}
