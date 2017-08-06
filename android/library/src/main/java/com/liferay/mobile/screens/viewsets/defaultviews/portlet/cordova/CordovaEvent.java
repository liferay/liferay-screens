package com.liferay.mobile.screens.viewsets.defaultviews.portlet.cordova;

import com.liferay.mobile.screens.base.interactor.event.BasicEvent;

/**
 * @author Víctor Galán Grande
 */

public class CordovaEvent {

	public enum CordovaEventType { PAGE_STARTED, PAGE_FINISHED, ERROR }

	private CordovaEventType eventType;
	private String param;

	public CordovaEvent(CordovaEventType eventType, String param) {
		this.eventType = eventType;
		this.param = param;
	}

	public CordovaEventType getEventType() {
		return eventType;
	}

	public String getParam() {
		return param;
	}
}
