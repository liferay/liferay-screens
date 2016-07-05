package com.liferay.mobile.screens.gallery.interactor.upload;

import com.liferay.mobile.screens.gallery.interactor.BaseGalleryInteractor;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryUploadInteractor extends BaseGalleryInteractor {

	void uploadImageEntry(long repositoryId, long folderId, String title, String description, String changeLog,
		String picturePath);
}
