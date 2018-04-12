package com.liferay.mobile.screens.ddm.form.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public class FormInstanceConnector71 implements FormInstanceConnector {

    public FormInstanceConnector71(Session session) {

    }

    @Override
    public FormContext evaluateContext(long formInstanceId, String languageId,
        JSONArray fieldValues) throws Exception {

        return null;
    }

    @Override
    public FormInstance getFormInstance(long formInstanceId) throws Exception {
        return null;
    }
}
