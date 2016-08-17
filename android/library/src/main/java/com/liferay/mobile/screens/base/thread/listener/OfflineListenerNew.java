package com.liferay.mobile.screens.base.thread.listener;

/**
 * @author Javier Gamarra
 */
public interface OfflineListenerNew {

	void loadingFromCache(boolean success);

	void retrievingOnline(boolean triedInCache, Exception e);

	void storingToCache(Object object);

	void error(Exception e, String userAction);

}
