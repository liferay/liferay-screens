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

package com.liferay.mobile.screens.ddm.form.interactor.update;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceRecordEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordUpdateInteractor
    extends BaseCacheWriteInteractor<DDMFormListener, FormInstanceRecordEvent> {

    @Override
    public FormInstanceRecordEvent execute(FormInstanceRecordEvent event) throws Exception {
        FormInstanceRecordConnector connector =
                ServiceProvider.getInstance().getFormInstanceRecordConnector(getSession());

        FormInstanceRecord currentFormInstanceRecord = event.getModel();
        JSONArray fieldValuesJson = new JSONArray(currentFormInstanceRecord.getFieldValues());

        FormInstanceRecord formInstanceRecord = connector.updateFormInstanceRecord(
                currentFormInstanceRecord.getFormInstanceRecordId(),
                currentFormInstanceRecord.isDraft(), fieldValuesJson);

        event.setFormInstanceRecord(formInstanceRecord);

        return event;
    }

    @Override
    public void onSuccess(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().onFormInstanceRecordUpdated(event.getModel());
        }
    }

    @Override
    public void onFailure(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().error(event.getException(), DDMFormScreenlet.UPDATE_RECORD_ACTION);
        }
    }
}
