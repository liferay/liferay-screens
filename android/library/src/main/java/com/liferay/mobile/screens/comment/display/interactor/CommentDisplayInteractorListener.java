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

package com.liferay.mobile.screens.comment.display.interactor;

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.comment.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public interface CommentDisplayInteractorListener extends BaseCacheListener {

    /**
     * Called when the screenlet successfully loads the comment.
     */
    void onLoadCommentSuccess(CommentEntry commentEntry);

    /**
     * Called when the screenlet successfully deletes the comment.
     */
    void onDeleteCommentSuccess();

    /**
     * Called when the screenlet successfully updates the comment.
     */
    void onUpdateCommentSuccess(CommentEntry commentEntry);
}
