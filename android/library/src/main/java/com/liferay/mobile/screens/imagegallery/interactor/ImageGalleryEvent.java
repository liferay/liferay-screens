package com.liferay.mobile.screens.imagegallery.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;

public class ImageGalleryEvent extends ListEvent<ImageEntry> {

	private String title;
	private String description;
	private String changeLog;
	private String picturePath;

	private ImageEntry imageEntry;
	private boolean starting;
	private long folderId;

	public ImageGalleryEvent() {
		super();
	}

	public ImageGalleryEvent(ImageEntry imageEntry) {
		this.imageEntry = imageEntry;
		starting = false;
	}

	public ImageGalleryEvent(Exception e) {
		super(e);
		starting = false;
	}

	public ImageGalleryEvent(String picturePath, String title, String description, String changeLog) {
		this.picturePath = picturePath;
		this.title = title;
		this.description = description;
		this.changeLog = changeLog;
		starting = true;
	}

	@Override
	public String getListKey() {
		return imageEntry.getImageUrl();
	}

	@Override
	public ImageEntry getModel() {
		return imageEntry;
	}

	public ImageEntry getImageEntry() {
		return imageEntry;
	}

	public void setImageEntry(ImageEntry imageEntry) {
		this.imageEntry = imageEntry;
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

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}

	public boolean isStarting() {
		return starting;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	public long getFolderId() {
		return folderId;
	}
}
