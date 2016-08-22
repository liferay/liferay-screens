package com.liferay.mobile.screens.userportrait.interactor.upload;

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadEvent extends OfflineEventNew {

	public UserPortraitUploadEvent(String picturePath) {
		this(picturePath, new JSONObject());
	}

	public UserPortraitUploadEvent(String picturePath, JSONObject jsonObject) {
		super(jsonObject);

		this._picturePath = picturePath;
	}

	public String getPicturePath() {
		return _picturePath;
	}

	private String _picturePath;
}
