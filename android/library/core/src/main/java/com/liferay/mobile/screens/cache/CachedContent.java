package com.liferay.mobile.screens.cache;

import com.liferay.mobile.screens.cache.tablecache.TableCache;

/**
 * @author Javier Gamarra
 */
public interface CachedContent {

	CachedType getCachedType();

	String getId();

	TableCache getTableCache();
}
