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

package com.liferay.mobile.screens.base.list.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListViewModel<E> extends BaseViewModel {

    /**
     * Called this method when the request has been finished successfully.
     *
     * @param startRow first position
     * @param endRow last position
     * @param entries list of entries
     * @param rowCount total count
     */
    void showFinishOperation(int startRow, int endRow, List<E> entries, int rowCount);

    /**
     * Called this method when the request has been failed.
     *
     * @param startRow first position
     * @param endRow last position
     * @param e exception
     */
    void showFinishOperation(int startRow, int endRow, Exception e);
}
