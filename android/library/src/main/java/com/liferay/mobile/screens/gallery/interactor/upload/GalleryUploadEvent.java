package com.liferay.mobile.screens.gallery.interactor.upload;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public class GalleryUploadEvent extends BasicEvent {

	public GalleryUploadEvent(int targetScreenletId, int totalBytes, int totalBytesSent) {
		super(targetScreenletId);

		this.totalBytes = totalBytes;
		this.totalBytesSent = totalBytesSent;
		this.completed = false;
	}

	public GalleryUploadEvent(int screenletId, ImageEntry imageEntry) {
		super(screenletId);

		this.completed = true;
		this.imageEntry = imageEntry;
	}

	public GalleryUploadEvent(int targetScreenletId, Exception exception) {
		super(targetScreenletId, exception);
	}

	public int getTotalBytes() {
		return totalBytes;
	}

	public int getTotalBytesSended() {
		return totalBytesSent;
	}

	public boolean isCompleted() {
		return completed;
	}

	public ImageEntry getImageEntry() {
		return imageEntry;
	}

	private int totalBytes;
	private int totalBytesSent;
	private boolean completed;
	private ImageEntry imageEntry;
}
