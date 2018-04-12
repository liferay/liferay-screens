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
