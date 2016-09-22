package com.liferay.mobile.screens.gallery.interactor.upload;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;

public class GalleryProgress extends CacheEvent {

	private final int totalBytes;
	private final int totalBytesSent;

	public GalleryProgress(int totalBytes, int totalBytesSent) {
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
