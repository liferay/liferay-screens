package com.liferay.mobile.screens.ddm.form.model;

import android.os.Parcel;

import com.liferay.mobile.screens.ddl.model.Field;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * @author Paulo Cruz
 */
public abstract class DDMField<T extends Serializable> extends Field<T> {

    private String instanceId;

    public DDMField(Map<String, Object> attributes, Locale currentLocale, Locale defaultLocale) {
        super(attributes, currentLocale, defaultLocale);

        instanceId = getAttributeStringValue(attributes, "instanceId");
    }

    protected DDMField(Parcel source, ClassLoader loader) {
        super(source, loader);
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
