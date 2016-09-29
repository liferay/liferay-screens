package com.liferay.mobile.screens.gallery.interactor.upload;

import android.content.Intent;
import com.liferay.mobile.screens.base.MediaStoreEvent;
import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.gallery.interactor.GalleryEvent;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;

/**
 * @author Víctor Galán Grande
 */
public class GalleryUploadInteractor extends BaseCacheWriteInteractor<GalleryInteractorListener, GalleryEvent> {

	@Override
	public GalleryEvent execute(GalleryEvent event) throws Exception {

		validate(groupId, event.getFolderId(), event.getTitle(), event.getDescription(), event.getChangeLog(),
			event.getPicturePath());

		Intent service = new Intent(LiferayScreensContext.getContext(), GalleryUploadService.class);
		service.putExtra("targetScreenletId", getTargetScreenletId());
		service.putExtra("actionName", getActionName());

		service.putExtra("repositoryId", groupId);
		service.putExtra("folderId", event.getFolderId());
		service.putExtra("title", event.getTitle());
		service.putExtra("description", event.getDescription());
		service.putExtra("changeLog", event.getChangeLog());
		service.putExtra("picturePath", event.getPicturePath());

		LiferayScreensContext.getContext().startService(service);

		return null;
	}

	@Override
	public void onSuccess(GalleryEvent event) throws Exception {
		if (event.isStarting()) {
			getListener().onPictureUploadInformationReceived(event.getPicturePath(), event.getTitle(),
				event.getDescription(), event.getChangeLog());
		} else {
			getListener().onPictureUploaded(event.getImageEntry());
		}
	}

	@Override
	protected void onFailure(GalleryEvent event) {
		getListener().error(event.getException(), getActionName());
	}

	public void onEventMainThread(MediaStoreEvent event) {
		getListener().onPicturePathReceived(event.getFilePath());
	}

	public void onEventMainThread(GalleryProgress event) {
		getListener().onPictureUploadProgress(event.getTotalBytes(), event.getTotalBytesSent());
	}

	private void validate(long repositoryId, long folderId, String title, String description, String changeLog,
		String picturePath) {

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
		} else if (picturePath == null) {
			throw new IllegalArgumentException("Picture path can not be null");
		}
	}
}
