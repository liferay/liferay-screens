package com.liferay.mobile.screens.gallery.interactor;

import com.liferay.mobile.screens.base.interactor.Interactor;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryInteractor extends Interactor<GalleryInteractorListener> {

  void loadRows(int startRow, int endRow, long groupId, long folderId) throws Exception;
}
