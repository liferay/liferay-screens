/*
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
package com.liferay.mobile.screens.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mounir Hallab
 */
public class GetUserAdapter extends BaseAdapter {

    private final List jsonValues;

    public GetUserAdapter(Map<String, Object> map) {
        jsonValues = new ArrayList(map.entrySet());
    }

    @Override
    public int getCount() {
        return jsonValues.size();
    }

    @Override
    public Map.Entry<String, Object> getItem(int position) {
        return (Map.Entry) jsonValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_get_user_row, parent, false);
        }

        Map.Entry<String, Object> item = getItem(position);
        TextView keyLabel = convertView.findViewById(R.id.keyLabel);
        keyLabel.setText(item.getKey());
        if (item.getValue() != null) {
            TextView keyValue = convertView.findViewById(R.id.keyValue);
            keyValue.setText(item.getValue().toString());
        }
        return convertView;
    }
}
