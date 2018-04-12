package com.liferay.mobile.screens.ddm.form.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecordEvent extends ListEvent<FormInstanceRecord> {

    public FormInstanceRecordEvent(FormInstanceRecord formInstanceRecord) {
        _formInstanceRecord = formInstanceRecord;
    }

    public FormInstanceRecordEvent(JSONArray fieldValues, long formInstanceId, boolean isDraft) {
        _fieldValues = fieldValues;
        _formInstanceId = formInstanceId;
        _isDraft = isDraft;
    }

    public FormInstanceRecordEvent(JSONArray fieldValues, long formInstanceId,
        long formInstanceRecordId, boolean isDraft) {

        _fieldValues = fieldValues;
        _formInstanceId = formInstanceId;
        _formInstanceRecordId = formInstanceRecordId;
        _isDraft = isDraft;
    }

    public JSONArray getFieldValues() {
        return _fieldValues;
    }

    public long getFormInstanceId() {
        return _formInstanceId;
    }

    public long getFormInstanceRecordId() {
        return _formInstanceRecordId;
    }

    @Override
    public String getListKey() {
        return null;
    }

    @Override
    public FormInstanceRecord getModel() {
        return _formInstanceRecord;
    }

    public boolean isDraft() {
        return _isDraft;
    }

    public void setModel(FormInstanceRecord formInstanceRecord) {
        _formInstanceRecord = formInstanceRecord;
    }

    private JSONArray _fieldValues;
    private long _formInstanceId;
    private long _formInstanceRecordId;
    private boolean _isDraft;

    private FormInstanceRecord _formInstanceRecord;
}
