package com.liferay.mobile.screens.ddm.form.model;

import java.util.List;

/**
 * @author Paulo Cruz
 */
public class FormInstance {

    private long formInstanceId;
    private List<Field> fields;

    public FormInstance(long formInstanceId, List<Field> fields) {
        this.formInstanceId = formInstanceId;
        this.fields = fields;
    }

    public long getFormInstanceId() {
        return formInstanceId;
    }

    public void setFormInstanceId(long formInstanceId) {
        this.formInstanceId = formInstanceId;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
