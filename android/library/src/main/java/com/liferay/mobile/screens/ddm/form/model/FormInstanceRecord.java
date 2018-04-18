package com.liferay.mobile.screens.ddm.form.model;

import java.util.List;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecord {

    private long formInstanceRecordId;
    private List<FieldValue> fieldValues;

    public long getFormInstanceRecordId() {
        return formInstanceRecordId;
    }

    public void setFormInstanceRecordId(long formInstanceRecordId) {
        this.formInstanceRecordId = formInstanceRecordId;
    }

    public List<FieldValue> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<FieldValue> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public boolean isDraft() {
        return formInstanceRecordId == 0;
    }
}
