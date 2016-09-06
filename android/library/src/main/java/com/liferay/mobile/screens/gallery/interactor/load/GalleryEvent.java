package com.liferay.mobile.screens.gallery.interactor.load;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

public class GalleryEvent extends ListEvent<ImageEntry> {

	private String title;
	private String description;
	private int totalBytes;
	private int totalBytesSent;
	private boolean completed;
	private ImageEntry imageEntry;
	private String picturePath;
	private String changeLog;
	private boolean starting;

	public GalleryEvent() {
		super();
	}

	public GalleryEvent(ImageEntry imageEntry) {
		this.imageEntry = imageEntry;
		this.completed = true;
		this.starting = false;
	}

	public GalleryEvent(int totalBytes, int totalBytesSent) {
		this.totalBytes = totalBytes;
		this.totalBytesSent = totalBytesSent;
		this.completed = false;
		this.starting = false;
	}

	public GalleryEvent(Exception e) {
		super(e);
	}

	public GalleryEvent(String picturePath, String title, String description) {
		this.picturePath = picturePath;
		this.title = title;
		this.description = description;
		starting = false;
	}

	public int getTotalBytes() {
		return totalBytes;
	}

	public int getTotalBytesSent() {
		return totalBytesSent;
	}

	public boolean isCompleted() {
		return completed;
	}

	public ImageEntry getImageEntry() {
		return imageEntry;
	}

	public void setImageEntry(ImageEntry imageEntry) {
		this.imageEntry = imageEntry;
	}

	@Override
	public String getListKey() {
		return imageEntry.getImageUrl();
	}

	@Override
	public ImageEntry getModel() {
		return imageEntry;
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

	public boolean isStarting() {
		return starting;
	}
}
