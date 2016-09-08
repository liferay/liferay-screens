package com.liferay.mobile.screens.base.interactor.listener;

/**
 * @author Javier Gamarra
 */
public interface BaseCacheListener {

	//TODO Error propagating event instead of the exception
	void error(Exception e, String userAction);
}
