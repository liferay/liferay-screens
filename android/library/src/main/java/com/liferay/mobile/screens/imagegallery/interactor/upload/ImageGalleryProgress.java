package com.liferay.mobile.screens.imagegallery.interactor.upload;

public class ImageGalleryProgress {

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
