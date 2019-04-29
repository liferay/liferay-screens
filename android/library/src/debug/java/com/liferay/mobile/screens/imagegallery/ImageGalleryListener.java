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

package com.liferay.mobile.screens.imagegallery;

import android.net.Uri;
import androidx.annotation.LayoutRes;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.imagegallery.model.ImageEntry;
import com.liferay.mobile.screens.viewsets.defaultviews.imagegallery.DefaultUploadDetailView;

/**
 * @author Víctor Galán Grande
 */
public interface ImageGalleryListener extends BaseListListener<ImageEntry> {

    /**
     * Called when an item in the list is deleted.
     */
    void onImageEntryDeleted(long imageEntryId);

    /**
     * Called when an item is prepared for upload.
     */
    void onImageUploadStarted(Uri pictureUri, String title, String description, String changelog);

    /**
     * Called when an item is uploading.
     */
    void onImageUploadProgress(int totalBytes, int totalBytesSent);

    /**
     * Called when an item finishes uploading.
     */
    void onImageUploadEnd(ImageEntry entry);

    /**
     * Called when the View for uploading an image is instantiated.
     * The default behavior is to show the default view in a dialog.
     * To retain this behavior, all this method needs to do is return `false`.
     * To change the default behavior, use the {@link DefaultUploadDetailView#initializeUploadView}
     * method to initialize a custom View that extends {@link BaseDetailUploadView}.
     * Then return `true` to prevent the screenlet from executing the default behavior.
     *
     * @return boolean
     */
    boolean showUploadImageView(String actionName, Uri pictureUri, int screenletId);

    /**
     * Called when the screenlet provides the image upload View.
     * To inflate the default view, return `0` in this method.
     * Alternatively, display this View with a custom layout by returning its layout ID.
     * Such a layout must have {@link DefaultUploadDetailView} as its root class.
     *
     * @return layout ID or `0` to inflate the default view.
     */
    @LayoutRes
    int provideImageUploadDetailView();
}
