package com.liferay.mobile.screens.cache.sql;

import com.liferay.mobile.screens.cache.CachedContent;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface CacheStrategy<E extends CachedContent> {

	E getById(String id);

	List<E> get(String query, Object[] args);

	Object set(E object);

	void clear();

	void clear(String id);
}