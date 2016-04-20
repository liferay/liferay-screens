package com.liferay.mobile.screens.webcontentdisplay.interactor;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public interface WebContentDisplayFromStructureInteractor extends WebContentDisplayBaseInteractor {

	void load(Long structureId, long groupId, String articleId, Locale locale) throws Exception;

}
