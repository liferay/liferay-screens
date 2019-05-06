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

package com.liferay.mobile.screens.base.view;

import com.liferay.mobile.screens.base.BaseScreenlet;

public interface BaseViewModel {

    /**
     * Called when the operation is going to started.
     */
    void showStartOperation(String actionName);

    /**
     * Called when the operation has been finished.
     */
    void showFinishOperation(String actionName);

    /**
     * Called when the operation has been failed.
     *
     * @param e exception
     */
    void showFailedOperation(String actionName, Exception e);

    /**
     * Gets the screenlet.
     *
     * @return screenlet
     */
    BaseScreenlet getScreenlet();

    /**
     * Sets the screenlet.
     */
    void setScreenlet(BaseScreenlet screenlet);
}