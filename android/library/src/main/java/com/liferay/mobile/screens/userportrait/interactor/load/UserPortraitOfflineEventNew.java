package com.liferay.mobile.screens.userportrait.interactor.load;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import org.json.JSONObject;

public class UserPortraitOfflineEventNew extends OfflineEventNew {

	public UserPortraitOfflineEventNew() {
		super();
	}

	public UserPortraitOfflineEventNew(JSONObject jsonObject) {
		super(jsonObject);
	}
}