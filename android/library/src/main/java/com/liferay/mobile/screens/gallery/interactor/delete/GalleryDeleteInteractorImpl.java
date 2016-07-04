package com.liferay.mobile.screens.gallery.interactor.delete;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.dlapp.DLAppService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.gallery.interactor.GalleryInteractorListener;
import com.liferay.mobile.screens.userportrait.interactor.load.UserPortraitLoadCallback;

/**
 * @author Víctor Galán Grande
 */
public class GalleryDeleteInteractorImpl extends BaseRemoteInteractor<GalleryInteractorListener>
	implements GalleryDeleteInteractor {

	public GalleryDeleteInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void deleteImageEntry(long imageEntryId) throws Exception {
		_imageEntryId = imageEntryId;
		getDLAppService().deleteFileEntry(imageEntryId);
	}

	public void onEvent(GalleryDeleteEvent event) {
		if(!isValidEvent(event)){
			return;
		}

		if(event.isFailed()) {
			getListener().onImageEntryDeleteFailure(event.getException());
		}
		else {
			getListener().onImageEntryDeleted(event.getImageEntryId());
		}
	}

	private DLAppService getDLAppService() {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new GalleryDeleteCallback(getTargetScreenletId(), _imageEntryId));

		return new DLAppService(session);
	}

	private long _imageEntryId;
}
