package com.liferay.mobile.screens.testapp.gallery;

import org.json.JSONObject;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface GalleryListener  {

	void onGalleryLoaded(List<JSONObject> images);

	void onErrorLoadingGallery(Exception e);
}
