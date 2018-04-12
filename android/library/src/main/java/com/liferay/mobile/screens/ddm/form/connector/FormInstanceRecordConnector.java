package com.liferay.mobile.screens.ddm.form.connector;

import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public interface FormInstanceRecordConnector {
    FormInstanceRecord addFormInstanceRecord(long formInstanceId, boolean isDraft,
        JSONArray fieldValues) throws Exception;

    FormInstanceRecord getFormInstanceRecord(long formInstanceRecordId) throws Exception;

    FormInstanceRecord updateFormInstanceRecord(long formInstanceRecordId, boolean isDraft,
        JSONArray fieldValues) throws Exception;
}
