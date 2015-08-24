package com.liferay.mobile.screens.cache;

import java.util.List;

public interface Cache<E> {

	List<E> get(CachedType cachedType, String query, Object... args);

	E getById(CachedType cachedType, String id);

	void set(E object);

	void clear();

	void clear(CachedType cachedType);

	void clear(CachedType cachedType, String id);

}