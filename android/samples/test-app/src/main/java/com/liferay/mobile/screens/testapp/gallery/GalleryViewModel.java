package com.liferay.mobile.screens.testapp.gallery;

import com.liferay.mobile.screens.base.view.BaseViewModel;

import org.json.JSONObject;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface GalleryViewModel extends BaseViewModel {

	void showImages(List<JSONObject> files);
}
