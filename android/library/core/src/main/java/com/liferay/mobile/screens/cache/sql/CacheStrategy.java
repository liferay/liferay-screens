package com.liferay.mobile.screens.cache.sql;

import com.liferay.mobile.screens.cache.CachedContent;

import java.util.List;
import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public interface CacheStrategy<E extends CachedContent> {

	E getById(String id, Long groupId, Long userId, Locale locale);

	E getById(String id);

	List<E> get(String query, Object[] args);

	Object set(E object);

	void clear();

	void clear(String id);
}