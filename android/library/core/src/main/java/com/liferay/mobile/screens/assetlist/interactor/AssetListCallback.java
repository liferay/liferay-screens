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

package com.liferay.mobile.screens.assetlist.interactor;

import android.util.Pair;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.context.RequestStateEvent;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorBatchAsyncTaskCallback;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.JSONUtil;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class AssetListCallback
	extends InteractorBatchAsyncTaskCallback<AssetListCallback.Result> {

	public AssetListCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange) {
		super(targetScreenletId);

		_rowsRange = rowsRange;
	}

	@Override
	public Result transform(Object obj) throws Exception {
		Result result = new Result();

		JSONArray jsonArray = ((JSONArray)obj).getJSONArray(0);
		List<AssetEntry> entries = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			entries.add(new AssetEntry(JSONUtil.toMap(jsonObject)));
		}

		result.entries = entries;
		result.rowCount = ((JSONArray)obj).getInt(1);

		return result;
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Result result) {

		EventBusUtil.post(new RequestStateEvent(targetScreenletId, _rowsRange));

		return new AssetListEvent(
			targetScreenletId, _rowsRange.first, _rowsRange.second, result.entries, result.rowCount);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		EventBusUtil.post(new RequestStateEvent(targetScreenletId, _rowsRange));

		return new AssetListEvent(targetScreenletId, e);
	}

	private final Pair<Integer, Integer> _rowsRange;

	static class Result {

		List<AssetEntry> entries;
		int rowCount;

	}

}