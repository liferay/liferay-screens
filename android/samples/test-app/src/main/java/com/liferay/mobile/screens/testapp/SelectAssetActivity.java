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

package com.liferay.mobile.screens.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.liferay.mobile.screens.testapp.utils.AssetClassnameIds70;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;

/**
 * @author Javier Gamarra
 */
public class SelectAssetActivity extends ThemeActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.select_asset);

        ListView assetList = findViewById(R.id.select_asset_list);
        ArrayAdapter<AssetClassnameIds70> adapter =
            new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AssetClassnameIds70.values());
        assetList.setAdapter(adapter);
        assetList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = getIntentWithTheme(AssetListActivity.class);
        intent.putExtra("classNameId", AssetClassnameIds70.values()[position].getValue());
        DefaultAnimation.startActivityWithAnimation(this, intent);
    }
}
