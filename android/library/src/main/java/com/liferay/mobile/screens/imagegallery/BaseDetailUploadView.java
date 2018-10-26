package com.liferay.mobile.screens.imagegallery;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryEvent;
import com.liferay.mobile.screens.util.EventBusUtil;

/**
 * @author Víctor Galán Grande
 */
public abstract class BaseDetailUploadView extends RelativeLayout {

    protected String actionName;
    protected Uri pictureUri;
    protected int screenletId;

    public BaseDetailUploadView(Context context) {
        super(context);
    }

    public BaseDetailUploadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseDetailUploadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseDetailUploadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initializeUploadView(String actionName, Uri pictureUri, int screenletId) {
        this.actionName = actionName;
        this.pictureUri = pictureUri;
        this.screenletId = screenletId;
    }

    public void finishActivityAndStartUpload(String title, String description, String changelog) {
        finishActivityAndStartUpload(pictureUri, title, description, changelog);
    }

    public void finishActivityAndStartUpload(Uri pictureUri, String title, String description, String changelog) {
        ImageGalleryEvent event = new ImageGalleryEvent(pictureUri, title, description, changelog);
        event.setActionName(actionName);
        event.setTargetScreenletId(screenletId);
        EventBusUtil.post(event);
    }

    public abstract String getTitle();

    public abstract String getDescription();
}
