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

package com.liferay.mobile.screens.asset.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.interactor.AssetListInteractor;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class AssetListScreenlet extends BaseListScreenlet<AssetEntry, AssetListInteractor>
    implements BaseListInteractorListener<AssetEntry> {

    private long classNameId;
    private String portletItemName;
    private HashMap<String, Object> customEntryQuery = new HashMap<>();

    public AssetListScreenlet(Context context) {
        super(context);
    }

    public AssetListScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AssetListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AssetListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public long getClassNameId() {
        return classNameId;
    }

    public void setClassNameId(long classNameId) {
        this.classNameId = classNameId;
    }

    public String getPortletItemName() {
        return portletItemName;
    }

    public void setPortletItemName(String portletItemName) {
        this.portletItemName = portletItemName;
    }

    public Map<String, Object> getCustomEntryQuery() {
        return customEntryQuery;
    }

    public void setCustomEntryQuery(HashMap<String, Object> customEntryQuery) {
        this.customEntryQuery = customEntryQuery;
    }

    @Override
    protected void loadRows(AssetListInteractor interactor) {
        interactor.start(classNameId, customEntryQuery, portletItemName);
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.AssetListScreenlet, 0, 0);

        classNameId = castToLong(typedArray.getString(R.styleable.AssetListScreenlet_classNameId));
        portletItemName = typedArray.getString(R.styleable.AssetListScreenlet_portletItemName);

        typedArray.recycle();

        return super.createScreenletView(context, attributes);
    }

    @Override
    public void error(Exception e, String userAction) {
        if (getListener() != null) {
            getListener().error(e, userAction);
        }
    }

    @Override
    protected AssetListInteractor createInteractor(String actionName) {
        return new AssetListInteractor();
    }
}