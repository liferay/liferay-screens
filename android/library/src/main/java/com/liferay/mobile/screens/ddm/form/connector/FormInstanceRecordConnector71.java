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

package com.liferay.mobile.screens.ddm.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordConnector71 implements FormInstanceRecordConnector {

    public FormInstanceRecordConnector71(Session session) {

    }

    @Override
    public FormInstanceRecord addFormInstanceRecord(
            long formInstanceId, boolean isDraft, JSONArray fieldValues) throws Exception {

        return null;
    }

    @Override
    public FormInstanceRecord getFormInstanceRecord(long formInstanceRecordId) throws Exception {

        return null;
    }

    @Override
    public FormInstanceRecord updateFormInstanceRecord(
        long formInstanceRecordId, boolean isDraft, JSONArray fieldValues) throws Exception {

        return null;
    }
}
