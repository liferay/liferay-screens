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

package com.liferay.mobile.screens.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Silvio Santos
 */
public class JSONUtil {

    private JSONUtil() {
        super();
    }

    public static List toList(JSONArray jsonArray) throws JSONException {
        List<Object> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(fromJson(jsonArray.get(i)));
        }

        return list;
    }

    public static List<String> toStringList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.get(i).toString());
        }

        return list;
    }

    public static Map<String, Object> toMap(JSONObject jsonObject) throws JSONException {

        Map<String, Object> map = new HashMap<>();
        Iterator<String> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            Object object = jsonObject.get(key);

            map.put(key, fromJson(object));
        }

        return map;
    }

    public static Long castToLong(final Object o) {
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        } else if (o instanceof String) {
            return Long.valueOf((String) o);
        } else {
            return (Long) o;
        }
    }

    private static Object fromJson(Object object) throws JSONException {
        if (object == JSONObject.NULL) {
            return null;
        } else if (object instanceof JSONObject) {
            return toMap((JSONObject) object);
        } else if (object instanceof JSONArray) {
            return toList((JSONArray) object);
        } else {
            return object;
        }
    }
}