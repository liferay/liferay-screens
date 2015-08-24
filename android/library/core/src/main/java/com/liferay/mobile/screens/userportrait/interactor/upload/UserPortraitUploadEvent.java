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

	public UserPortraitUploadEvent(int targetScreenletId, String picturePath, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);

		_picturePath = picturePath;
	}

	public String getPicturePath() {
		return _picturePath;
	}

	private String _picturePath;
}
