package com.liferay.mobile.screens.base.thread.event;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserEvent extends JSONThreadObjectEvent {

	public UserEvent(Exception e) {
		super(e);
	}

	public UserEvent(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public String getId() {
		return null;
	}
}
