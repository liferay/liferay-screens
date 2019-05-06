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

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListInteractor extends BaseListInteractor<BaseListInteractorListener<Record>, DDLFormEvent> {

    protected void validate(long recordSetId, int startRow, int endRow, Locale locale) {
        super.validate(startRow, endRow, locale);

        if (recordSetId <= 0) {
            throw new IllegalArgumentException("ddlRecordSetId cannot be 0 or negative");
        }
    }

    @Override
    protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
        long recordSetId = (long) args[0];
        long userId = (long) args[1];

        validate(recordSetId, query.getStartRow(), query.getEndRow(), locale);

        ScreensDDLRecordConnector ddlRecordService =
            ServiceProvider.getInstance().getScreensDDLRecordConnector(getSession());
        int startRow = query.getStartRow();
        int endRow = query.getEndRow();
        String localeString = locale.toString();

        return userId != 0 ? ddlRecordService.getDdlRecords(recordSetId, userId, localeString, startRow, endRow,
            query.getComparatorJSONWrapper())
            : ddlRecordService.getDdlRecords(recordSetId, localeString, startRow, endRow,
                query.getComparatorJSONWrapper());
    }

    @Override
    protected Integer getPageRowCountRequest(Object... args) throws Exception {
        long recordSetId = (long) args[0];
        long userId = (long) args[1];

        validate(recordSetId, query.getStartRow(), query.getEndRow(), locale);

        ScreensDDLRecordConnector ddlRecordService =
            ServiceProvider.getInstance().getScreensDDLRecordConnector(getSession());

        return userId != 0 ? ddlRecordService.getDdlRecordsCount(recordSetId, userId)
            : ddlRecordService.getDdlRecordsCount(recordSetId);
    }

    @Override
    protected DDLFormEvent createEntity(Map<String, Object> stringObjectMap) {
        return new DDLFormEvent(new Record(stringObjectMap, locale), new JSONObject(stringObjectMap));
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        return String.valueOf(args[0]);
    }
}