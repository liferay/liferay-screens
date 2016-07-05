package com.liferay.mobile.screens.gallery.interactor.delete;

import com.liferay.mobile.screens.gallery.interactor.BaseGalleryInteractor;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryDeleteInteractor extends BaseGalleryInteractor {

	void deleteImageEntry(long imageEntryId) throws Exception;
}
