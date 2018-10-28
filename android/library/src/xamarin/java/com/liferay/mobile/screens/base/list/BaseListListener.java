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

package com.liferay.mobile.screens.base.list;

import android.view.View;
import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListListener extends BaseCacheListener {

    /**
     * Called when the call to retrieve a page of items fails.
     *
     * @param e exception
     */
    void onListPageFailed(int startRow, Exception e);

    /**
     * Called when the call to retrieve a page of items succeed.
     */
    void onListPageReceived(int startRow, int endRow, List entries, int rowCount);

    /**
     * Called when a list item is selected.
     */
    void onListItemSelected(Object element, View view);
}
