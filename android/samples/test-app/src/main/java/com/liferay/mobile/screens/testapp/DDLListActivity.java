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
import com.liferay.mobile.android.callback.typed.JSONObjectCallback;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.listener.CacheListener;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.list.DDLListScreenlet;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class DDLListActivity extends ThemeActivity implements BaseListListener<Record>, CacheListener {

    private DDLListScreenlet screenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ddl_list);

        screenlet = findViewById(R.id.ddl_list_screenlet);

        screenlet.setListener(this);
        screenlet.setCacheListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        screenlet.loadPage(0);
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {
        error(getString(R.string.page_error), e);
    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List<Record> entries, int rowCount) {
        info(rowCount + " " + getString(R.string.rows_received_info) + " " + startRow);
    }

    @Override
    public void onListItemSelected(Record element, View view) {
        loadDDLForm(element);
    }

    @Override
    public void loadingFromCache(boolean success) {
        info(getString(R.string.loading_cache_info) + " " + success);
    }

    @Override
    public void retrievingOnline(boolean triedInCache, Exception e) {
        info(getString(R.string.retrieving_online_info) + " " + triedInCache);
    }

    @Override
    public void storingToCache(Object object) {
        info(getString(R.string.storing_cache_info));
    }

    @Override
    public void error(Exception e, String userAction) {

    }

    private void loadDDLForm(Record element) {
        final long recordId = element.getRecordId();
        final long recordSetId = element.getRecordSetId();

        try {
            Session session = SessionContext.createSessionFromCurrentSession();
            session.setCallback(getCallback(recordId, recordSetId));

            ServiceProvider.getInstance().getDDLRecordSetConnector(session).getRecordSet(recordSetId);
        } catch (Exception e) {
            error(getString(R.string.structureId_error), e);
        }
    }

    private JSONObjectCallback getCallback(final long recordId, final long recordSetId) {
        return new JSONObjectCallback() {

            @Override
            public void onSuccess(JSONObject result) {
                try {
                    Intent intent = getIntentWithTheme(DDLFormActivity.class);
                    intent.putExtra("recordId", recordId);
                    intent.putExtra("recordSetId", recordSetId);
                    intent.putExtra("structureId", result.getLong("DDMStructureId"));

                    DefaultAnimation.startActivityWithAnimation(DDLListActivity.this, intent);
                } catch (JSONException e) {
                    error(getString(R.string.parse_json_error), e);
                }
            }

            @Override
            public void onFailure(Exception e) {
                error(getString(R.string.structureId_error), e);
            }
        };
    }
}
