package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedWriteRemoteInteractor<L, E extends BasicEvent> extends BaseRemoteInteractor<L> {

	public BaseCachedWriteRemoteInteractor(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId);

		_offlinePolicy = offlinePolicy;
	}

	protected void storeOnError(Object... args) throws Exception {
		if (_offlinePolicy == OfflinePolicy.STORE_ON_ERROR) {
			try {
				sendOnline(args);
			}
			catch (Exception e) {
				LiferayLogger.i("Store online first failed, trying to store locally version");
				storeToCache(args);
			}
		}
		else {
			sendOnline(args);
		}
	}

	protected abstract void sendOnline(Object... args) throws Exception;

	protected abstract void storeToCache(Object... args);

	private final OfflinePolicy _offlinePolicy;

}
