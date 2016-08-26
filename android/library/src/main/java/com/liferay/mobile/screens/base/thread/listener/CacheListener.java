package com.liferay.mobile.screens.base.thread.listener;

public interface CacheListener {

	void loadingFromCache(boolean success);

	void retrievingOnline(boolean triedInCache, Exception e);

	void storingToCache(Object object);
}
