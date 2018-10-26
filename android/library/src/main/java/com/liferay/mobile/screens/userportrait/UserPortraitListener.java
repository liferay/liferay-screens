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

package com.liferay.mobile.screens.userportrait;

import android.graphics.Bitmap;
import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public interface UserPortraitListener extends BaseCacheListener {

    /**
     * Called when an image is received from the server. You can then apply
     * image filters (grayscale, for example) and return the new image.
     * You can return `null` or the original image supplied as the argument
     * if you don’t want to modify it.
     *
     * @param bitmap original user portrait
     * @return modified or not user portrait
     */
    Bitmap onUserPortraitLoadReceived(Bitmap bitmap);

    /**
     * Called when the user portrait upload service finishes.
     */
    void onUserPortraitUploaded();
}