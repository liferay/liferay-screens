package com.liferay.mobile.screens.gallery.interactor.upload;

public class GalleryProgress {

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
