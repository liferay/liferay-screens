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

package com.liferay.mobile.screens.ddl.list.interactor;

import android.util.Pair;

import com.liferay.mobile.screens.base.context.RequestState;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorBatchAsyncTaskCallback;
import com.liferay.mobile.screens.ddl.list.DDLEntry;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListCallback
	extends InteractorBatchAsyncTaskCallback<DDLListCallback.Result> {

	public DDLListCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange) {
		super(targetScreenletId);

		_rowsRange = rowsRange;
	}

	@Override
	public Result transform(Object obj) throws Exception {
		Result result = new Result();

		JSONArray jsonArray = ((JSONArray)obj).getJSONArray(0);
		List<DDLEntry> entries = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			entries.add(new DDLEntry(JSONUtil.toMap(jsonObject)));
		}

		result.entries = entries;
		result.rowCount = ((JSONArray)obj).getInt(1);

		return result;
	}

	@Override
	public void onSuccess(Result result) {
		cleanRequestState();

		super.onSuccess(result);
	}

	@Override
	public void onFailure(Exception e) {
		cleanRequestState();

		super.onFailure(e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Result result) {
		return new DDLListEvent(
			targetScreenletId, _rowsRange.first, _rowsRange.second, result.entries, result.rowCount);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new DDLListEvent(targetScreenletId, e);
	}

	protected void cleanRequestState() {
		RequestState.getInstance().remove(getTargetScreenletId(), _rowsRange);
	}

	private final Pair<Integer, Integer> _rowsRange;

	static class Result {

		List<DDLEntry> entries;
		int rowCount;

	}

}