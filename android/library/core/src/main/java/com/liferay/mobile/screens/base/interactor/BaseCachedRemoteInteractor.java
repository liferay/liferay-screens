package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.util.NoSuchElementException;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedRemoteInteractor<L, E extends BasicEvent> extends BaseRemoteInteractor<L> {

	public BaseCachedRemoteInteractor(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId);

		_retrievedFromCache = false;
		_offlinePolicy = offlinePolicy;
	}

	protected void processWithCache(Object... args) throws Exception {
		if (_offlinePolicy == OfflinePolicy.CACHE_FIRST) {
			try {
				_retrievedFromCache = cached(args);

				if (!_retrievedFromCache) {
					LiferayLogger.i("Retrieve from cache first failed, trying online");
					online(args);
				}
			}
			catch (Exception e) {
				LiferayLogger.e("Retrieve from cache first failed, trying online", e);
				online(args);
			}
		}
		else if (_offlinePolicy == OfflinePolicy.CACHE_ONLY) {
			LiferayLogger.i("Trying to retrieve object from cache");

			_retrievedFromCache = cached(args);
			if (!_retrievedFromCache) {
				throw new NoSuchElementException();
			}
		}
		else if (_offlinePolicy == OfflinePolicy.REMOTE_FIRST) {
			try {
				online(args);
			}
			catch (Exception e) {
				LiferayLogger.e("Retrieve online first failed, trying cached version", e);

				_retrievedFromCache = cached(args);

				if (!_retrievedFromCache) {
					throw new NoSuchElementException();
				}
			}
		}
		else {
			online(args);
		}
	}

	protected void onEventWithCache(E event, Object... args) {
		if (event.isFailed()) {
			if (OfflinePolicy.REMOTE_FIRST.equals(_offlinePolicy)) {
				try {
					_retrievedFromCache = cached(args);
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
			storeToCache(event, args);
		}
	}

	protected abstract void online(Object... args) throws Exception;

	protected abstract void notifyError(E event);

	protected abstract boolean cached(Object... args) throws Exception;

	protected abstract void storeToCache(E event, Object... args);

	protected boolean hasToStoreToCache() {
		return !_retrievedFromCache && !_offlinePolicy.equals(OfflinePolicy.REMOTE_ONLY);
	}

	protected OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	private boolean _retrievedFromCache;
	private final OfflinePolicy _offlinePolicy;

}
