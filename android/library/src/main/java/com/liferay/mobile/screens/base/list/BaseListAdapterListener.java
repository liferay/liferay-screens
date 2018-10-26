/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.base.list;

import android.view.View;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public interface BaseListAdapterListener {

    /**
     * Called when row is null and it's necessary load another list page.
     */
    void onPageNotFound(int row);

    /**
     * Called this methid when you want to implement some behavior
     * when an item is clicked.
     */
    void onItemClick(int position, View view);
}