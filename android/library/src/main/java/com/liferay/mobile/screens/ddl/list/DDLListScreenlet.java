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

package com.liferay.mobile.screens.ddl.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractor;
import com.liferay.mobile.screens.ddl.model.Record;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListScreenlet extends BaseListScreenlet<Record, DDLListInteractor>
    implements BaseListInteractorListener<Record> {

    private long recordSetId;

    public DDLListScreenlet(Context context) {
        super(context);
    }

    public DDLListScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DDLListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DDLListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public long getRecordSetId() {
        return recordSetId;
    }

    public void setRecordSetId(long recordSetId) {
        this.recordSetId = recordSetId;
    }

    @Override
    public void error(Exception e, String userAction) {
        if (getListener() != null) {
            getListener().error(e, userAction);
        }
    }

    @Override
    protected void loadRows(DDLListInteractor interactor) {
        interactor.start(recordSetId, userId);
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.DDLListScreenlet, 0, 0);
        recordSetId = castToLong(typedArray.getString(R.styleable.DDLListScreenlet_recordSetId));
        typedArray.recycle();

        return super.createScreenletView(context, attributes);
    }

    @Override
    protected DDLListInteractor createInteractor(String actionName) {
        return new DDLListInteractor();
    }
}