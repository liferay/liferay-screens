package com.liferay.mobile.screens.gallery.interactor.upload;

import com.liferay.mobile.screens.base.interactor.BasicEvent;

/**
 * @author Víctor Galán Grande
 */
public class StartUploadEvent extends BasicEvent {

	public StartUploadEvent(int screenletId, String picturePath, String title, String description) {
		super(screenletId);

		this.picturePath = picturePath;
		this.title = title;
		this.description = description;
	}

	public String getPicturePath() {
		return picturePath;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	private String picturePath;
	private String title;
	private String description;
}
