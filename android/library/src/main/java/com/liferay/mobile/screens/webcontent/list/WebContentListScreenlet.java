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

package com.liferay.mobile.screens.webcontent.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.list.interactor.WebContentListInteractor;

/**
 * @author Javier Gamarra
 */
public class WebContentListScreenlet extends BaseListScreenlet<WebContent, WebContentListInteractor>
    implements BaseListInteractorListener<WebContent> {

    private long folderId;

    public WebContentListScreenlet(Context context) {
        super(context);
    }

    public WebContentListScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebContentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WebContentListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void error(Exception e, String userAction) {
        if (getListener() != null) {
            getListener().error(e, userAction);
        }
    }

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    @Override
    protected void loadRows(WebContentListInteractor interactor) {
        interactor.start(folderId);
    }

    @Override
    protected WebContentListInteractor createInteractor(String actionName) {
        return new WebContentListInteractor();
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.WebContentListScreenlet, 0, 0);

        folderId = castToLong(typedArray.getString(R.styleable.WebContentListScreenlet_folderId));

        typedArray.recycle();

        return super.createScreenletView(context, attributes);
    }
}
