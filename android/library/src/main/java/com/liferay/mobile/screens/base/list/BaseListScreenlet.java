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

package com.liferay.mobile.screens.base.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Santos
 */
public abstract class BaseListScreenlet<E, N extends BaseListInteractor> extends BaseScreenlet<BaseListViewModel, N>
    implements BaseListInteractorListener<E> {

    public static final String LOAD_INITIAL_PAGE_ACTION = "LOAD_INITIAL_PAGE_ACTION";
    protected static final int FIRST_PAGE_SIZE = 50;
    protected static final int PAGE_SIZE = 25;
    protected boolean autoLoad;
    protected int firstPageSize;
    protected BaseListListener listener;
    protected int pageSize;
    protected String obcClassName;
    private List<String> labelFields;

    public BaseListScreenlet(Context context) {
        super(context);
    }

    public BaseListScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Loads the associate page from given row.
     */
    public void loadPageForRow(int row) {
        loadPage(getPageFromRow(row));
    }

    @Override
    public void onListRowsFailure(int startRow, int endRow, Exception e) {
        getViewModel().showFinishOperation(startRow, endRow, e);

        if (listener != null) {
            listener.onListPageFailed(startRow, e);
        }
    }

    @Override
    public void onListRowsReceived(int startRow, int endRow, List<E> entries, int rowCount) {
        getViewModel().showFinishOperation(startRow, endRow, entries, rowCount);

        if (listener != null) {
            listener.onListPageReceived(startRow, endRow, entries, rowCount);
        }
    }

    /**
     * Returns first row of the given page.
     *
     * @param page chosen page.
     * @return first row.
     */
    public int getFirstRowForPage(int page) {
        if (page == 0) {
            return 0;
        }

        return (firstPageSize + (page - 1) * pageSize);
    }

    /**
     * Returns the page where the row is from.
     *
     * @return row page.
     */
    public int getPageFromRow(int row) {
        if (row < firstPageSize) {
            return 0;
        }

        return ((row - firstPageSize) / pageSize) + 1;
    }

    /**
     * Loads the given page. If the page is 0, it loads the initial page.
     */
    public void loadPage(int page) {
        if (page == 0) {
            getViewModel().showStartOperation(LOAD_INITIAL_PAGE_ACTION);
        }

        int startRow = getFirstRowForPage(page);
        int endRow = getFirstRowForPage(page + 1);

        try {
            N interactor = getInteractor();
            Query query = new Query(startRow, endRow, obcClassName);
            interactor.setQuery(query);
            loadRows(interactor);
        } catch (Exception e) {
            onListRowsFailure(startRow, endRow, e);
        }
    }

    public boolean isAutoLoad() {
        return autoLoad;
    }

    public void setAutoLoad(boolean autoLoad) {
        this.autoLoad = autoLoad;
    }

    public int getFirstPageSize() {
        return firstPageSize;
    }

    public void setFirstPageSize(int firstPageSize) {
        this.firstPageSize = firstPageSize;
    }

    public BaseListListener getListener() {
        return listener;
    }

    public void setListener(BaseListListener listener) {
        this.listener = listener;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getLabelFields() {
        return labelFields;
    }

    public void setLabelFields(List<String> labelFields) {
        this.labelFields = labelFields;
    }

    protected abstract void loadRows(N interactor);

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {

        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.AssetListScreenlet, 0, 0);

        int layoutId = typedArray.getResourceId(R.styleable.AssetListScreenlet_layoutId, getDefaultLayoutId());

        firstPageSize = typedArray.getInteger(R.styleable.AssetListScreenlet_firstPageSize, FIRST_PAGE_SIZE);

        pageSize = typedArray.getInteger(R.styleable.AssetListScreenlet_pageSize, PAGE_SIZE);

        autoLoad = typedArray.getBoolean(R.styleable.AssetListScreenlet_autoLoad, true);

        labelFields = parse(typedArray.getString(R.styleable.AssetListScreenlet_labelFields));

        obcClassName = typedArray.getString(R.styleable.AssetListScreenlet_obcClassName);

        typedArray.recycle();

        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    @Override
    protected void onScreenletAttached() {
        super.onScreenletAttached();

        if (autoLoad) {
            //LMW-176 TODO handle when first page is already loaded
            loadPage(0);
        }
    }

    @Override
    protected void onUserAction(String userActionName, N interactor, Object... args) {
    }

    private List<String> parse(String labelFields) {
        if (labelFields == null) {
            LiferayLogger.e("No labelFields configured");
            labelFields = "";
        }

        List<String> parsedFields = new ArrayList<>();
        for (String text : labelFields.split(",")) {
            parsedFields.add(text.trim());
        }
        return parsedFields;
    }
}
