package com.liferay.mobile.screens.portlet.interactor;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import org.json.JSONObject;

public class PortletEvent extends CacheEvent {

	public PortletEvent() {
		super();
	}

	public PortletEvent(JSONObject jsonObject) {
		super(jsonObject);
	}
}