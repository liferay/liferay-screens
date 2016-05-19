package com.liferay.mobile.screens.testapp.gallery;

import com.liferay.mobile.screens.base.view.BaseViewModel;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public interface GalleryViewModel extends BaseViewModel {

	void showImages(JSONArray files);
}
