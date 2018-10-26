package com.liferay.mobile.screens.viewsets.defaultviews.web.cordova;

/**
 * @author Víctor Galán Grande
 */

public class CordovaEvent {

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

    public enum CordovaEventType {PAGE_STARTED, PAGE_FINISHED, ERROR}
}
