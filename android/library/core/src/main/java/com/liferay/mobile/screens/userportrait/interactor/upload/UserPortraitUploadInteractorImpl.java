package com.liferay.mobile.screens.userportrait.interactor.upload;

import android.content.Intent;

import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.OfflineCallback;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.CachePolicy;
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
	extends BaseCachedRemoteInteractor<UserPortraitInteractorListener, UserPortraitUploadEvent>
	implements UserPortraitUploadInteractor {

	public UserPortraitUploadInteractorImpl(int targetScreenletId, CachePolicy cachePolicy, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, cachePolicy, offlinePolicy);
	}

	public void upload(final Long userId, final String picturePath) throws Exception {
		storeOnError(new OfflineCallback() {
			@Override
			public void sendOnline() throws Exception {
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
			public void storeToCache() {
				store(userId, picturePath);
			}
		});
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

			store(user.getId(), event.getPicturePath());

			try {
				getListener().onUserPortraitUploaded(user.getId());
			}
			catch (Exception e) {
				getListener().onUserPortraitUploadFailure(e);
			}
		}
	}

	private void store(Long userId, String picturePath) {
		Cache cache = CacheSQL.getInstance();
		cache.set(new TableCache(String.valueOf(userId), DefaultCachedType.USER_PORTRAIT_UPLOAD, picturePath));
	}

}
