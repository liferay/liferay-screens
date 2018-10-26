package com.liferay.mobile.screens.imagegallery.interactor;

import android.net.Uri;
import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;

public class ImageGalleryEvent extends ListEvent<ImageEntry> {

    private String title;
    private String description;
    private String changeLog;
    private String pictureUri;

    private ImageEntry imageEntry;
    private boolean starting;
    private long folderId;

    private long repositoryId;

    public ImageGalleryEvent() {
        super();
    }

    public ImageGalleryEvent(ImageEntry imageEntry) {
        this.imageEntry = imageEntry;
        starting = false;
    }

    public ImageGalleryEvent(Exception e) {
        super(e);
        starting = false;
    }

    public ImageGalleryEvent(Uri pictureUri, String title, String description, String changeLog) {
        this.pictureUri = pictureUri.toString();
        this.title = title;
        this.description = description;
        this.changeLog = changeLog;
        starting = true;
    }

    @Override
    public String getListKey() {
        return imageEntry.getImageUrl();
    }

    @Override
    public ImageEntry getModel() {
        return imageEntry;
    }

    public ImageEntry getImageEntry() {
        return imageEntry;
    }

    public void setImageEntry(ImageEntry imageEntry) {
        this.imageEntry = imageEntry;
    }

    public Uri getPictureUri() {
        return Uri.parse(pictureUri);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public boolean isStarting() {
        return starting;
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(long repositoryId) {
        this.repositoryId = repositoryId;
    }
}
