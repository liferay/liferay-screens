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

import com.liferay.mobile.screens.assetlist.AssetListScreenletEntry;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorBatchAsyncTaskCallback;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class AssetListCallback
	extends InteractorBatchAsyncTaskCallback<AssetListCallback.Result> {

	public AssetListCallback(int targetScreenletId, int page) {
		super(targetScreenletId);

		_page = page;
	}

	@Override
	public AssetListCallback.Result transform(Object obj) throws Exception {
		AssetListCallback.Result result = new AssetListCallback.Result();

		JSONArray jsonArray = ((JSONArray)obj).getJSONArray(0);
		List<AssetListScreenletEntry> entries = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			entries.add(new AssetListScreenletEntry(jsonObject));
		}

		result.entries = entries;
		result.rowCount = ((JSONArray)obj).getInt(1);

		return result;
	}

	public static class Result {

		List<AssetListScreenletEntry> entries;
		int rowCount;

	}

	@Override
	protected BasicEvent createEvent(
		int targetScreenletId, AssetListCallback.Result result) {

		return new AssetListEvent(
			targetScreenletId, _page, result.entries, result.rowCount);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new AssetListEvent(targetScreenletId, e);
	}

	private int _page;

}