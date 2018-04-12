package com.liferay.mobile.screens.ddm.form.connector;

import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public interface FormInstanceConnector {
    FormContext evaluateContext(long formInstanceId, String languageId, JSONArray fieldValues)
        throws Exception;

    FormInstance getFormInstance(long formInstanceId) throws Exception;
}
