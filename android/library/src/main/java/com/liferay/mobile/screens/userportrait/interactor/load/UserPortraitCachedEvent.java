package com.liferay.mobile.screens.userportrait.interactor.load;

import com.liferay.mobile.screens.base.interactor.event.CachedEvent;
import org.json.JSONObject;

public class UserPortraitCachedEvent extends CachedEvent {

	public UserPortraitCachedEvent() {
		super();
	}

	public UserPortraitCachedEvent(JSONObject jsonObject) {
		super(jsonObject);
	}
}