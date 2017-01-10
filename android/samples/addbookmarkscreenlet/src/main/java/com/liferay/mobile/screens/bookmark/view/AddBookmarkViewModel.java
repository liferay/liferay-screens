/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.bookmark.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;

/**
 * @author Javier Gamarra
 */
public interface AddBookmarkViewModel extends BaseViewModel {

	/**
	 * Gets the bookmark URL.
	 *
	 * @return bookmark URL
	 */
	String getURL();

	/**
	 * Sets the bookmark URL.
	 *
	 * @param value bookmark URL
	 */
	void setURL(String value);

	/**
	 * Gets the bookmark title.
	 *
	 * @return bookmark title
	 */
	String getTitle();

	/**
	 * Sets the bookmark title.
	 *
	 * @param value bookmark title
	 */
	void setTitle(String value);
}
