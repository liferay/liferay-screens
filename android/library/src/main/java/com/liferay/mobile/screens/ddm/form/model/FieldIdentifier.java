package com.liferay.mobile.screens.ddm.form.model;

/**
 * @author Paulo Cruz
 */
public abstract class FieldIdentifier {

    private String instanceId;
    private String name;

    public FieldIdentifier(String instanceId, String name) {
        this.instanceId = instanceId;
        this.name = name;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
