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

package com.liferay.mobile.screens.imagegallery.view;

import android.net.Uri;
import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;

/**
 * @author Víctor Galán Grande
 */
public interface ImageGalleryViewModel extends BaseListViewModel<ImageEntry> {

    /**
     * Deletes an {@link ImageEntry} with its `imageEntryId`.
     */
    void deleteImage(long imageEntryId);

    /**
     * Adds an {@link ImageEntry}.
     */
    void addImage(ImageEntry imageEntry);

    /**
     * Called when an {@link ImageEntry} is prepared for upload.
     */
    void imageUploadStart(Uri pictureUri);

    /**
     * Retrieves constantly the progress until the picture is successfully uploaded.
     * This method retrieves the bytes sent and the total.
     */
    void imageUploadProgress(int bytesSent, int totalBytes);

    /**
     * Called when the {@link ImageEntry} was successfully uploaded.
     */
    void imageUploadComplete();
}
