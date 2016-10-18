package com.liferay.mobile.screens.imagegallery.interactor;

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface ImageGalleryInteractorListener extends BaseListInteractorListener<ImageEntry> {

	void onImageEntryDeleted(long imageEntryId);

	void onPicturePathReceived(String picturePath);

	void onPictureUploaded(ImageEntry entry);

	void onPictureUploadProgress(int totalBytes, int totalBytesSent);

	void onPictureUploadInformationReceived(String picturePath, String title, String description, String changelog);
}
