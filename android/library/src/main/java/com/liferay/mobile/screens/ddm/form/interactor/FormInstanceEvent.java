package com.liferay.mobile.screens.ddm.form.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;

/**
 * @author Paulo Cruz
 */
public class FormInstanceEvent extends ListEvent<FormInstance> {

    public FormInstanceEvent(long formInstanceId) {
        _formInstanceId = formInstanceId;
    }

    public FormInstanceEvent(FormInstance formInstance) {
        _formInstance = formInstance;
    }

    public long getFormInstanceId() {
        return _formInstanceId;
    }

    @Override
    public String getListKey() {
        return null;
    }

    @Override
    public FormInstance getModel() {
        return _formInstance;
    }

    public void setModel(FormInstance formInstance) {
        _formInstance = formInstance;
    }

    private long _formInstanceId;

    private FormInstance _formInstance;
}
