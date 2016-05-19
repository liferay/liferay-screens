package com.liferay.mobile.screens.testapp.gallery;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public interface GalleryListener  {

	void onGalleryLoaded(JSONArray jsonObject);

	void onErrorLoadingGallery(Exception e);
}
