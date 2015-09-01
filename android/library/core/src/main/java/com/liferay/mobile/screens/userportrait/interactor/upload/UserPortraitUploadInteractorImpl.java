package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseCachedWriteRemoteInteractor;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;

/**
 * @author Javier Gamarra
 */
public class UserPortraitUploadInteractorImpl
	extends BaseCachedWriteRemoteInteractor<UserPortraitInteractorListener, UserPortraitUploadEvent>
	implements UserPortraitUploadInteractor {

	public UserPortraitUploadInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	public void upload(final Long userId, final String picturePath) throws Exception {
		loadWithCache(userId, picturePath, false);
	}

	public void onEventMainThread(UserPortraitUploadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event, event.getUserId(), event.getPicturePath());
	}

	@Override
	public void online(Object[] args) throws Exception {

		long userId = (long) args[0];
		String picturePath = (String) args[1];

		if (getListener() != null) {
			getListener().onStartUserPortraitLoadRequest();
		}

		Intent service = new Intent(LiferayScreensContext.getContext(), UserPortraitService.class);
		service.putExtra("picturePath", picturePath);
		service.putExtra("screenletId", getTargetScreenletId());
		service.putExtra("userId", userId);

		LiferayScreensContext.getContext().startService(service);
	}

	@Override
	protected void notifySuccess(UserPortraitUploadEvent event) {
		User loggedUser = SessionContext.getLoggedUser();

		if (event.getJSONObject() != null) {
			User user = new User(event.getJSONObject());
			loggedUser = user;
			if (user.getId() == SessionContext.getLoggedUser().getId()) {
				SessionContext.setLoggedUser(user);
			}
		}

		try {
			getListener().onUserPortraitUploaded(loggedUser.getId());
		}
		catch (Exception e) {
			getListener().onUserPortraitUploadFailure(e);
		}

	}

	@Override
	protected void notifyError(UserPortraitUploadEvent event) {
		getListener().onUserPortraitUploadFailure(event.getException());
	}

	@Override
	protected void storeToCache(Object... args) {

		long userId = (long) args[0];
		String picturePath = (String) args[1];

		TableCache file = new TableCache(String.valueOf(userId), DefaultCachedType.USER_PORTRAIT_UPLOAD, picturePath);
		CacheSQL.getInstance().set(file);
	}

}
