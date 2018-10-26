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

package com.liferay.mobile.screens.listbookmark;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;

/**
 * @author Javier Gamarra
 */
public class BookmarkListScreenlet extends BaseListScreenlet<Bookmark, BookmarkListInteractor> {

    private long folderId;

    public BookmarkListScreenlet(Context context) {
        super(context);
    }

    public BookmarkListScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BookmarkListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BookmarkListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void error(Exception e, String userAction) {
        if (getListener() != null) {
            getListener().error(e, userAction);
        }
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.BookmarkListScreenlet, 0, 0);
        groupId = typedArray.getInt(R.styleable.BookmarkListScreenlet_groupId, (int) LiferayServerContext.getGroupId());
        folderId = typedArray.getInt(R.styleable.BookmarkListScreenlet_folderId, 0);
        typedArray.recycle();

        return super.createScreenletView(context, attributes);
    }

    @Override
    protected void loadRows(BookmarkListInteractor interactor) {

        ((BookmarkListListener) getListener()).interactorCalled();

        interactor.start(folderId, obcClassName);
    }

    @Override
    protected BookmarkListInteractor createInteractor(String actionName) {
        return new BookmarkListInteractor();
    }
}
