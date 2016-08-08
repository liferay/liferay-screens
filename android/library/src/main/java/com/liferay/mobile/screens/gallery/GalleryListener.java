package com.liferay.mobile.screens.gallery;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryListener extends BaseListListener<ImageEntry> {

	void onImageEntryDeleteFailure(Exception e);

	void onImageEntryDeleted(long imageEntryId);

	void onImageUploadStarted();

	void onImageUploadProgress(int totalBytes, int totalBytesSent);

	void onImageUploadEnd(ImageEntry entry);

	void onImageUploadFailure(Exception e);
}
