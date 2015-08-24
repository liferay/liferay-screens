package com.liferay.mobile.screens.userportrait.interactor.load;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitLoadEvent extends JSONObjectEvent {

	public UserPortraitLoadEvent(int targetScreenletId, Exception e, long userId) {
		super(targetScreenletId, e);

		_userId = userId;
	}

	public UserPortraitLoadEvent(int targetScreenletId, JSONObject jsonObject, long userId) {
		super(targetScreenletId, jsonObject);

		_userId = userId;
	}

	public long getUserId() {
		return _userId;
	}

	private final long _userId;
}
