package com.liferay.mobile.screens.base.interactor;

/**
 * @author Javier Gamarra
 */
public interface OfflineCallback {

	void sendOnline() throws Exception;

	void storeToCache();

}
