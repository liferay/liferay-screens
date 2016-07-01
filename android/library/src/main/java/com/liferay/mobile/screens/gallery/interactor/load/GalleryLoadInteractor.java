package com.liferay.mobile.screens.gallery.interactor.load;

import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.gallery.interactor.BaseGalleryInteractor;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import java.util.Locale;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryLoadInteractor extends BaseGalleryInteractor {

	void loadRows(long groupId, long folderId, String[] mimeTypes, int startRow, int endRow, Locale locale)
		throws Exception;

}
