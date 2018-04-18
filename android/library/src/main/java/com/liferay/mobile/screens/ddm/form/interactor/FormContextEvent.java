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
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

import org.json.JSONArray;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;

/**
 * @author Paulo Cruz
 */
public class FormContextEvent extends ListEvent<FormContext> {

    private final String CONTEXT = "CONTEXT";

    private FormContext formContext;
    private FormInstance formInstance;
    private FormInstanceRecord formInstanceRecord;
    private String languageId;

    public FormContextEvent() {
        super();
    }

    public FormContextEvent(FormInstance formInstance, FormInstanceRecord formInstanceRecord,
                            String languageId) {

        this.formInstance = formInstance;
        this.formInstanceRecord = formInstanceRecord;
        this.languageId = languageId;
    }

    @Override
    public String getListKey() {
        long formInstanceId = formInstance.getFormInstanceId();
        long formInstanceRecordId = formInstanceRecord.getFormInstanceRecordId();
        return CONTEXT + formInstanceId + SEPARATOR + formInstanceRecordId;
    }

    @Override
    public FormContext getModel() {
        return getFormContext();
    }

    public FormContext getFormContext() {
        return formContext;
    }

    public void setFormContext(FormContext formContext) {
        this.formContext = formContext;
    }

    public FormInstance getFormInstance() {
        return formInstance;
    }

    public FormInstanceRecord getFormInstanceRecord() {
        return formInstanceRecord;
    }

    public String getLanguageId() {
        return languageId;
    }
}
