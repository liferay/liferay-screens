package com.liferay.mobile.screens.gallery.interactor.upload;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public class GalleryUploadEvent extends BasicEvent {

	public GalleryUploadEvent(int targetScreenletId, int totalBytes, int totalBytesSended, boolean isCompleted,
		ImageEntry entry) {
		super(targetScreenletId);
		_totalBytes = totalBytes;
		_totalBytesSended = totalBytesSended;
		_isCompleted = isCompleted;
		_imageEntry = entry;
	}

	public GalleryUploadEvent(int targetScreenletId, Exception exception) {
		super(targetScreenletId, exception);
	}

	public int getTotalBytes() {
		return _totalBytes;
	}

	public int getTotalBytesSended() {
		return _totalBytesSended;
	}

	public boolean isCompleted() {
		return _isCompleted;
	}

	public ImageEntry getImageEntry() {
		return _imageEntry;
	}

	private int _totalBytes;
	private int _totalBytesSended;
	private boolean _isCompleted;
	private ImageEntry _imageEntry;
}
