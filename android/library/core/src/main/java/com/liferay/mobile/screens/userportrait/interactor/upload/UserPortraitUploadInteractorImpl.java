package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadInteractorImpl extends BaseRemoteInteractor<UserPortraitInteractorListener>
	implements UserPortraitUploadInteractor {

	public UserPortraitUploadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void upload(String picturePath) {
		if (getListener() != null) {
			getListener().onStartUserPortraitLoadRequest();
		}

		Intent service = new Intent(LiferayScreensContext.getContext(), UserPortraitService.class);
		service.putExtra("picturePath", picturePath);
		service.putExtra("screenletId", getTargetScreenletId());

		LiferayScreensContext.getContext().startService(service);
	}

	public void onEventMainThread(UserPortraitUploadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onUserPortraitUploadFailure(event.getException());
		}
		else {
			JSONObject userAttributes = event.getJSONObject();
			User user = new User(event.getJSONObject());
			if (user.getId() == SessionContext.getLoggedUser().getId()) {
				SessionContext.setLoggedUser(user);
			}

			try {
				long portraitId = userAttributes.getLong("portraitId");
				String uuid = userAttributes.getString("uuid");

				getListener().onUserPortraitUploaded(true, portraitId, uuid);
			}
			catch (Exception e) {
				getListener().onUserPortraitUploadFailure(e);
			}
		}
	}


}
