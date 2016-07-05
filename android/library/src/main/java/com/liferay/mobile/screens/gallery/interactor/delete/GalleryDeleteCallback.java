package com.liferay.mobile.screens.gallery.interactor.delete;

import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;

import org.json.JSONObject;

public class GalleryDeleteCallback extends JSONObjectCallback {

    public GalleryDeleteCallback(int targetScreenletId) {
        super(targetScreenletId);
    }

    @Override
    public JSONObject transform(Object obj) throws Exception {
        return new JSONObject();
    }
}
