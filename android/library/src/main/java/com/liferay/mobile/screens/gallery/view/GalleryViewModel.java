package com.liferay.mobile.screens.gallery.view;

import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.gallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryViewModel extends BaseListViewModel<ImageEntry> {

	void setColumns(int numCols);

	void deleteImage(long imageEntryId);

	void addImage(ImageEntry imageEntry);

	void updateView();
}
