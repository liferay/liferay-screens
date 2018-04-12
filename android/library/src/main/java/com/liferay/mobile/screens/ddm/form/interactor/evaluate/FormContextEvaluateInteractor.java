package com.liferay.mobile.screens.ddm.form.interactor.evaluate;

import com.liferay.mobile.screens.base.interactor.BaseCacheWriteInteractor;
import com.liferay.mobile.screens.ddm.form.FormListener;
import com.liferay.mobile.screens.ddm.form.FormScreenlet;
import com.liferay.mobile.screens.ddm.form.connector.FormInstanceConnector;
import com.liferay.mobile.screens.ddm.form.interactor.FormContextEvent;
import com.liferay.mobile.screens.ddm.form.model.FormContext;
import com.liferay.mobile.screens.util.ServiceProvider;

/**
 * @author Paulo Cruz
 */
public class FormContextEvaluateInteractor
    extends BaseCacheWriteInteractor<FormListener, FormContextEvent> {

    @Override
    public FormContextEvent execute(FormContextEvent event) throws Exception {
        FormInstanceConnector connector =
                ServiceProvider.getInstance().getFormInstanceConnector(getSession());

        FormContext formContext = connector.evaluateContext(
                event.getFormInstanceId(), event.getLanguageId(), event.getFieldValues());

        event.setModel(formContext);

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
            getListener().error(event.getException(), FormScreenlet.EVALUATE_CONTEXT_ACTION);
        }
    }
}
