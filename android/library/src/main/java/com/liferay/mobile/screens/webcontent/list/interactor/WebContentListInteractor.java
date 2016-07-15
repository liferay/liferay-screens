package com.liferay.mobile.screens.webcontent.list.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public interface WebContentListInteractor extends Interactor<WebContentListInteractorListener> {

	void load(long groupId, long folderId, int startRow, int endRow, Locale locale, String obcClassName)
		throws Exception;

}
