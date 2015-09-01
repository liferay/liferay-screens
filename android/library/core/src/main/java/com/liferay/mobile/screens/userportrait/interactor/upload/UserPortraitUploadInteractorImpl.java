package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseCachedWriteRemoteInteractor;
import com.liferay.mobile.screens.cache.Cache;
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

		if (event.isFailed()) {
			getListener().onUserPortraitUploadFailure(event.getException());
		}
		else {
			User user = new User(event.getJSONObject());
			if (user.getId() == SessionContext.getLoggedUser().getId()) {
				SessionContext.setLoggedUser(user);
			}

			try {
				storeToCache(user.getId(), event.getPicturePath(), true);

				getListener().onUserPortraitUploaded(user.getId());
			}
			catch (Exception e) {
				getListener().onUserPortraitUploadFailure(e);
			}
		}
	}

	@Override
	protected void sendOnline(Object[] args) throws Exception {

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
	protected void storeToCache(Object... args) {

		long userId = (long) args[0];
		String picturePath = (String) args[1];
		boolean sent = (boolean) args[2];

		TableCache file = new TableCache(String.valueOf(userId), DefaultCachedType.USER_PORTRAIT_UPLOAD, picturePath);
		file.setSent(sent);
		CacheSQL.getInstance().set(file);
	}

}
