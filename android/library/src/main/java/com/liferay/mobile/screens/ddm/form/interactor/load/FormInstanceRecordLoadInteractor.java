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

package com.liferay.mobile.screens.ddm.form.interactor.load;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceRecordConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormInstanceRecordEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordLoadInteractor
    extends BaseCacheReadInteractor<DDMFormListener, FormInstanceRecordEvent> {

    @Override
    public FormInstanceRecordEvent execute(Object... args) throws Exception {
        long formInstanceRecordId = (long) args[0];

        FormInstanceRecordConnector connector =
            ServiceProvider.getInstance().getFormInstanceRecordConnector(getSession());

        FormInstanceRecord formInstanceRecord = connector.getFormInstanceRecord(
            formInstanceRecordId);

        FormInstanceRecordEvent event = new FormInstanceRecordEvent(formInstanceRecord);

        return event;
    }

    @Override
    public void onSuccess(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().onFormInstanceRecordLoaded(event.getModel());
        }
    }

    @Override
    public void onFailure(FormInstanceRecordEvent event) {
        if (getListener() != null) {
            getListener().error(event.getException(), DDMFormScreenlet.LOAD_RECORD_ACTION);
        }
    }

    @Override
    protected String getIdFromArgs(Object... args) {
        return args[0].toString();
    }
}
