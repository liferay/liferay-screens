package com.liferay.mobile.screens.gallery.view;

import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import java.util.List;

/**
 * @author Víctor Galán Grande
 */
public interface GalleryViewModel extends BaseListViewModel<ImageEntry> {

  void showDetailImage(ImageEntry image);
}
