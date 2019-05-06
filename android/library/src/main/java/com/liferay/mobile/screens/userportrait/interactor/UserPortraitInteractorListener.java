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

package com.liferay.mobile.screens.userportrait.interactor;

import android.graphics.Bitmap;
import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 * @author Jose Manuel Navarro
 */
public interface UserPortraitInteractorListener extends BaseCacheListener {

    /**
     * Called when the user portrait has been successfully loaded.
     *
     * @param bitmap original user portrait
     * @return modified or not user portrait
     */
    Bitmap onEndUserPortraitLoadRequest(Bitmap bitmap);

    /**
     * Called when the user portrait upload service finishes.
     */
    void onUserPortraitUploaded(Long userId);

    /**
     * Called when the user doesn't have a portrait
     */
    void onUserWithoutPortrait(User user);
}