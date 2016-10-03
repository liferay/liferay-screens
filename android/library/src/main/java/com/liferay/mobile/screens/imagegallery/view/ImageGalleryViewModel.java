package com.liferay.mobile.screens.imagegallery.view;

import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface ImageGalleryViewModel extends BaseListViewModel<ImageEntry> {

	void deleteImage(long imageEntryId);

	void addImage(ImageEntry imageEntry);

	void imageUploadStart(String picturePath);

	void imageUploadProgress(int bytesSent, int totalBytes);

	void imageUploadComplete();
}
