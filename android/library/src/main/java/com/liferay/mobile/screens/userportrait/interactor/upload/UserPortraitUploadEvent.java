package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.net.Uri;
import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadEvent extends CacheEvent {

    private String uriPath;

    public UserPortraitUploadEvent() {
        super();
    }

    public UserPortraitUploadEvent(Uri uriPath) {
        this(uriPath, new JSONObject());
    }

    public UserPortraitUploadEvent(Uri uriPath, JSONObject jsonObject) {
        super(jsonObject);

        this.uriPath = uriPath.toString();
    }

    public UserPortraitUploadEvent(Exception exception) {
        super(exception);
    }

    public Uri getUriPath() {
        return Uri.parse(uriPath);
    }

    public void setUriPath(Uri uriPath) {
        this.uriPath = uriPath.toString();
    }
}
