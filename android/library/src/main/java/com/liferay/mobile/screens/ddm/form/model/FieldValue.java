package com.liferay.mobile.screens.ddm.form.model;

/**
 * @author Paulo Cruz
 */
public class FieldValue extends FieldIdentifier {

    private String value;

    public FieldValue(String instanceId, String name, String value) {
        super(instanceId, name);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
