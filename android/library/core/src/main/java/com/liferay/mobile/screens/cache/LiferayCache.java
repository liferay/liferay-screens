package com.liferay.mobile.screens.cache;

import java.util.List;

public interface LiferayCache<E extends CachedContent> {

	E recover(CachedType cachedType, String key);

	void store(List<E> objects);

	void store(E object);

	boolean hasCachedContents(CachedType cachedType, String id);
}
