package com.liferay.mobile.screens.gallery;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryListener extends BaseListListener<ImageEntry> {

	void onImageEntryDeleteFailure(GalleryScreenlet screenlet, Exception e);

	void onImageEntryDeleted(GalleryScreenlet screenlet, long imageEntryId);

	void onImageUploadStarted();

	void onImageUploadProgress(int totalBytes, int totalBytesSended);

	void onImageUploadEnd(ImageEntry entry);

	void onImageUploadFailure(Exception e);
}
