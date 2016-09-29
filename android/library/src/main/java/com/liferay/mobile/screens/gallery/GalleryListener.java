package com.liferay.mobile.screens.gallery;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryListener extends BaseListListener<ImageEntry> {

	void onImageEntryDeleted(long imageEntryId);

	void onImageUploadStarted(String picturePath, String title, String description, String changelog);

	void onImageUploadProgress(int totalBytes, int totalBytesSent);

	void onImageUploadEnd(ImageEntry entry);

	Class provideImageUploadDetailActivity();
}
