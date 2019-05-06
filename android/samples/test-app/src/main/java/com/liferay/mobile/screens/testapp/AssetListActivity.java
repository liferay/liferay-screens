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
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class AssetListActivity extends ThemeActivity implements BaseListListener<AssetEntry> {

    private AssetListScreenlet assetListScreenlet;
    private long classNameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset_list);

        classNameId = getIntent().getLongExtra("classNameId", 0);

        assetListScreenlet = findViewById(R.id.asset_list_screenlet);
        assetListScreenlet.setClassNameId(classNameId);

        if (classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_microblogs))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_organization))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_site))
            || classNameId == Long.parseLong(
            LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_user))) {

            assetListScreenlet.setGroupId(Long.parseLong(getResources().getString(R.string.liferay_parent_group_id)));
        }

        assetListScreenlet.setListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LiferayServerContext.setGroupId(Long.parseLong(getResources().getString(R.string.liferay_group_id)));
        assetListScreenlet.loadPage(0);
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {
        error(getString(R.string.page_error), e);
    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List<AssetEntry> entries, int rowCount) {
        info(rowCount + " " + getString(R.string.rows_received_info) + " " + startRow);
    }

    @Override
    public void onListItemSelected(AssetEntry element, View view) {

        Intent intent = getIntentWithTheme(AssetDisplayActivity.class);
        intent.putExtra("entryId", Long.valueOf(element.getValues().get("entryId").toString()));
        DefaultAnimation.startActivityWithAnimation(this, intent);
    }

    @Override
    public void error(Exception e, String userAction) {

    }
}
