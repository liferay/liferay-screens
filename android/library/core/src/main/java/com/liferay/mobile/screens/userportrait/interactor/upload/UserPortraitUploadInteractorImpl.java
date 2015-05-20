package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
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

	public void upload(Long userId, String picturePath) {
		if (getListener() != null) {
			getListener().onStartUserPortraitLoadRequest();
		}

		Intent service = new Intent(LiferayScreensContext.getContext(), UserPortraitService.class);
		service.putExtra("picturePath", picturePath);
		service.putExtra("screenletId", getTargetScreenletId());
		service.putExtra("userId", userId);

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
			User user = new User(event.getJSONObject());
			if (user.getId() == SessionContext.getLoggedUser().getId()) {
				SessionContext.setLoggedUser(user);
			}

			try {
				getListener().onUserPortraitUploaded(user.getId());
			}
			catch (Exception e) {
				getListener().onUserPortraitUploadFailure(e);
			}
		}
	}


}
