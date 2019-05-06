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

package com.liferay.mobile.screens.ddl.form.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.ddl.model.Record;
import org.json.JSONObject;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;

/**
 * @author Silvio Santos
 */
public class DDLFormEvent extends ListEvent<Record> {

    private Record record;

    public DDLFormEvent() {
        super();
    }

    public DDLFormEvent(Record record, JSONObject jsonObject) {
        super(jsonObject);

        this.record = record;
    }

    @Override
    public String getListKey() {
        long recordSetId = record.getRecordSetId();
        long recordId = record.getRecordId();
        return recordSetId + SEPARATOR + recordId;
    }

    @Override
    public Record getModel() {
        return getRecord();
    }

    public Record getRecord() {
        return record;
    }
}