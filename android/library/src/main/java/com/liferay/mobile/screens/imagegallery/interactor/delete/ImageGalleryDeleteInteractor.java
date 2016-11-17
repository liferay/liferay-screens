package com.liferay.mobile.screens.imagegallery.interactor.delete;

import com.liferay.mobile.android.v7.dlapp.DLAppService;
import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryEvent;
import com.liferay.mobile.screens.imagegallery.interactor.ImageGalleryInteractorListener;

/**
 * @author Víctor Galán Grande
 */
public class ImageGalleryDeleteInteractor extends BaseCacheWriteInteractor<ImageGalleryInteractorListener, ImageGalleryEvent> {

	@Override
	public ImageGalleryEvent execute(ImageGalleryEvent event) throws Exception {

		long fileEntryId = event.getImageEntry().getFileEntryId();
		validate(fileEntryId);

		DLAppService dlAppService = new DLAppService(getSession());
		dlAppService.deleteFileEntry(fileEntryId);

		return event;
	}

	@Override
	public void onSuccess(ImageGalleryEvent event) {
		getListener().onImageEntryDeleted(event.getImageEntry().getFileEntryId());
	}

	@Override
	public void onFailure(ImageGalleryEvent event) {
		getListener().error(event.getException(), getActionName());
	}

	private void validate(long imageEntryId) {
		if (imageEntryId <= 0) {
			throw new IllegalArgumentException("Image entry Id must be greater than 0");
		}
	}
}
