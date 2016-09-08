package com.liferay.mobile.screens.userportrait.interactor.load;

import com.liferay.mobile.screens.base.interactor.event.CachedEvent;
import org.json.JSONObject;

public class UserPortraitEvent extends CachedEvent {

	public UserPortraitEvent() {
		super();
	}

	public UserPortraitEvent(JSONObject jsonObject) {
		super(jsonObject);
	}
}