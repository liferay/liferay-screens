package com.liferay.mobile.screens.base.interactor;

import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.util.LiferayLogger;

import java.util.NoSuchElementException;

/**
 * @author Javier Gamarra
 */
public abstract class BaseCachedReadRemoteInteractor<L, E extends BasicEvent> extends BaseRemoteInteractor<L> {

	public BaseCachedReadRemoteInteractor(int targetScreenletId, CachePolicy cachePolicy) {
		super(targetScreenletId);

		_retrievedFromCache = false;
		_cachePolicy = cachePolicy;
	}

	protected void loadWithCache(Object... args) throws Exception {
		if (_cachePolicy == CachePolicy.CACHE_FIRST) {
			try {
				_retrievedFromCache = getFromCache(args);

				if (!_retrievedFromCache) {
					LiferayLogger.i("Retrieve from cache first failed, trying online");
					loadOnline(args);
				}
			}
			catch (Exception e) {
				LiferayLogger.i("Retrieve from cache first failed, trying online");
				loadOnline(args);
			}
		}
		else if (_cachePolicy == CachePolicy.CACHE_ONLY) {
			LiferayLogger.i("Trying to retrieve object from cache");

			_retrievedFromCache = getFromCache(args);
			if (!_retrievedFromCache) {
				throw new NoSuchElementException();
			}
		}
		else if (_cachePolicy == CachePolicy.ONLINE_FIRST) {
			try {
				loadOnline(args);
			}
			catch (Exception e) {
				LiferayLogger.i("Retrieve online first failed, trying cached version");

				_retrievedFromCache = getFromCache(args);

				if (!_retrievedFromCache) {
					throw new NoSuchElementException();
				}
			}
		}
		else {
			loadOnline(args);
		}
	}

	protected void onEventWithCache(E event, Object... args) {
		if (event.isFailed()) {
			if (CachePolicy.ONLINE_FIRST.equals(_cachePolicy)) {
				try {
					_retrievedFromCache = getFromCache(args);
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

	protected abstract void loadOnline(Object... args) throws Exception;

	protected abstract void notifyError(E event);

	protected abstract boolean getFromCache(Object... args) throws Exception;

	protected abstract void storeToCache(E event, Object... args);

	protected boolean hasToStoreToCache() {
		return !_retrievedFromCache && !_cachePolicy.equals(CachePolicy.NO_CACHE);
	}

	protected CachePolicy getCachePolicy() {
		return _cachePolicy;
	}

	private boolean _retrievedFromCache;
	private final CachePolicy _cachePolicy;

}
