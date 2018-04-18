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

package com.liferay.mobile.screens.ddm.form.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

import org.json.JSONArray;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordEvent extends ListEvent<FormInstanceRecord> {

    private FormInstance formInstance;
    private FormInstanceRecord formInstanceRecord;

    public FormInstanceRecordEvent(FormInstanceRecord formInstanceRecord) {
        this.formInstanceRecord = formInstanceRecord;
    }

    public FormInstanceRecordEvent(FormInstance formInstance,
                                   FormInstanceRecord formInstanceRecord) {

        this.formInstance = formInstance;
        this.formInstanceRecord = formInstanceRecord;
    }

    @Override
    public String getListKey() {
        long formInstanceId = formInstance.getFormInstanceId();
        long formInstanceRecordId = formInstanceRecord.getFormInstanceRecordId();
        return formInstanceId + SEPARATOR + formInstanceRecordId;
    }

    @Override
    public FormInstanceRecord getModel() {
        return getFormInstanceRecord();
    }

    public FormInstance getFormInstance() {
        return formInstance;
    }

    public void setFormInstance(FormInstance formInstance) {
        this.formInstance = formInstance;
    }

    public FormInstanceRecord getFormInstanceRecord() {
        return formInstanceRecord;
    }

    public void setFormInstanceRecord(FormInstanceRecord formInstanceRecord) {
        this.formInstanceRecord = formInstanceRecord;
    }
}
