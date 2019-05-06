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

package com.liferay.mobile.screens.dlfile.display.video;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.dlfile.display.BaseFileDisplayScreenlet;

/**
 * @author Sarai Díaz García
 */
public class VideoDisplayScreenlet extends BaseFileDisplayScreenlet implements VideoDisplayListener {

    public VideoDisplayScreenlet(Context context) {
        super(context);
    }

    public VideoDisplayScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VideoDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onVideoPrepared() {
        if (listener != null) {
            getListener().onVideoPrepared();
        }
    }

    @Override
    public void onVideoError(Exception e) {
        if (listener != null) {
            getListener().onVideoError(e);
        }
    }

    @Override
    public void onVideoCompleted() {
        if (listener != null) {
            getListener().onVideoCompleted();
        }
    }

    public VideoDisplayListener getListener() {
        return (VideoDisplayListener) listener;
    }

    public void setListener(VideoDisplayListener listener) {
        this.listener = listener;
    }
}
