package com.liferay.mobile.screens.ddm.form.interactor.evaluate;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormContextEvent;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONArray;

/**
 * @author Paulo Cruz
 */
public class FormContextEvaluateInteractor
    extends BaseCacheWriteInteractor<DDMFormListener, FormContextEvent> {

    @Override
    public FormContextEvent execute(FormContextEvent event) throws Exception {
        FormInstanceConnector connector =
                ServiceProvider.getInstance().getFormInstanceConnector(getSession());

        FormInstance formInstance = event.getFormInstance();
        FormInstanceRecord formInstanceRecord = event.getFormInstanceRecord();

        JSONArray fieldValuesJson = new JSONArray(formInstanceRecord.getFieldValues());

        FormContext formContext = connector.evaluateContext(formInstance.getFormInstanceId(),
                event.getLanguageId(), fieldValuesJson);

        event.setFormContext(formContext);

        return event;
    }

    @Override
    public void onSuccess(FormContextEvent event) {
        if (getListener() != null) {
            getListener().onFormContextEvaluated(event.getModel());
        }
    }

    @Override
    public void onFailure(FormContextEvent event) {
        if (getListener() != null) {
            getListener().error(event.getException(), DDMFormScreenlet.EVALUATE_CONTEXT_ACTION);
        }
    }
}
