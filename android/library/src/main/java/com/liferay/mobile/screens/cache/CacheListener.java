package com.liferay.mobile.screens.cache;

/**
 * @author Javier Gamarra
 */
public interface CacheListener {

	void loadingFromCache(boolean success);

	void retrievingOnline(boolean triedInCache, Exception e);

	void storingToCache(Object object);
}
