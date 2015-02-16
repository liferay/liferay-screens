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
import com.liferay.mobile.screens.base.list.BaseListCallback;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class AssetListCallback
	extends BaseListCallback<AssetEntry> {

    public AssetListCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange) {
        super(targetScreenletId, rowsRange);
    }

    @Override
    public AssetEntry createEntity(Map<String, Object> stringObjectMap) {
        return new AssetEntry(stringObjectMap);
    }

}