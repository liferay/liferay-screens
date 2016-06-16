package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.cache.CacheListener;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.util.NoSuchElementException;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedRemoteInteractor<L extends CacheListener, E extends BasicEvent>
	extends BaseRemoteInteractor<L> {

	public BaseCachedRemoteInteractor(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId);

		_retrievedFromCache = false;
		_offlinePolicy = offlinePolicy;
	}

	protected void processWithCache(Object... args) throws Exception {
		if (_offlinePolicy == OfflinePolicy.CACHE_FIRST) {
			try {
				_retrievedFromCache = cached(args);

				getListener().loadingFromCache(_retrievedFromCache);

				if (!_retrievedFromCache) {
					LiferayLogger.i("Retrieve from cache first failed, trying online");

					getListener().retrievingOnline(true, null);
					online(args);
				}
			}
			catch (Exception e) {
				LiferayLogger.e("Retrieve from cache first failed, trying online", e);

				getListener().retrievingOnline(true, e);
				online(args);
			}
		}
		else if (_offlinePolicy == OfflinePolicy.CACHE_ONLY) {
			LiferayLogger.i("Trying to retrieve object from cache");

			_retrievedFromCache = cached(args);

			getListener().loadingFromCache(_retrievedFromCache);

			if (!_retrievedFromCache) {
				throw new NoSuchElementException();
			}
		}
		else if (_offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
			try {

				getListener().retrievingOnline(false, null);

				online(args);
			}
			catch (Exception e) {
				LiferayLogger.e("Retrieve online first failed, trying cached version", e);

				_retrievedFromCache = cached(args);

				getListener().loadingFromCache(_retrievedFromCache);

				if (!_retrievedFromCache) {
					throw new NoSuchElementException();
				}
			}
		}
		else {

			getListener().retrievingOnline(false, null);

			online(args);
		}
	}

	protected void onEventWithCache(E event, Object... args) {
		if (event.isFailed()) {
			if (OfflinePolicy.REMOTE_FIRST.equals(_offlinePolicy)) {
				try {
					_retrievedFromCache = cached(args);

					getListener().loadingFromCache(_retrievedFromCache);

					if (!_retrievedFromCache) {
						notifyError(event);
					}
				}
				catch (Exception e) {
					notifyError(event);
				}
			}
			else {
				notifyError(event);
			}
		}
		else if (hasToStoreToCache()) {

			getListener().storingToCache(event);

			storeToCache(event, args);
		}
	}

	protected abstract void online(Object... args) throws Exception;

	protected abstract boolean cached(Object... args) throws Exception;

	protected abstract void storeToCache(E event, Object... args);

	protected abstract void notifyError(E event);

	protected OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	protected boolean hasToStoreToCache() {
		return !_retrievedFromCache;
	}

	private boolean _retrievedFromCache;
	private final OfflinePolicy _offlinePolicy;

}
