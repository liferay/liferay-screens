package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedRemoteInteractor<L, E extends BasicEvent> extends BaseRemoteInteractor<L> {

	public BaseCachedRemoteInteractor(int targetScreenletId, CachePolicy cachePolicy, OfflinePolicy offlinePolicy) {
		super(targetScreenletId);

		_retrievedFromCache = false;
		_cachePolicy = cachePolicy;
		_offlinePolicy = offlinePolicy;
	}

	protected void loadWithCache(CacheCallback callback) throws Exception {
		if (_cachePolicy == CachePolicy.CACHE_FIRST) {
			try {
				_retrievedFromCache = callback.retrieveFromCache();
			}
			catch (Exception e) {

				if (!_retrievedFromCache) {
					LiferayLogger.i("Retrieve from cache first failed, trying online");

					callback.loadOnline();
				}
			}
		}
		else if (_cachePolicy == CachePolicy.CACHE_ONLY) {
			LiferayLogger.i("Trying to retrieve object from cache");

			_retrievedFromCache = callback.retrieveFromCache();
		}
		else if (_cachePolicy == CachePolicy.ONLINE_FIRST) {
			try {
				callback.loadOnline();
			}
			catch (Exception e) {
				LiferayLogger.i("Retrieve online first failed, trying cached version");

				_retrievedFromCache = callback.retrieveFromCache();
			}
		}
		else {
			callback.loadOnline();
		}
	}

	protected void storeOnError(OfflineCallback callback) throws Exception {
		if (_offlinePolicy == OfflinePolicy.STORE_ON_ERROR) {
			try {
				callback.sendOnline();
			}
			catch (Exception e) {
				LiferayLogger.i("Store online first failed, trying to store locally version");
				callback.storeToCache();
			}
		}
		else {
			callback.sendOnline();
		}
	}

//	protected void processCachedResponse(E event) {
//		if (event.isFailed()) {
//			if (CachePolicy.ONLINE_FIRST.equals(_cachePolicy)) {
//
//				_retrievedFromCache = retrieveFromCache(event);
//
//				if (!_retrievedFromCache) {
//
//					notifyListenerError(event);
//				}
//			}
//			else {
//
//				notifyListenerError(event);
//			}
//		}
//		else {
//			if (hasToStoreToCache()) {
//				store(event);
//			}
//
//			notifyListenerSuccess(event);
//		}
//	}
//
//	protected boolean hasToStoreToCache() {
//		return !_retrievedFromCache && !_cachePolicy.equals(CachePolicy.NO_CACHE);
//	}
//
//	protected abstract void store(E event);

	protected CachePolicy getCachePolicy() {
		return _cachePolicy;
	}

	private final CachePolicy _cachePolicy;
	private final OfflinePolicy _offlinePolicy;
	private boolean _retrievedFromCache;

}
