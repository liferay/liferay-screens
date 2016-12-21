/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.base.interactor.listener;

public interface CacheListener {

	/**
	 * Call this method if you want to load the content from cache.
	 *
	 * @param success
	 */
	void loadingFromCache(boolean success);

	/**
	 * Call this method if you want to retrieve the content throught internet conection.
	 *
	 * @param triedInCache true if you want to try first from cache
	 * @param e exception
	 */
	void retrievingOnline(boolean triedInCache, Exception e);

	/**
	 * Call this method if you want to store any {@link Object} to cache.
	 *
	 * @param object
	 */
	void storingToCache(Object object);
}
