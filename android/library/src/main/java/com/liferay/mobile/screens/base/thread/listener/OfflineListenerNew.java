package com.liferay.mobile.screens.base.thread.listener;

/**
 * @author Javier Gamarra
 */
public interface OfflineListenerNew {

	//TODO Error propagating event instead of the exception
	void error(Exception e, String userAction);
}
