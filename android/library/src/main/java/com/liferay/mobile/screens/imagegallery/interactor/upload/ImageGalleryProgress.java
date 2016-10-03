package com.liferay.mobile.screens.imagegallery.interactor.upload;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;

public class ImageGalleryProgress extends CacheEvent {

	private final int totalBytes;
	private final int totalBytesSent;

	public ImageGalleryProgress(int totalBytes, int totalBytesSent) {
		this.totalBytes = totalBytes;
		this.totalBytesSent = totalBytesSent;
	}

	public int getTotalBytes() {
		return totalBytes;
	}

	public int getTotalBytesSent() {
		return totalBytesSent;
	}
}
