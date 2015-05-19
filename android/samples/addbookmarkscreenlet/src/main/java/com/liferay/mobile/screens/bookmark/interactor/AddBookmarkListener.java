package com.liferay.mobile.screens.bookmark.interactor;

/**
 * @author Javier Gamarra
 */
public interface AddBookmarkListener {
	void onAddBookmarkFailure(Exception exception);

	void onAddBookmarkSuccess();
}
