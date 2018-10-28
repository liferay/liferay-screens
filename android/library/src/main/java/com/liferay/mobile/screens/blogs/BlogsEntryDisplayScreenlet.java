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

package com.liferay.mobile.screens.blogs;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.interactor.AssetDisplayInteractor;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Sarai Díaz García
 */

public class BlogsEntryDisplayScreenlet extends BaseScreenlet<BlogsEntryDisplayViewModel, AssetDisplayInteractor>
    implements AssetDisplayListener {

    public static final String LOAD_BLOGS_ACTION = "LOAD_BLOGS_ACTION";
    private long entryId;
    private String className;
    private long classPK;
    private boolean autoLoad;
    private AssetDisplayListener listener;
    private BlogsEntry blogsEntry;

    public BlogsEntryDisplayScreenlet(Context context) {
        super(context);
    }

    public BlogsEntryDisplayScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlogsEntryDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BlogsEntryDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Searches the {@link BlogsEntry} with the given attributes ({@link #entryId} or {@link #className}
     * and {@link #classPK}) and loads it in the screenlet.
     */
    public void load() {
        performUserAction(LOAD_BLOGS_ACTION);
    }

    /**
     * Loads the {@link BlogsEntry} directly in the screenlet.
     */
    public void loadBlogsEntry() {
        onRetrieveAssetSuccess(blogsEntry);
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.BlogsEntryDisplayScreenlet, 0, 0);

        int layoutId = typedArray.getResourceId(R.styleable.BlogsEntryDisplayScreenlet_layoutId, getDefaultLayoutId());

        autoLoad = typedArray.getBoolean(R.styleable.BlogsEntryDisplayScreenlet_autoLoad, true);
        entryId = typedArray.getInt(R.styleable.BlogsEntryDisplayScreenlet_entryId, 0);

        className = typedArray.getString(R.styleable.BlogsEntryDisplayScreenlet_className);
        classPK = typedArray.getInt(R.styleable.BlogsEntryDisplayScreenlet_classPK, 0);

        View view = LayoutInflater.from(context).inflate(layoutId, null);

        typedArray.recycle();

        return view;
    }

    @Override
    protected AssetDisplayInteractor createInteractor(String actionName) {
        return new AssetDisplayInteractor();
    }

    @Override
    protected void onUserAction(String userActionName, AssetDisplayInteractor interactor, Object... args) {
        if (entryId != 0) {
            interactor.start(entryId);
        } else {
            interactor.start(className, classPK);
        }
    }

    @Override
    public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
        blogsEntry = (BlogsEntry) assetEntry;

        getViewModel().showFinishOperation(blogsEntry);

        if (listener != null) {
            listener.onRetrieveAssetSuccess(assetEntry);
        }
    }

    @Override
    public void error(Exception e, String userAction) {
        getViewModel().showFailedOperation(userAction, e);

        if (listener != null) {
            listener.error(e, userAction);
        }
    }

    @Override
    protected void onScreenletAttached() {
        super.onScreenletAttached();

        if (autoLoad) {
            autoLoad();
        }
    }

    /**
     * Checks if there is a session created and if exists {@link #entryId} or {@link #className}
     * and {@link #classPK} attributes and then calls {@link #load()} method. If the previous condition
     * is not true, {@link #loadBlogsEntry()} is called.
     */
    protected void autoLoad() {
        if (SessionContext.isLoggedIn() && (entryId != 0 || (className != null && classPK != 0))) {
            load();
        } else if (blogsEntry != null) {
            loadBlogsEntry();
        }
    }

    public long getEntryId() {
        return entryId;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
    }

    public BlogsEntry getBlogsEntry() {
        return blogsEntry;
    }

    public void setBlogsEntry(BlogsEntry blogsEntry) {
        this.blogsEntry = blogsEntry;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getClassPK() {
        return classPK;
    }

    public void setClassPK(long classPK) {
        this.classPK = classPK;
    }

    public boolean getAutoLoad() {
        return autoLoad;
    }

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public AssetDisplayListener getListener() {
        return listener;
    }

    public void setListener(AssetDisplayListener listener) {
        this.listener = listener;
    }
}
