package com.liferay.mobile.screens.userportrait.interactor.load;

import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitLoadEvent extends JSONObjectEvent {

	public UserPortraitLoadEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public UserPortraitLoadEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
