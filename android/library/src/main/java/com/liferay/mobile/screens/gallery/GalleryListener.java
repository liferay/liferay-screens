package com.liferay.mobile.screens.gallery;

import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryListener extends BaseListListener<ImageEntry> {

	void onImageEntryDeleteFailure(GalleryScreenlet screenlet, Exception e);
	void onImageEntryDeleted(GalleryScreenlet screenlet, long imageEntryId);

}
