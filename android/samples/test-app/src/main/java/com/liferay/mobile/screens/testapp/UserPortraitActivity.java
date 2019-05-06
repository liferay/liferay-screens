/*
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

package com.liferay.mobile.screens.testapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import com.liferay.mobile.screens.base.interactor.listener.CacheListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.userportrait.UserPortraitListener;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public class UserPortraitActivity extends ThemeActivity implements UserPortraitListener, CacheListener {

    private UserPortraitScreenlet screenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_portrait);

        screenlet = findViewById(R.id.user_portrait_screenlet);
        screenlet.setUserId(SessionContext.getUserId());
        screenlet.setListener(this);
        screenlet.setCacheListener(this);
        screenlet.loadLoggedUserPortrait();
    }

    @Override
    public Bitmap onUserPortraitLoadReceived(Bitmap bitmap) {
        info(getString(R.string.user_portrait_info));
        LiferayLogger.i("Bitmap received with width " + bitmap.getWidth() + " and height " + bitmap.getHeight());
        return null;
    }

    @Override
    public void onUserPortraitUploaded() {

    }

    @Override
    public void loadingFromCache(boolean success) {
        View content = findViewById(android.R.id.content);
        Snackbar.make(content, getString(R.string.loading_cache_info) + " " + success, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void retrievingOnline(boolean triedInCache, Exception e) {

    }

    @Override
    public void storingToCache(Object object) {
        View content = findViewById(android.R.id.content);
        Snackbar.make(content, getString(R.string.storing_cache_info), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void error(Exception e, String userAction) {
        error(getString(R.string.user_portrait_error), e);
    }
}
