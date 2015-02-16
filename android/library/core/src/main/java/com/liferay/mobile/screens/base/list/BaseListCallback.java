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
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public abstract class BaseListCallback<E>
        extends InteractorBatchAsyncTaskCallback<BaseListResult<E>> {

    public BaseListResult transform(Object obj) throws Exception {
        BaseListResult result = new BaseListResult();
        JSONArray jsonArray = ((JSONArray) obj).getJSONArray(0);
        List<E> entries = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            entries.add(createEntity(JSONUtil.toMap(jsonObject)));
        }

        result.setEntries(entries);
        result.setRowCount(((JSONArray) obj).getInt(1));
        return result;
    }

    public abstract E createEntity(Map<String, Object> stringObjectMap);

    public BaseListCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange) {
        super(targetScreenletId);

        _rowsRange = rowsRange;
    }

    @Override
    public void onSuccess(BaseListResult<E> result) {
        cleanRequestState();

        super.onSuccess(result);
    }

    @Override
    public void onFailure(Exception e) {
        cleanRequestState();

        super.onFailure(e);
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, BaseListResult<E> result) {
        return new BaseListEvent<E>(
                targetScreenletId, _rowsRange.first, _rowsRange.second, result.getEntries(), result.getRowCount());
    }

    @Override
    protected BasicEvent createEvent(int targetScreenletId, Exception e) {
        return new BaseListEvent<E>(targetScreenletId, e);
    }

    protected void cleanRequestState() {
        RequestState.getInstance().remove(getTargetScreenletId(), _rowsRange);
    }

    private final Pair<Integer, Integer> _rowsRange;


}