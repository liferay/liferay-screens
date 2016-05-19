package com.liferay.mobile.screens.testapp.gallery;

import android.support.annotation.NonNull;

import com.liferay.mobile.android.callback.typed.JSONArrayCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.dlapp.DLAppService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONArrayEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.EventBusUtil;

import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class GalleryInteractor extends BaseRemoteInteractor<GalleryListener> {

	public GalleryInteractor(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void load(long groupId, long folderId) {
		try {
			Session session = SessionContext.createSessionFromCurrentSession();
			session.setCallback(getCallback());
			DLAppService dlAppService = new DLAppService(session);
			dlAppService.getFileEntries(groupId, folderId);
		}
		catch (Exception e) {
			getListener().onErrorLoadingGallery(e);
		}
	}

	public void onEvent(JSONArrayEvent jsonArrayEvent) {
		if (jsonArrayEvent.isFailed()) {
			getListener().onErrorLoadingGallery(jsonArrayEvent.getException());
		}
		else {
			getListener().onGalleryLoaded(jsonArrayEvent.getJsonArray());
		}
	}

	@NonNull
	private JSONArrayCallback getCallback() {
		return new JSONArrayCallback() {
			@Override
			public void onFailure(Exception exception) {
				EventBusUtil.post(new JSONArrayEvent(getTargetScreenletId(), exception));
			}

			@Override
			public void onSuccess(JSONArray result) {
				EventBusUtil.post(new JSONArrayEvent(getTargetScreenletId(), result));
			}
		};
	}
}
