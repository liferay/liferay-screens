package com.liferay.mobile.screens.userportrait.interactor.upload;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadEvent extends OfflineEventNew {

	public UserPortraitUploadEvent() {
		super();
	}

	public UserPortraitUploadEvent(String picturePath) {
		this(picturePath, new JSONObject());
	}

	public UserPortraitUploadEvent(String picturePath, JSONObject jsonObject) {
		super(jsonObject);

		this._picturePath = picturePath;
	}

	public UserPortraitUploadEvent(Exception exception) {
		super(exception);
	}

	public String getPicturePath() {
		return _picturePath;
	}

	private String _picturePath;

	public void setPicturePath(String picturePath) {
		this._picturePath = picturePath;
	}
}
