package com.liferay.mobile.screens.bookmark.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.bookmark.AddBookmarkListener;

/**
 * @author Javier Gamarra
 */
public interface AddBookmarkInteractor extends Interactor<AddBookmarkListener> {

	void addBookmark(String url, String title, Integer folderId) throws Exception;

}
