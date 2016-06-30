package com.liferay.mobile.screens.gallery.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;
import java.util.Locale;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryInteractor extends Interactor<GalleryInteractorListener> {

	void loadRows(long groupId, long folderId, String[] mimeTypes, int startRow, int endRow, Locale locale)
		throws Exception;

}
