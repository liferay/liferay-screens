package com.liferay.mobile.screens.ddm.form.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;

/**
 * @author Paulo Cruz
 */
public class FormInstanceEvent extends ListEvent<FormInstance> {

    private FormInstance formInstance;

    public FormInstanceEvent() {
        super();
    }

    public FormInstanceEvent(FormInstance formInstance) {
        this.formInstance = formInstance;
    }

    @Override
    public String getListKey() {
        return formInstance.getFormInstanceId() + "";
    }

    @Override
    public FormInstance getModel() {
        return getFormInstance();
    }

    public FormInstance getFormInstance() {
        return formInstance;
    }

    public void setFormInstance(FormInstance formInstance) {
        this.formInstance = formInstance;
    }
}
