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

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListResult;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class FilteredAssetListCallback extends InteractorAsyncTaskCallback<BaseListResult<AssetEntry>> {

	public FilteredAssetListCallback(int targetScreenletId) {
		super(targetScreenletId);
	}

	public BaseListResult transform(Object obj) throws Exception {
		BaseListResult result = new BaseListResult();

		List<AssetEntry> entries = new ArrayList<>();
		JSONArray jsonArray = (JSONArray) obj;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			entries.add(new AssetEntry(JSONUtil.toMap(jsonObject)));
		}

		result.setEntries(entries);
		result.setRowCount(entries.size());
		return result;
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, BaseListResult<AssetEntry> result) {
		return new BaseListEvent<>(targetScreenletId, 0, 0, null, result.getEntries(), result.getRowCount());
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new BaseListEvent<AssetEntry>(targetScreenletId, 0, 0, null, e);
	}
}