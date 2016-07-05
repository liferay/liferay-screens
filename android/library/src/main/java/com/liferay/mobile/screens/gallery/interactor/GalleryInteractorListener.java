package com.liferay.mobile.screens.gallery.interactor;

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryInteractorListener extends BaseListInteractorListener<ImageEntry> {

	void onImageEntryDeleteFailure(Exception e);

	void onImageEntryDeleted(long imageEntryId);

	void onPicturePathReceived(String picturePath);

	void onPictureUploaded(ImageEntry entry);

	void onPictureUploadProgress(int totalBytes, int totalBytesSended);

	void onPictureUploadFailure(Exception e);
}
