package com.liferay.mobile.screens.bookmark.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;

/**
 * @author Javier Gamarra
 */
public interface AddBookmarkViewModel extends BaseViewModel {

	String getURL();

	void setURL(String value);

	String getTitle();

	void setTitle(String value);

}
