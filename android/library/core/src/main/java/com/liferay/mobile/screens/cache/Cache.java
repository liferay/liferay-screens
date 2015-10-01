package com.liferay.mobile.screens.cache;

import android.content.Context;

import java.util.List;
import java.util.Locale;

public interface Cache<E> {

	List<E> get(CachedType cachedType, String query, Object... args);

	E getById(CachedType cachedType, String id);

	E getById(CachedType cachedType, String id, Long groupId, Long userId, Locale locale);

	void set(E object);

	void clear(CachedType cachedType);

	void clear(CachedType cachedType, String id);

	boolean clear(Context context);

	void resync();

}