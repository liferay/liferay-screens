package com.liferay.mobile.screens.gallery.interactor.upload;

import android.app.Activity;
import android.content.Intent;
import com.liferay.mobile.screens.base.MediaStoreEvent;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import com.liferay.mobile.screens.userportrait.interactor.upload.UserPortraitService;

/**
 * @author Víctor Galán Grande
 */
public class GalleryUploadInteractorImpl extends BaseRemoteInteractor<GalleryInteractorListener> implements GalleryUploadInteractor {

	public GalleryUploadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void onEvent(MediaStoreEvent event) {
		if (isValidEvent(event)) {
			getListener().onPicturePathReceived(event.getFilePath());
		}
	}

	@Override
	public void uploadImageEntry(long repositoryId, long folderId, String title,
		String description, String changeLog, String picturePath) throws Exception {

		validate(repositoryId, folderId, title, description, changeLog, picturePath);
		Intent service = new Intent(LiferayScreensContext.getContext(), GalleryUploadService.class);
		service.putExtra("screenletId", getTargetScreenletId());
		service.putExtra("repositoryId", repositoryId);
		service.putExtra("folderId", folderId);
		service.putExtra("title", title);
		service.putExtra("description", description);
		service.putExtra("changeLog", changeLog);
		service.putExtra("picturePath", picturePath);

		LiferayScreensContext.getContext().startService(service);
	}

	public void onEvent(GalleryUploadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if(event.isFailed()) {
			getListener().onPictureUploadFailure(event.getException());
		}
		else if(event.isCompleted()) {
			getListener().onPictureUploaded(event.getImageEntry());
		}
		else {
			getListener().onPictureUploadProgress(event.getTotalBytes(), event.getTotalBytesSended());
		}

	}

	private void validate(long repositoryId, long folderId, String title, String description, String changeLog,
		String picturePath) {

		if (repositoryId <= 0) {
			throw new IllegalArgumentException("Repository Id has to be greater than 0");
		}

		if (folderId < 0) {
			throw new IllegalArgumentException("Folder Id has to be greater than 0");
		}

		if (title == null) {
			throw new IllegalArgumentException("Title can not be null");
		}

		if (description == null) {
			throw new IllegalArgumentException("Description can not be null");
		}

		if (changeLog == null) {
			throw new IllegalArgumentException("Changelog can not be null");
		}

		if (picturePath == null) {
			throw new IllegalArgumentException("Picture path can not be null");
		}
	}
}
