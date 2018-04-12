package com.liferay.mobile.screens.ddm.form.interactor;

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.ddm.form.model.FormContext;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public class FormContextEvent extends ListEvent<FormContext> {

    public FormContextEvent(JSONArray fieldValues, long formInstanceId, String languageId) {
        _fieldValues = fieldValues;
        _formInstanceId = formInstanceId;
        _languageId = languageId;
    }

    public JSONArray getFieldValues() {
        return _fieldValues;
    }

    public long getFormInstanceId() {
        return _formInstanceId;
    }

    public String getLanguageId() {
        return _languageId;
    }

    @Override
    public String getListKey() {
        return null;
    }

    @Override
    public FormContext getModel() {
        return _formContext;
    }


    public void setModel(FormContext formInstanceRecord) {
        _formContext = formInstanceRecord;
    }

    private JSONArray _fieldValues;
    private long _formInstanceId;
    private String _languageId;

    private FormContext _formContext;

}
