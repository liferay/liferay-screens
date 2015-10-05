package com.liferay.mobile.screens.userportrait.interactor.upload;

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadEvent extends JSONObjectEvent implements RemoteWrite {

	public UserPortraitUploadEvent(int targetScreenletId, String picturePath, long userId, Exception e) {
		super(targetScreenletId, e);

		_picturePath = picturePath;
		_userId = userId;
	}

	public UserPortraitUploadEvent(int targetScreenletId, String picturePath, long userId, JSONObject jsonObject) {
		super(targetScreenletId, jsonObject);

		_picturePath = picturePath;
		_userId = userId;
	}

	public String getPicturePath() {
		return _picturePath;
	}

	public long getUserId() {
		return _userId;
	}

	@Override
	public boolean isRemote() {
		return _remote;
	}

	public void setRemote(boolean remote) {
		_remote = remote;
	}

	private boolean _remote;
	private String _picturePath;
	private long _userId;
}
