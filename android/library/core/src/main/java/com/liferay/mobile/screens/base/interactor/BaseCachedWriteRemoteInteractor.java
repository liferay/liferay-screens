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

	protected void loadWithCache(Object... args) throws Exception {
		if (_offlinePolicy == OfflinePolicy.STORE_ON_ERROR) {
			try {
				online(args);
			}
			catch (Exception e) {
				LiferayLogger.i("Store online first failed, trying to store locally version");
				storeToCache(args);
			}
		}
		else {
			online(args);
		}
	}

	protected void onEventWithCache(E event, Object... args) {
		if (event.isFailed()) {
			try {
				storeToCache(args);
				notifySuccess(event);
			}
			catch (Exception e) {
				notifyError(event);
			}
		}
		else {
			notifySuccess(event);
		}
	}

	protected abstract void online(Object... args) throws Exception;

	protected abstract void notifySuccess(E event);

	protected abstract void notifyError(E event);

	protected abstract void storeToCache(Object... args);

	private final OfflinePolicy _offlinePolicy;

}
