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

import com.liferay.mobile.screens.base.list.ListCallback;
import com.liferay.mobile.screens.base.list.ListResult;
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
        extends ListCallback<DDLEntry> {


    public DDLListCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange) {
        super(targetScreenletId, rowsRange);
    }

    @Override
    public ListResult transform(Object obj) throws Exception {
        ListResult result = new ListResult();

        JSONArray jsonArray = ((JSONArray) obj).getJSONArray(0);
        List<DDLEntry> entries = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            entries.add(new DDLEntry(JSONUtil.toMap(jsonObject)));
        }

        result.setEntries(entries);
        result.setRowCount(((JSONArray) obj).getInt(1));

        return result;
    }


}