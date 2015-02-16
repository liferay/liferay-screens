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

import android.util.Pair;

import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorBatchAsyncTaskCallback;

/**
 * @author Silvio Santos
 */
public abstract class ListCallback<E>
        extends InteractorBatchAsyncTaskCallback<ListResult<E>> {

    public ListCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange) {
        super(targetScreenletId);

        _rowsRange = rowsRange;
    }

    @Override
    public abstract ListResult<E> transform(Object obj) throws Exception;

    @Override
    public void onSuccess(ListResult<E> result) {
        cleanRequestState();

        super.onSuccess(result);
    }

    @Override
    public void onFailure(Exception e) {
        cleanRequestState();

        super.onFailure(e);
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, ListResult<E> result) {
        return new ListEvent<E>(
                targetScreenletId, _rowsRange.first, _rowsRange.second, result.getEntries(), result.getRowCount());
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, Exception e) {
        return new ListEvent<E>(targetScreenletId, e);
    }

    protected void cleanRequestState() {
        RequestState.getInstance().remove(getTargetScreenletId(), _rowsRange);
    }

    private final Pair<Integer, Integer> _rowsRange;


}