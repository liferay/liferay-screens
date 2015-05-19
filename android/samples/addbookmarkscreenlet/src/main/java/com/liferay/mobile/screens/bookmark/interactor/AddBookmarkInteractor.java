package com.liferay.mobile.screens.bookmark.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Javier Gamarra
 */
public interface AddBookmarkInteractor extends Interactor<AddBookmarkListener> {

	void addBookmark(String url, String title, Integer folderId) throws Exception;

}
