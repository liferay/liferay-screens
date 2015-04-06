package com.liferay.mobile.screens.userportrait.interactor.upload;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadEvent extends JSONObjectEvent {
	public UserPortraitUploadEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public UserPortraitUploadEvent(int targetScreenletId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);
	}
}
