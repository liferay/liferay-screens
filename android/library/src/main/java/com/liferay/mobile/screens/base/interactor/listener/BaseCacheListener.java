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

package com.liferay.mobile.screens.base.interactor.listener;

/**
 * @author Javier Gamarra
 */
public interface BaseCacheListener {

    /**
     * Catch the exception and the user action if there was an error.
     *
     * @param e exception
     */
    //TODO Error propagating event instead of the exception
    void error(Exception e, String userAction);
}
