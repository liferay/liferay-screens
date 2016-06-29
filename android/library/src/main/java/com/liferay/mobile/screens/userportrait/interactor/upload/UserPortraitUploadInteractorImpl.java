package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.MediaStoreEvent;
import com.liferay.mobile.screens.base.interactor.BaseCachedWriteRemoteInteractor;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.userportrait.interactor.UserPortraitInteractorListener;

import org.json.JSONObject;

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
		storeWithCache(userId, picturePath, false);
	}

	public void onEventMainThread(UserPortraitUploadEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			try {
				storeToCacheAndLaunchEvent(event, event.getUserId(), event.getPicturePath());
			}
			catch (Exception e) {
				getListener().onUserPortraitUploadFailure(event.getException());
			}
		}
		else {
			if (!event.isCacheRequest()) {
				store(true, event.getUserId(), event.getPicturePath());
			}

			User oldLoggedUser = SessionContext.getCurrentUser();

			if (event.getJSONObject() != null) {
				User user = new User(event.getJSONObject());
				if (oldLoggedUser != null && user.getId() == oldLoggedUser.getId()) {
					SessionContext.setCurrentUser(user);
				}
			}

			try {
				if (oldLoggedUser != null) {
					getListener().onUserPortraitUploaded(oldLoggedUser.getId());
				}
			}
			catch (Exception e) {
				getListener().onUserPortraitUploadFailure(e);
			}
		}
	}

	public void onEvent(MediaStoreEvent event) {
		getListener().onPicturePathReceived(event.getFilePath());
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
	protected void storeToCacheAndLaunchEvent(Object... args) {

		long userId = (long) args[0];
		String picturePath = (String) args[1];

		store(false, userId, picturePath);

		UserPortraitUploadEvent event = new UserPortraitUploadEvent(getTargetScreenletId(), picturePath, userId, new JSONObject());
		event.setCacheRequest(true);
		onEventMainThread(event);
	}

	private void store(boolean synced, long userId, String picturePath) {
		TableCache file = new TableCache(String.valueOf(userId), DefaultCachedType.USER_PORTRAIT_UPLOAD, picturePath);
		file.setDirty(!synced);
		CacheSQL.getInstance().set(file);
	}

}
