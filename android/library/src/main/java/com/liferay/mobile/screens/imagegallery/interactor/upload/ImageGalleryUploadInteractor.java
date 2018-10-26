package com.liferay.mobile.screens.imagegallery.interactor.upload;

import android.content.Intent;
import android.net.Uri;
import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryEvent;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryInteractorListener;

/**
 * @author Víctor Galán Grande
 */
public class ImageGalleryUploadInteractor
    extends BaseCacheWriteInteractor<ImageGalleryInteractorListener, ImageGalleryEvent> {

    @Override
    public ImageGalleryEvent execute(ImageGalleryEvent event) throws Exception {

        validate(groupId, event.getFolderId(), event.getTitle(), event.getDescription(), event.getChangeLog(),
            event.getPictureUri());

        Intent service = new Intent(LiferayScreensContext.getContext(), ImageGalleryUploadService.class);
        service.putExtra("targetScreenletId", getTargetScreenletId());
        service.putExtra("actionName", getActionName());
        service.putExtra("repositoryId", event.getRepositoryId() == 0 ? groupId : event.getRepositoryId());
        service.putExtra("folderId", event.getFolderId());
        service.putExtra("title", event.getTitle());
        service.putExtra("description", event.getDescription());
        service.putExtra("changeLog", event.getChangeLog());
        service.putExtra("pictureUri", event.getPictureUri());

        LiferayScreensContext.getContext().startService(service);

        return null;
    }

    @Override
    public void onSuccess(ImageGalleryEvent event) {
        if (event.isStarting()) {
            getListener().onPictureUploadInformationReceived(event.getPictureUri(), event.getTitle(),
                event.getDescription(), event.getChangeLog());
        } else {
            getListener().onPictureUploaded(event.getImageEntry());
        }
    }

    @Override
    public void onFailure(ImageGalleryEvent event) {
        getListener().error(event.getException(), getActionName());
    }

    public void onEventMainThread(ImageGalleryProgress event) {
        getListener().onPictureUploadProgress(event.getTotalBytes(), event.getTotalBytesSent());
    }

    private void validate(long repositoryId, long folderId, String title, String description, String changeLog,
        Uri pictureUri) {

        if (repositoryId <= 0) {
            throw new IllegalArgumentException("Repository Id has to be greater than 0");
        } else if (folderId < 0) {
            throw new IllegalArgumentException("Folder Id has to be greater than 0");
        } else if (title == null) {
            throw new IllegalArgumentException("Title can not be null");
        } else if (description == null) {
            throw new IllegalArgumentException("Description can not be null");
        } else if (changeLog == null) {
            throw new IllegalArgumentException("Changelog can not be null");
        } else if (pictureUri == null) {
            throw new IllegalArgumentException("Picture path can not be null");
        }
    }
}
