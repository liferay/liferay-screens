package com.liferay.mobile.screens.sites;

import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Javier Gamarra
 */
public interface SiteInteractor extends Interactor<SiteListener> {

	void loadSites(Long userId) throws Exception;
}
