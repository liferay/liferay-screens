package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.userportrait.interactor.upload.RemoteWrite;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedWriteRemoteInteractor<L, E extends RemoteWrite> extends BaseRemoteInteractor<L> {

	public BaseCachedWriteRemoteInteractor(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId);

		_offlinePolicy = offlinePolicy;
	}

	protected void storeWithCache(Object... args) throws Exception {
		if (_offlinePolicy == OfflinePolicy.CACHE_ONLY) {
			storeToCacheAndLaunchEvent(args);
		}
		else if (_offlinePolicy == OfflinePolicy.CACHE_FIRST) {
			try {
				storeToCacheAndLaunchEvent(args);
			}
			catch (Exception e) {
				online(args);
			}
		}
		else if (_offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
			try {
				online(args);
			}
			catch (Exception e) {
				storeToCacheAndLaunchEvent(args);
				LiferayLogger.i("Store online first failed, trying to store locally version");
			}
		}
		else {
			online(args);
		}
	}

	protected abstract void online(Object... args) throws Exception;

	protected abstract void storeToCacheAndLaunchEvent(Object... args);

	private final OfflinePolicy _offlinePolicy;

}
